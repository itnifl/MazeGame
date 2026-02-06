package main.game.maze.generated;

/**
 * Generated opponent registry with all character types.
 * @generated from opponents.ecore
 */
public class OpponentRegistry {
    public static final String GAME_NAME = "Testing Game";

    public static void listEnemies() {
        System.out.println("Enemy: Ghost Zombie (Health: 120)");
        System.out.println("Enemy: Ghost Zombie (Health: 120)");
        System.out.println("Enemy: Ghost Zombie (Health: 120)");
        System.out.println("Enemy: Classic Zombie (Health: 120)");
        System.out.println("Enemy: Angry Zombie (Health: 60)");
        System.out.println("Enemy: Classic Zombie (Health: 120)");
        System.out.println("Enemy: Pumpkin Bomber Example (Health: 100)");
    }

    public static int getEnemyTypeCount() {
        return 7;
    }

    public static String[] getEnemyTypeNames() {
        return new String[] { "Ghost Zombie", "Ghost Zombie", "Ghost Zombie", "Classic Zombie", "Angry Zombie", "Classic Zombie", "Pumpkin Bomber Example" };
    }
}
