package main.game.maze.config;
import main.game.maze.difficulties.*;

import java.util.Map;
// rules established by XmiRulesLoader throughout xmi files
public record ProfileRules(
    String name, // profile name
    Integer enemyCount, // batch objective
    Map<EnemyTypes, Double> ratios, // mixing character types ratio (normalized; not used if Override occurs)
    Map<EnemyTypes, Integer> countsOverride, // fixed counts when Overriding
    Map<EnemyTypes, Integer> caps // limits as maxCount
) {}
