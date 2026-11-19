package main.game.maze.common.graphics.fire;

/**
 * A pumpkin bomb exploding: a round fireball that bursts the pumpkin shell into flying
 * chunks and seeds. Drawn by {@link PumpkinBurstModel}; coordinates use the caller's space
 * (the model only offsets from the centre, so y-up and y-down both work).
 *
 * @param centerX impact x
 * @param centerY impact y
 * @param radius  fireball radius, normally the projectile's splash radius; must be positive
 * @param seed    makes the debris scatter differ between bursts while staying stable per burst
 */
public record PumpkinBurst(double centerX, double centerY, double radius, long seed) {

    /** Shortest on-screen life, used for small (straight-shot) bursts. */
    public static final double MIN_LIFETIME_SECONDS = 0.45;
    /** Longest on-screen life, used for big lobbed bombs. */
    public static final double MAX_LIFETIME_SECONDS = 1.0;
    /** Burst radius for a straight (non-lobbed) pumpkin shot. */
    public static final double STRAIGHT_SHOT_RADIUS = 18.0;
    /** Smallest burst for a lobbed pumpkin bomb, so tiny splash radii still read as an explosion. */
    public static final double MIN_LOB_RADIUS = 24.0;
    /** Furthest any puff reaches from the centre (including its radius), as a multiple of {@link #radius}. */
    static final double EXTENT_FACTOR = 1.75;

    public PumpkinBurst {
        if (!(radius > 0)) {
            throw new IllegalArgumentException("radius must be positive, was " + radius);
        }
    }

    /** Distance from the centre that contains every puff over the whole life. */
    public double maxExtent() {
        return radius * EXTENT_FACTOR;
    }

    /** Burst radius for a lobbed bomb: it fills the splash (damage) radius. */
    public static double lobRadius(double splashRadius) {
        return Math.max(MIN_LOB_RADIUS, splashRadius);
    }

    /** On-screen life for a burst of this radius: bigger bombs burn and smoke longer. Visual only. */
    public static double lifetimeFor(double radius) {
        return PuffFrame.clamp(0.35 + 0.011 * radius, MIN_LIFETIME_SECONDS, MAX_LIFETIME_SECONDS);
    }
}
