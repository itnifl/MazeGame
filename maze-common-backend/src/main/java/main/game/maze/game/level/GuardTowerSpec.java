package main.game.maze.game.level;

/**
 * The guard towers, as defined in {@code levels.xmi}: stationary, very tough, they shoot over a few walls and fire
 * an rpg every {@code rpgIntervalSeconds} with a burst of gatling rounds halfway between two rpgs.
 *
 * @param maxWallsOver   walls that may lie between a tower and the player for the tower to shoot
 * @param rpgSpeed       pixels per second the rocket flies
 * @param rpgBlastRadius reach of a rocket explosion
 * @param rpgPlayerDamage damage to the player inside the blast
 * @param rpgWallDamage  damage of the explosion to walls and other enemies
 * @param deathBlastDamage damage of the explosion when the tower is blown up
 */
public record GuardTowerSpec(String displayName, int health, double sightRange, int maxWallsOver,
                             double rpgIntervalSeconds, double rpgSpeed, double rpgBlastRadius, int rpgPlayerDamage,
                             int rpgWallDamage, int gatlingRounds, double gatlingRoundIntervalSeconds, int gatlingDamage,
                             int deathBlastDamage, String sprite, String rpgLaunchSound, String rpgExplosionSound,
                             String gatlingSound, String hitSound, String destroyedSound) {

    /** Spawn ids of guard towers start with this, so a frontend can tell them from the other enemies. */
    public static final String SPAWN_ID_PREFIX = "tower";

    public static boolean isTowerId(String spawnId) {
        return spawnId != null && spawnId.startsWith(SPAWN_ID_PREFIX);
    }

    public GuardTowerSpec {
        if (health <= 0 || sightRange <= 0 || maxWallsOver < 0 || rpgIntervalSeconds <= 0 || rpgSpeed <= 0
                || rpgBlastRadius <= 0 || rpgPlayerDamage < 0 || rpgWallDamage < 0 || gatlingRounds < 0
                || gatlingRoundIntervalSeconds <= 0 || gatlingDamage < 0 || deathBlastDamage < 0) {
            throw new IllegalArgumentException("guard tower values must be positive: " + displayName);
        }
        if (gatlingRounds * gatlingRoundIntervalSeconds > rpgIntervalSeconds / 2) {
            throw new IllegalArgumentException("a gatling burst must fit between two rpgs (half the rpg interval): " + displayName);
        }
    }

    /** Reach of the explosion when the tower is blown up: a little wider than a rocket's. */
    public double deathBlastRadius() {
        return rpgBlastRadius * 1.5;
    }

    /** When, within one rpg cycle, the gatling burst starts: halfway between two rpgs. */
    public double gatlingStartSeconds() {
        return rpgIntervalSeconds / 2;
    }
}
