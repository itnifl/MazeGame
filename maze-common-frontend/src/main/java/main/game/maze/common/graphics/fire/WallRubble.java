package main.game.maze.common.graphics.fire;

import java.util.Objects;

/**
 * A breakable wall segment collapsing into rubble, drawn by {@link WallRubbleModel}.
 * Coordinates use the caller's space; the model only offsets along and across the segment,
 * so y-up and y-down both work.
 *
 * @param thickness drawn wall thickness; sets the size of the chunks and the dust; positive
 * @param material  rubble look, from the wall material in the walls model
 * @param seed      varies the chunk scatter between walls
 */
public record WallRubble(double ax, double ay, double bx, double by,
                         double thickness, RubbleMaterial material, long seed) {

    /** On-screen life of the collapse animation. Visual only. */
    public static final double LIFETIME_SECONDS = 1.0;
    /** Furthest any puff reaches from the wall line (including its radius), in wall thicknesses. */
    static final double EXTENT_THICKNESSES = 4.0;

    public WallRubble {
        Objects.requireNonNull(material, "material");
        if (!(thickness > 0)) {
            throw new IllegalArgumentException("thickness must be positive, was " + thickness);
        }
    }

    /** Rubble thickness for a wall drawn {@code wallThicknessPx} wide: chunkier than the line so blocks read clearly. */
    public static double visualThickness(double wallThicknessPx) {
        return Math.max(10.0, wallThicknessPx * 2.4);
    }

    /** Distance around the segment that contains every puff over the whole life. */
    public double maxExtent() {
        return thickness * EXTENT_THICKNESSES;
    }

    double length() {
        return Math.hypot(bx - ax, by - ay);
    }
}
