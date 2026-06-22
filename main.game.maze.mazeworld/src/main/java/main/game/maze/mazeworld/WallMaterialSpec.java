package main.game.maze.mazeworld;

/**
 * Lightweight description of a breakable wall material used by {@link GameMazeWorld}
 * to assign per-wall hit points without depending on the EMF walls model.
 *
 * <p>Callers with access to {@code WallRegistry} (in {@code maze-module-generator})
 * can build specs from registry entries and pass them into
 * {@link GameMazeWorld#assignBreakableWalls(long, java.util.List)}.
 * The {@link GameMazeWorld#DEFAULT_BREAKABLE_MATERIALS} constant provides a
 * matching fallback that mirrors the values in {@code walls.xmi}.
 */
public record WallMaterialSpec(String id, String displayName, int hitPoints) {

    public WallMaterialSpec {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id must not be blank");
        if (hitPoints <= 0) throw new IllegalArgumentException("hitPoints must be positive");
    }
}
