package main.game.maze.libgdx.model;

import java.util.List;

/**
 * Snapshot of render and movement data loaded from shared models.
 */
public record RuntimeVisualModel(
        String playerImagePath,
        float playerSpeed,
        float playerSize,
        String wallImagePath,
        String goalImagePath,
        float goalX,
        float goalY,
        float goalSize,
        List<EnemySpawn> enemies) {
}
