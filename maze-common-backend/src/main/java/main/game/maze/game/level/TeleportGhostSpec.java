package main.game.maze.game.level;

/**
 * The ghost that teleports the player, as defined in {@code levels.xmi}: it does not hurt, a touch sends the
 * player to a random spot at the outer edge of the map (what the teleport then does is the level's
 * {@link TeleportRulesSpec}).
 *
 */
public record TeleportGhostSpec(String displayName, int health, double speed, String sprite) {

    /** Spawn ids of these ghosts start with this, so a frontend can tell them from the other enemies. */
    public static final String SPAWN_ID_PREFIX = "portalghost";

    public static boolean isPortalGhostId(String spawnId) {
        return spawnId != null && spawnId.startsWith(SPAWN_ID_PREFIX);
    }

    public TeleportGhostSpec {
        if (health <= 0 || speed <= 0) {
            throw new IllegalArgumentException("teleport ghost values must be positive: " + displayName);
        }
    }
}
