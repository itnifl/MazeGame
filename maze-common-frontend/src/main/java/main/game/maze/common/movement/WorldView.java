package main.game.maze.common.movement;

/**
 * Backend-neutral world snapshot consumed by {@link EnemyMovementService}.
 *
 * <p>This is the dependency-inversion seam between the movement algorithm
 * (pure, in maze-common-frontend) and the rendering backend. JavaFX wraps
 * its {@code GameMazeWorld}/{@code PlayerCharacter} into this interface;
 * libGDX wraps its {@code MazeArena}/{@code PlayerState}. The movement
 * service never imports anything from either backend.
 */
public interface WorldView {

    /** Player x in world units. */
    double playerX();

    /** Player y in world units. */
    double playerY();

    /**
     * True if an enemy with bounding box centred at ({@code centerX},
     * {@code centerY}) and edge {@code size} would overlap any wall or
     * leave the playfield. Both frontends MUST answer the same way for
     * the same coordinates so the parity test holds.
     */
    boolean wouldCollide(double centerX, double centerY, double size);

    /** Minimum playable x bound in world units. */
    default double minX() {
        return 0d;
    }

    /** Minimum playable y bound in world units. */
    default double minY() {
        return 0d;
    }

    /** Maximum playable x bound in world units. */
    default double maxX() {
        return 1000d;
    }

    /** Maximum playable y bound in world units. */
    default double maxY() {
        return 1000d;
    }
}
