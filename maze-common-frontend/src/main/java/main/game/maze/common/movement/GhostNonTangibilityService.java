package main.game.maze.common.movement;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Shared utility for ghost non-tangibility (phasing) energy calculations.
 *
 * <p>Phasing ghosts have {@code nonTangibilityEnergy > 0}. While phasing they:
 * <ul>
 *   <li>Move in a single random cardinal direction, ignoring all wall collisions.</li>
 *   <li>Bounce at board boundaries by picking a new random direction.</li>
 *   <li>Can still harm the player (wall-blocking checks are skipped for them).</li>
 *   <li>Are rendered semi-transparent based on remaining energy.</li>
 * </ul>
 *
 * <p>Both the JavaFX and libGDX frontends must use this class for the constants
 * and computations to guarantee parity between the two runtimes.
 */
public final class GhostNonTangibilityService {

    private static final Logger LOGGER = Logger.getLogger(GhostNonTangibilityService.class.getName());

    /** Maximum non-tangibility energy (ghosts start at this value when spawned phasing). */
    public static final double MAX_ENERGY = 100.0;

    /**
     * Energy drained per simulated second.
     * Derived from the JavaFX movement loop: 0.14 units per ~60 ms tick,
     * scaled to a per-second rate.
     */
    public static final double ENERGY_DECREASE_PER_SEC = 0.14 * (1000.0 / 60.0);

    private GhostNonTangibilityService() {
        // Utility class — not instantiable.
    }

    /**
     * Returns {@code true} when the ghost is currently phasing through walls.
     *
     * @param nonTangibilityEnergy the ghost's current energy value
     * @return {@code true} if energy is greater than zero
     */
    public static boolean isPhasing(double nonTangibilityEnergy) {
        return nonTangibilityEnergy > 0;
    }

    /**
     * Drains energy for one simulation step of {@code dtSeconds} and returns the
     * new (non-negative) energy value.
     *
     * @param currentEnergy current energy value (0..MAX_ENERGY)
     * @param dtSeconds     elapsed seconds since the last step (e.g. 0.060 for JavaFX at 60 ms)
     * @return updated energy, clamped to zero
     */
    public static double drainEnergy(double currentEnergy, double dtSeconds) {
        return Math.max(0.0, currentEnergy - dtSeconds * ENERGY_DECREASE_PER_SEC);
    }

    /**
     * Calculates rendering opacity from the ghost's current energy.
     *
     * <p>Formula: {@code clamp(1 − energy/MAX_ENERGY + 0.1, 0.1, 1.0)}.
     * A fully-phasing ghost (energy = MAX_ENERGY) has opacity 0.1;
     * a solid ghost (energy = 0) has opacity 1.0.
     *
     * @param energy current energy value
     * @return opacity in [0.1, 1.0]
     */
    public static double calculateOpacity(double energy) {
        if (energy <= 0) {
            return 1.0;
        }
        double opacity = 1.0 - (energy / MAX_ENERGY) + 0.1;
        return Math.max(0.1, Math.min(1.0, opacity));
    }

    /**
     * Calculates rendering opacity taking the ghost's configured base visibility into account.
     *
     * <p>When solid ({@code energy <= 0}) the result is exactly {@code visibilityLevel / 100.0}.
     * When phasing the opacity ramps from {@code 0.1} up toward {@code baseOpacity} as energy drains,
     * but is capped at {@code baseOpacity} so a semi-transparent ghost never appears more opaque
     * than its configured visibility level.
     *
     * <p>Design note: a ghost with {@code visibilityLevel=0} that is phasing still renders at
     * opacity {@code 0.1} (the phasing minimum) so players can detect it; this is an intentional
     * gameplay floor.
     *
     * @param energy          current non-tangibility energy (0..MAX_ENERGY)
     * @param visibilityLevel configured visibility percentage (0..100)
     * @return opacity in [0.0, 1.0]
     */
    public static double calculateOpacity(double energy, int visibilityLevel) {
        double baseOpacity = Math.max(0.0, Math.min(100.0, visibilityLevel)) / 100.0;
        if (energy <= 0) {
            return baseOpacity;
        }
        double phasingOpacity = 1.0 - (energy / MAX_ENERGY) + 0.1;
        double clampedOpacity = Math.max(0.1, Math.min(baseOpacity, phasingOpacity));
        if (LOGGER.isLoggable(Level.FINE) && phasingOpacity > baseOpacity) {
            LOGGER.fine(String.format(
                    "Ghost opacity clamped by phasing: energy=%.2f visibilityLevel=%d baseOpacity=%.4f clampedOpacity=%.4f",
                    energy, visibilityLevel, baseOpacity, clampedOpacity));
        }
        return clampedOpacity;
    }
}
