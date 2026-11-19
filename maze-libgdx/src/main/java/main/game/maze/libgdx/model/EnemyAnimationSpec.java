package main.game.maze.libgdx.model;

/**
 * Immutable animation metadata for a spawned enemy.
 *
 * <p>Directional paths are the frame-1 images for each movement direction.
 * The libGDX runtime derives subsequent frame paths by replacing the first
 * digit sequence in the filename (see
 * {@link main.game.maze.libgdx.game.GdxEnemyRuntime#deriveAnimationFramePath}).
 *
 * <p>{@code animationFrameCount} is the number of walk-cycle frames available
 * per direction (1 means no animation — always show the base image).
 * {@code spriteScale} multiplies the spawn's logical size for rendering only.
 */
public record EnemyAnimationSpec(
        String imageTurnLeft,
        String imageTurnRight,
        String imageTurnUp,
        String imageTurnDown,
        int animationFrameCount,
        float spriteScale,
        boolean damageStatesEnabled) {

    /**
     * Backward-compatible 6-argument constructor with {@code damageStatesEnabled = false}.
     */
    public EnemyAnimationSpec(
            String imageTurnLeft,
            String imageTurnRight,
            String imageTurnUp,
            String imageTurnDown,
            int animationFrameCount,
            float spriteScale) {
        this(imageTurnLeft, imageTurnRight, imageTurnUp, imageTurnDown, animationFrameCount, spriteScale, false);
    }

    /** No-animation default: single frame, 1:1 scale, no directional overrides, damage states disabled. */
    public static EnemyAnimationSpec defaults() {
        return new EnemyAnimationSpec(null, null, null, null, 1, 1.0f, false);
    }

    /** Directional images with no walk animation, 1:1 scale, damage states disabled. */
    public static EnemyAnimationSpec staticDirectional(
            String imageTurnLeft, String imageTurnRight,
            String imageTurnUp, String imageTurnDown) {
        return new EnemyAnimationSpec(imageTurnLeft, imageTurnRight, imageTurnUp, imageTurnDown, 1, 1.0f, false);
    }
}
