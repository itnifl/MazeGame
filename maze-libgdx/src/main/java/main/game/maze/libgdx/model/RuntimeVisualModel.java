package main.game.maze.libgdx.model;

import java.util.List;
import java.util.Objects;

/**
 * Snapshot of render and movement data loaded from shared models.
 */
public record RuntimeVisualModel(
        String playerImagePath,
        String playerDeathImagePath,
        float playerSpeed,
        float playerSize,
        int playerMaxHitPoints,
        String backgroundImagePath,
        String wallImagePath,
        String goalImagePath,
        float goalX,
        float goalY,
        float goalSize,
        List<EnemySpawn> enemies) {

        public RuntimeVisualModel {
                enemies = List.copyOf(Objects.requireNonNull(enemies, "enemies must not be null"));
        }
}
