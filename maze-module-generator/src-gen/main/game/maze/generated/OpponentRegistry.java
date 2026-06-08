package main.game.maze.generated;

import java.util.logging.Logger;

/**
 * Generated opponent registry with all character types.
 * @generated from opponents.ecore via FreeMarker template
 */
public class OpponentRegistry {
    private static final Logger LOGGER = Logger.getLogger(OpponentRegistry.class.getName());
    public static final String GAME_NAME = "Testing Game";

    public static void listEnemies() {
        LOGGER.info("Enemy: Ghost Zombie (Health: 120)");
        LOGGER.info("Enemy: Ghost Zombie (Health: 120)");
        LOGGER.info("Enemy: Ghost Zombie (Health: 120)");
        LOGGER.info("Enemy: Classic Zombie (Health: 120)");
        LOGGER.info("Enemy: Angry Zombie (Health: 60)");
        LOGGER.info("Enemy: Classic Zombie (Health: 120)");
        LOGGER.info("Enemy: Pumpkin Bomber Example (Health: 100)");
    }

    public static int getEnemyTypeCount() {
        return 7;
    }

    public static String[] getEnemyTypeNames() {
        return new String[] { "Ghost Zombie", "Ghost Zombie", "Ghost Zombie", "Classic Zombie", "Angry Zombie", "Classic Zombie", "Pumpkin Bomber Example" };
    }
}
