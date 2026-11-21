package main.game.maze.game.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.WallMaterialSpec;
import main.game.maze.mazeworld.WallZoneSpec;
import main.game.maze.mazeworld.ZoneGeometry;

/**
 * One level of the game, as defined in {@code levels.xmi}.
 *
 * @param threatCapacityFactor multiplies the difficulty's maximum threat
 * @param showWallMaterials    draw each wall with the sprite of its material
 * @param maxCascadeDepth      how many steps a collapse spreads from a destroyed wall; negative = no limit
 * @param inheritWeapons       start with the weapons of the previous level
 * @param zones                empty for the classic random walls, otherwise one or more zones, outer first
 * @param sergeant             the guard of the heart, or null
 * @param sergeantPosts        sergeants per difficulty tier
 * @param mines                the mines in a circle round every sergeant, or null
 * @param teleportGhost        the ghost that teleports the player, or null
 * @param ghostCounts          teleporting ghosts per difficulty tier
 * @param teleport             what a teleport does (cooldown, word, sounds); never null
 * @param guardTower           the guard towers on the outer parts of the map, or null
 * @param towerCounts          guard towers per difficulty tier
 * @param wallBuilder          the wall builders round the heart, or null
 * @param builderCounts        wall builders per difficulty tier
 */
public record LevelSpec(int number, String name, double threatCapacityFactor, boolean showWallMaterials,
                        boolean inheritWeapons, int maxCascadeDepth, List<ZoneSpec> zones,
                        SergeantSpec sergeant, Map<DifficultyTier, Integer> sergeantPosts,
                        MineSpec mines, TeleportGhostSpec teleportGhost, Map<DifficultyTier, Integer> ghostCounts,
                        TeleportRulesSpec teleport, GuardTowerSpec guardTower, Map<DifficultyTier, Integer> towerCounts,
                        WallBuilderSpec wallBuilder, Map<DifficultyTier, Integer> builderCounts) {

    /** Stand-in hit points of a wall that cannot be broken, when comparing how strong zones are. */
    static final double SOLID_WALL_STRENGTH = 200.0;
    /** Same fixed seed as the classic walls ({@code GameMazeWorld}), so a layout is reproducible. */
    private static final long WALL_SEED = 42L;

    /** One concentric zone: how many of its walls can be broken, and from which materials. */
    public record ZoneSpec(String displayName, double breakableShare, List<String> materialIds) {
        public ZoneSpec {
            materialIds = List.copyOf(materialIds);
        }
    }

    public LevelSpec {
        zones = List.copyOf(zones);
        sergeantPosts = Map.copyOf(sergeantPosts);
        ghostCounts = Map.copyOf(ghostCounts);
        teleport = teleport == null ? TeleportRulesSpec.DEFAULT : teleport;
        towerCounts = Map.copyOf(towerCounts);
        builderCounts = Map.copyOf(builderCounts);
    }

    public boolean hasMines() {
        return mines != null && hasSergeants();
    }

    public boolean hasTeleportGhosts() {
        return teleportGhost != null;
    }

    public boolean hasBuilders() {
        return wallBuilder != null;
    }

    /** Wall builders on this difficulty (0 without builders). */
    public int builderCount(DifficultyTier tier) {
        return wallBuilder == null ? 0 : builderCounts.getOrDefault(tier, 0);
    }

    /** The wall material with this id (walls.xmi), as the world's walls use it. */
    public static WallMaterialSpec wallMaterial(String id) {
        return materialOf(id);
    }

    public boolean hasTowers() {
        return guardTower != null;
    }

    /** Guard towers on this difficulty (0 without towers). */
    public int towerCount(DifficultyTier tier) {
        return guardTower == null ? 0 : towerCounts.getOrDefault(tier, 0);
    }

    /** Teleporting ghosts on this difficulty (0 without that ghost). */
    public int ghostCount(DifficultyTier tier) {
        return teleportGhost == null ? 0 : ghostCounts.getOrDefault(tier, 0);
    }

    public boolean hasZones() {
        return !zones.isEmpty();
    }

    public boolean hasSergeants() {
        return sergeant != null;
    }

    /** Sergeants standing guard on this difficulty (0 without a sergeant). */
    public int sergeantCount(DifficultyTier tier) {
        return sergeant == null ? 0 : sergeantPosts.getOrDefault(tier, 0);
    }

    /** The threat the level may fill with enemies: the difficulty's maximum x this level's factor, rounded. */
    public int maxThreat(int difficultyMaxThreat) {
        return (int) Math.round(difficultyMaxThreat * threatCapacityFactor);
    }

    /**
     * Gives a freshly generated maze this level's walls: a level with zones assigns the materials by zone (the
     * maze itself is untouched), a level without keeps the classic random walls; and the level's cascade limit.
     */
    public void applyWalls(GameMazeWorld world, int boardWidth, int boardHeight) {
        if (world == null) {
            return;
        }
        world.setMaxCascadeDepth(maxCascadeDepth);
        if (hasZones()) {
            world.assignZonedWalls(WALL_SEED, ZoneGeometry.forBoard(boardWidth, boardHeight, zones.size()), wallZones());
        }
    }

    /** The wall rules of the zones, outer zone first, for {@link GameMazeWorld#assignZonedWalls}. */
    public List<WallZoneSpec> wallZones() {
        List<WallZoneSpec> rules = new ArrayList<>();
        for (ZoneSpec zone : zones) {
            List<WallMaterialSpec> materials = new ArrayList<>();
            for (String id : zone.materialIds()) {
                materials.add(materialOf(id));
            }
            rules.add(new WallZoneSpec(zone.breakableShare(), materials));
        }
        return rules;
    }

    /**
     * How strong a zone's walls are: the solid walls count as {@value #SOLID_WALL_STRENGTH} hit points, the
     * breakable ones by their materials' average hit points.
     */
    public static double strengthOf(ZoneSpec zone) {
        double total = 0;
        for (String id : zone.materialIds()) {
            total += materialOf(id).hitPoints();
        }
        double average = total / zone.materialIds().size();
        return (1 - zone.breakableShare()) * SOLID_WALL_STRENGTH + zone.breakableShare() * average;
    }

    /** The breakable wall material of this id, or an {@link IllegalArgumentException}. */
    static WallMaterialSpec materialOf(String id) {
        for (WallMaterialSpec spec : GameMazeWorld.DEFAULT_BREAKABLE_MATERIALS) {
            if (spec.id().equals(id)) {
                return spec;
            }
        }
        throw new IllegalArgumentException("unknown wall material " + id);
    }
}
