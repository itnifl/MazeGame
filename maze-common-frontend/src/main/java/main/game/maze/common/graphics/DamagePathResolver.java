package main.game.maze.common.graphics;

/**
 * Maps a healthy sprite path and a {@link DamageState} to the sprite path to display.
 * Shared by the JavaFX and libGDX frontends; tests can supply their own lambda.
 */
@FunctionalInterface
public interface DamagePathResolver {

    /**
     * @return the damage-state sprite path, or {@code basePath} when no such sprite exists
     *         or {@code state} is {@link DamageState#HEALTHY}
     */
    String resolve(String basePath, DamageState state);

    /**
     * Resolver that checks the classpath (relative to {@code anchor}) with
     * {@link DamageStateResolver} naming, caching each answer so per-frame calls are cheap.
     */
    static DamagePathResolver classpath(Class<?> anchor) {
        return new CachingDamagePathResolver(path -> DamageStateResolver.resourceExists(anchor, path));
    }
}
