package main.game.maze.common.graphics.config;

/**
 * Phase 4 (F16): pluggable strategy for sourcing a {@link MazeRuntimeConfig}.
 *
 * <p>Default impl ({@link PropertiesMazeConfigLoader}) reads a .properties
 * file from the classpath or filesystem. A future impl will parse the DSL
 * produced by {@code main.game.maze.dsl} into the same record so both
 * backends can be driven by a single MDD-authored source.
 */
@FunctionalInterface
public interface MazeConfigLoader {
    MazeRuntimeConfig load();
}


