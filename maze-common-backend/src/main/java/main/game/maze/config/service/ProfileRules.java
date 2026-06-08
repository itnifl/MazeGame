package main.game.maze.config.service;

import java.util.Map;
import main.game.maze.difficulties.EnemyTypes;

/**
 * Rules established by XmiRulesLoader from XMI files.
 *
 * @param name           profile name
 * @param enemyCount     target number of enemies in the batch
 * @param ratios         ratio for each enemy type (normalized; ignored if countsOverride is used)
 * @param countsOverride fixed counts per enemy type (overrides ratios when not empty)
 * @param caps           maximum allowed count per enemy type
 */
public record ProfileRules(
        String name,
        Integer enemyCount,
        Map<EnemyTypes, Double> ratios,
        Map<EnemyTypes, Integer> countsOverride,
        Map<EnemyTypes, Integer> caps
) { }
