package main.game.maze.config;

import java.util.Map;
// rules established by XmiRulesLoader throughout xmi files
public record ProfileRules(
    String name, // profile name
    Integer enemyCount, // batch objective
    Map<EnemyType, Double> ratios, // mixing character types ratio (normalized; not used if Override occurs)
    Map<EnemyType, Integer> countsOverride, // fixed counts when Overriding
    Map<EnemyType, Integer> caps // limits as maxCount
) {}
