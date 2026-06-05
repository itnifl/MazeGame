package main.game.maze.config.service;

import java.util.Map;
import main.game.maze.difficulties.EnemyTypes;

/**
 * Given a profile key, returns the final enemy composition.
 */
public interface CompositionResolver {

    /**
     * Resolves the enemy composition for the given profile.
     *
     * @param profile profile key (for example "easy", "normal", "hard")
     * @return map of enemy type → count
     */
    Map<EnemyTypes, Integer> resolve(String profile);
}


