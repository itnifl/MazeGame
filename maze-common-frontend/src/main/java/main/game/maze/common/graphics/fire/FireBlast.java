package main.game.maze.common.graphics.fire;

/**
 * Geometry of one bomb detonation: a centre and four flame arms along the maze corridors.
 *
 * <p>The record is coordinate-system agnostic: arm lengths are given along +x, -x, +y and
 * -y of whatever space the frontend draws in (libGDX is y-up, JavaFX is y-down). Arm
 * lengths are the visual reach of each arm after wall clipping, so 0 means a wall right
 * next to the bomb.
 *
 * @param centerX       blast centre x
 * @param centerY       blast centre y
 * @param armPlusX      flame reach along +x (&ge; 0)
 * @param armMinusX     flame reach along -x (&ge; 0)
 * @param armPlusY      flame reach along +y (&ge; 0)
 * @param armMinusY     flame reach along -y (&ge; 0)
 * @param corridorWidth full width of the flame corridor (&gt; 0)
 * @param seed          seed for the static per-puff jitter, so one blast looks stable frame to frame
 */
public record FireBlast(
        double centerX,
        double centerY,
        double armPlusX,
        double armMinusX,
        double armPlusY,
        double armMinusY,
        double corridorWidth,
        long seed) {

    /** How long a blast is visible, shared by both frontends. */
    public static final double DEFAULT_LIFETIME_SECONDS = 0.75;

    public FireBlast {
        if (corridorWidth <= 0) {
            throw new IllegalArgumentException("corridorWidth must be > 0");
        }
        armPlusX = Math.max(0, armPlusX);
        armMinusX = Math.max(0, armMinusX);
        armPlusY = Math.max(0, armPlusY);
        armMinusY = Math.max(0, armMinusY);
    }

    /** Longest arm, used to scale the shockwave and embers. */
    public double maxArm() {
        return Math.max(Math.max(armPlusX, armMinusX), Math.max(armPlusY, armMinusY));
    }

    /**
     * Seed derived from the blast position, so a bomb at the same spot always looks the same
     * while different bombs look different.
     */
    public static long seedFor(double centerX, double centerY) {
        return Double.doubleToLongBits(centerX) * 31L + Double.doubleToLongBits(centerY);
    }
}
