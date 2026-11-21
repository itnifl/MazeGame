package main.game.maze.game.level;

/**
 * The wall builders, as defined in {@code levels.xmi}: construction worker zombies that build breakable walls to block
 * the way to the heart.
 *
 * @param buildSecondsPerCorridor seconds for one corridor width of wall
 * @param startRadiusCells        the builders walk out until they are this many cells from the heart and wall up the corridors
 *                                there (the first, outermost ring)
 * @param blockRadiusCells        the innermost ring: when a ring is closed they move one corridor width closer, down to this
 * @param spawnRadius             how far from the heart the builders start, in pixels
 * @param wallMaterial            id of the wall material (walls.xmi) the walls are made of
 */
public record WallBuilderSpec(String displayName, int health, double speed, String sprite, double buildSecondsPerCorridor,
                              int startRadiusCells, int blockRadiusCells, double spawnRadius, String wallMaterial, String buildSound) {

    /** Spawn ids of wall builders start with this, so a frontend can tell them from the other enemies. */
    public static final String SPAWN_ID_PREFIX = "builder";
    /** A corridor is this many wall segments wide (60 px of 20 px segments). */
    public static final int SEGMENTS_PER_CORRIDOR = 3;

    public static boolean isBuilderId(String spawnId) {
        return spawnId != null && spawnId.startsWith(SPAWN_ID_PREFIX);
    }

    public WallBuilderSpec {
        if (health <= 0 || speed <= 0 || buildSecondsPerCorridor <= 0 || blockRadiusCells < 1 || startRadiusCells < blockRadiusCells || spawnRadius <= 0
                || wallMaterial == null || wallMaterial.isBlank()) {
            throw new IllegalArgumentException("wall builder values must be positive: " + displayName);
        }
    }

    /** Seconds for one piece of wall (a corridor width is {@link #SEGMENTS_PER_CORRIDOR} pieces). */
    public double secondsPerSegment() {
        return buildSecondsPerCorridor / SEGMENTS_PER_CORRIDOR;
    }
}
