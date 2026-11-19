package main.game.maze.common.graphics;

import java.util.Locale;

/**
 * Visual damage state of an enemy based on remaining hit point ratio.
 *
 * <ul>
 *   <li>{@link #HEALTHY}: &gt; 75 % HP</li>
 *   <li>{@link #DAMAGED}: 51 &ndash; 75 % HP</li>
 *   <li>{@link #BLEEDING}: 26 &ndash; 50 % HP</li>
 *   <li>{@link #TIRED}: &le; 25 % HP</li>
 * </ul>
 */
public enum DamageState {
    HEALTHY,   // > 75 %
    DAMAGED,   // 51-75 %
    BLEEDING,  // 26-50 %
    TIRED;     // <= 25 %

    /**
     * Returns the lower-case state label used for sprite naming conventions
     * (e.g. {@code "healthy"}, {@code "damaged"}, {@code "bleeding"}, {@code "tired"}).
     *
     * @return the lower-case name
     */
    public String label() {
        return name().toLowerCase(Locale.ROOT);
    }

    /**
     * Determines the {@link DamageState} corresponding to the ratio of
     * {@code currentHp} to {@code maxHp}.
     *
     * @param currentHp the current hit points
     * @param maxHp the maximum hit points
     * @return the appropriate {@link DamageState}; returns {@link #HEALTHY} if {@code maxHp <= 0}
     */
    public static DamageState of(int currentHp, int maxHp) {
        if (maxHp <= 0) {
            return HEALTHY;
        }
        double ratio = (double) currentHp / maxHp;
        if (ratio > 0.75) {
            return HEALTHY;
        }
        if (ratio > 0.50) {
            return DAMAGED;
        }
        if (ratio > 0.25) {
            return BLEEDING;
        }
        return TIRED;
    }
}
