package main.game.maze.game.level;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import main.game.maze.runtime.DynamicModel;
import org.eclipse.emf.ecore.EObject;

/**
 * Loads the level catalogue from the levels model with dynamic EMF: {@code levels.ecore} is read at
 * runtime and {@code levels.xmi} is validated against it. Rules the metamodel cannot state are checked
 * here and fail fast: level numbers run 1..n, each zone is weaker
 * than the one outside it, zone materials exist, and a level with a sergeant says how many per difficulty.
 */
public final class LevelCatalogLoader {

    public static final String DEFAULT_ECORE = "/xmi/levels/levels.ecore";
    public static final String DEFAULT_MODEL = "/xmi/levels/levels.xmi";

    private static volatile LevelCatalog defaultCatalog;

    private LevelCatalogLoader() {
    }

    /** The shipped catalogue (loaded once). */
    public static LevelCatalog loadDefault() {
        LevelCatalog catalog = defaultCatalog;
        if (catalog == null) {
            synchronized (LevelCatalogLoader.class) {
                if (defaultCatalog == null) {
                    defaultCatalog = load(DEFAULT_ECORE, DEFAULT_MODEL);
                }
                catalog = defaultCatalog;
            }
        }
        return catalog;
    }

    /** Loads a catalogue from classpath metamodel and model paths. */
    public static LevelCatalog load(String ecorePath, String modelPath) {
        EObject root = DynamicModel.load(LevelCatalogLoader.class, ecorePath, modelPath, "levels");
        List<LevelSpec> levels = new ArrayList<>();
        for (EObject level : DynamicModel.list(root, "levels")) {
            int expected = levels.size() + 1;
            int number = DynamicModel.integer(level, "number");
            if (number != expected) {
                throw new IllegalStateException("levels must be numbered 1..n in order, but level "
                        + number + " is number " + expected);
            }
            levels.add(levelOf(level, levels));
        }
        return new LevelCatalog(levels);
    }

    /** Builds one level; what it does not set itself comes from its base level, when it has one. */
    private static LevelSpec levelOf(EObject level, List<LevelSpec> earlier) {
        int number = DynamicModel.integer(level, "number");
        String name = DynamicModel.string(level, "name");
        int baseNumber = DynamicModel.integer(level, "baseLevel");
        LevelSpec base = null;
        if (baseNumber != 0) {
            if (baseNumber < 1 || baseNumber >= number) {
                throw new IllegalStateException("level " + number + ": baseLevel " + baseNumber
                        + " must be an earlier level");
            }
            base = earlier.get(baseNumber - 1);
        }

        List<LevelSpec.ZoneSpec> zones = zonesOf(level, number);
        if (zones.isEmpty() && base != null) {
            zones = base.zones();
        }

        SergeantSpec sergeant = DynamicModel.value(level, "sergeant") instanceof EObject own ? sergeantOf(own)
                : base != null ? base.sergeant() : null;
        Map<DifficultyTier, Integer> posts = countsOf(level, "sergeantPosts", number, "sergeant");
        if (posts.isEmpty() && base != null) {
            posts = base.sergeantPosts();
        }
        requireAllTiers(sergeant != null, posts, number, "sergeant");

        MineSpec mines = DynamicModel.value(level, "mines") instanceof EObject own ? minesOf(own, number)
                : base != null ? base.mines() : null;

        TeleportGhostSpec ghost = DynamicModel.value(level, "teleportGhost") instanceof EObject own ? ghostOf(own)
                : base != null ? base.teleportGhost() : null;
        Map<DifficultyTier, Integer> ghostCounts = countsOf(level, "ghostCounts", number, "teleporting ghost");
        if (ghostCounts.isEmpty() && base != null) {
            ghostCounts = base.ghostCounts();
        }
        requireAllTiers(ghost != null, ghostCounts, number, "teleporting ghost");

        GuardTowerSpec tower = DynamicModel.value(level, "guardTower") instanceof EObject own ? towerOf(own, number)
                : base != null ? base.guardTower() : null;
        Map<DifficultyTier, Integer> towerCounts = countsOf(level, "towerCounts", number, "guard tower");
        if (towerCounts.isEmpty() && base != null) {
            towerCounts = base.towerCounts();
        }
        requireAllTiers(tower != null, towerCounts, number, "guard tower");

        WallBuilderSpec builder = DynamicModel.value(level, "wallBuilder") instanceof EObject own ? builderOf(own, number)
                : base != null ? base.wallBuilder() : null;
        Map<DifficultyTier, Integer> builderCounts = countsOf(level, "builderCounts", number, "wall builder");
        if (builderCounts.isEmpty() && base != null) {
            builderCounts = base.builderCounts();
        }
        requireAllTiers(builder != null, builderCounts, number, "wall builder");

        LavaSpec lava = DynamicModel.value(level, "lava") instanceof EObject own ? lavaOf(own, number)
                : base != null ? base.lava() : null;

        LastLifeGiftSpec gift = DynamicModel.value(level, "lastLifeGift") instanceof EObject own ? giftOf(own, number)
                : base != null ? base.lastLifeGift() : null;

        main.game.maze.game.ufo.UfoRaidSpec raid = DynamicModel.value(level, "ufoRaid") instanceof EObject own ? raidOf(own, number)
                : base != null ? base.ufoRaid() : null;

        TeleportRulesSpec teleport = DynamicModel.value(level, "teleport") instanceof EObject own ? teleportOf(own)
                : base != null ? base.teleport() : TeleportRulesSpec.DEFAULT;

        double threatCapacityFactor = inherited(level, "threatCapacityFactor", base,
                DynamicModel.decimal(level, "threatCapacityFactor"), base == null ? 0 : base.threatCapacityFactor());
        if (!(threatCapacityFactor > 0)) {
            throw new IllegalStateException("level " + number + ": threatCapacityFactor must be positive");
        }
        boolean showWallMaterials = inherited(level, "showWallMaterials", base,
                Boolean.TRUE.equals(DynamicModel.value(level, "showWallMaterials")), base != null && base.showWallMaterials());
        boolean inheritWeapons = inherited(level, "inheritWeapons", base,
                !Boolean.FALSE.equals(DynamicModel.value(level, "inheritWeapons")), base == null || base.inheritWeapons());
        int maxCascadeDepth = inherited(level, "maxCascadeDepth", base,
                DynamicModel.integer(level, "maxCascadeDepth"), base == null ? -1 : base.maxCascadeDepth());
        return new LevelSpec(number, name, threatCapacityFactor, showWallMaterials, inheritWeapons, maxCascadeDepth,
                zones, sergeant, posts, mines, ghost, ghostCounts, teleport, tower, towerCounts, builder, builderCounts, lava, gift, raid);
    }

    /** The level's own value when it sets one (or has no base level), otherwise the base level's. */
    private static <T> T inherited(EObject level, String feature, LevelSpec base, T own, T fromBase) {
        return base == null || DynamicModel.isSet(level, feature) ? own : fromBase;
    }

    private static List<LevelSpec.ZoneSpec> zonesOf(EObject level, int number) {
        List<LevelSpec.ZoneSpec> zones = new ArrayList<>();
        for (EObject zone : DynamicModel.list(level, "zones")) {
            double share = DynamicModel.decimal(zone, "breakableShare");
            if (share < 0 || share > 1) {
                throw new IllegalStateException("level " + number + ": breakableShare must be between 0 and 1");
            }
            List<String> materialIds = DynamicModel.strings(zone, "materialIds");
            for (String id : materialIds) {
                try {
                    LevelSpec.materialOf(id);
                } catch (IllegalArgumentException unknown) {
                    throw new IllegalStateException("level " + number + ": unknown wall material " + id, unknown);
                }
            }
            zones.add(new LevelSpec.ZoneSpec(DynamicModel.string(zone, "displayName"), share, materialIds));
        }
        for (int zone = 1; zone < zones.size(); zone++) {
            if (LevelSpec.strengthOf(zones.get(zone)) >= LevelSpec.strengthOf(zones.get(zone - 1))) {
                throw new IllegalStateException("level " + number + ": zone " + (zone + 1)
                        + " must have weaker walls than the zone outside it");
            }
        }
        return zones;
    }

    private static Map<DifficultyTier, Integer> countsOf(EObject level, String feature, int number, String what) {
        Map<DifficultyTier, Integer> counts = new EnumMap<>(DifficultyTier.class);
        for (EObject entry : DynamicModel.list(level, feature)) {
            int count = DynamicModel.integer(entry, "count");
            if (count < 0) {
                throw new IllegalStateException("level " + number + ": a " + what + " count cannot be negative");
            }
            counts.put(DifficultyTier.valueOf(DynamicModel.literalName(entry, "difficulty")), count);
        }
        return counts;
    }

    private static void requireAllTiers(boolean present, Map<DifficultyTier, Integer> counts, int number, String what) {
        if (!present) {
            return;
        }
        Set<DifficultyTier> missing = new HashSet<>(List.of(DifficultyTier.values()));
        missing.removeAll(counts.keySet());
        if (!missing.isEmpty()) {
            throw new IllegalStateException("level " + number + ": no " + what + " count for " + missing);
        }
    }

    private static MineSpec minesOf(EObject mines, int number) {
        try {
            return new MineSpec(DynamicModel.integer(mines, "count"), DynamicModel.decimal(mines, "ringRadius"),
                    DynamicModel.decimal(mines, "secondRingRadius"),
                    DynamicModel.decimal(mines, "triggerRadius"), DynamicModel.decimal(mines, "pulseSeconds"),
                    DynamicModel.decimal(mines, "blastRadius"), DynamicModel.integer(mines, "blastDamage"),
                    DynamicModel.integer(mines, "playerDamage"),
                    DynamicModel.decimal(mines, "holeRadius"));
        } catch (IllegalArgumentException invalid) {
            throw new IllegalStateException("level " + number + ": " + invalid.getMessage(), invalid);
        }
    }

    private static TeleportGhostSpec ghostOf(EObject ghost) {
        return new TeleportGhostSpec(DynamicModel.string(ghost, "displayName"), DynamicModel.integer(ghost, "health"),
                DynamicModel.decimal(ghost, "speed"), DynamicModel.string(ghost, "sprite"));
    }

    private static LastLifeGiftSpec giftOf(EObject gift, int number) {
        try {
            return LastLifeGiftSpec.of(DynamicModel.string(gift, "weapon"), DynamicModel.integer(gift, "shots"));
        } catch (IllegalArgumentException invalid) {
            throw new IllegalStateException("level " + number + ": " + invalid.getMessage(), invalid);
        }
    }

    private static main.game.maze.game.ufo.UfoRaidSpec raidOf(EObject raid, int number) {
        try {
            return new main.game.maze.game.ufo.UfoRaidSpec(DynamicModel.string(raid, "triggerTier"),
                    DynamicModel.integer(raid, "ufos"), DynamicModel.decimal(raid, "seconds"),
                    DynamicModel.decimal(raid, "ufoSpeed"), DynamicModel.decimal(raid, "beamEverySeconds"),
                    DynamicModel.decimal(raid, "chargeSeconds"), DynamicModel.decimal(raid, "beamSeconds"),
                    DynamicModel.decimal(raid, "beamRadius"), DynamicModel.decimal(raid, "aimMinDistance"),
                    DynamicModel.decimal(raid, "aimMaxDistance"), DynamicModel.decimal(raid, "damageFraction"),
                    DynamicModel.integer(raid, "soldiers"), DynamicModel.integer(raid, "soldierHealth"),
                    DynamicModel.integer(raid, "soldierDamage"), DynamicModel.decimal(raid, "soldierSight"),
                    DynamicModel.decimal(raid, "soldierSeconds"), DynamicModel.string(raid, "bannerText"),
                    DynamicModel.string(raid, "ufoSprite"), DynamicModel.string(raid, "beamSprite"),
                    DynamicModel.string(raid, "arriveSound"), DynamicModel.string(raid, "beamSound"));
        } catch (IllegalArgumentException invalid) {
            throw new IllegalStateException("level " + number + ": " + invalid.getMessage(), invalid);
        }
    }

    private static LavaSpec lavaOf(EObject lava, int number) {
        try {
            return new LavaSpec(DynamicModel.integer(lava, "streamCount"), DynamicModel.decimal(lava, "width"),
                    DynamicModel.integer(lava, "bridgesPerStream"), DynamicModel.decimal(lava, "bridgeLength"),
                    DynamicModel.decimal(lava, "meander"), DynamicModel.decimal(lava, "wavelength"),
                    DynamicModel.decimal(lava, "flowSpeed"), DynamicModel.decimal(lava, "glow"), DynamicModel.decimal(lava, "crust"),
                    DynamicModel.string(lava, "deathSound"));
        } catch (IllegalArgumentException invalid) {
            throw new IllegalStateException("level " + number + ": " + invalid.getMessage(), invalid);
        }
    }

    private static WallBuilderSpec builderOf(EObject builder, int number) {
        try {
            return new WallBuilderSpec(DynamicModel.string(builder, "displayName"), DynamicModel.integer(builder, "health"),
                    DynamicModel.decimal(builder, "speed"), DynamicModel.string(builder, "sprite"),
                    DynamicModel.decimal(builder, "buildSecondsPerCorridor"), DynamicModel.integer(builder, "startRadiusCells"),
                    DynamicModel.integer(builder, "blockRadiusCells"),
                    DynamicModel.decimal(builder, "spawnRadius"), DynamicModel.string(builder, "wallMaterial"),
                    DynamicModel.string(builder, "buildSound"));
        } catch (IllegalArgumentException invalid) {
            throw new IllegalStateException("level " + number + ": " + invalid.getMessage(), invalid);
        }
    }

    private static GuardTowerSpec towerOf(EObject tower, int number) {
        try {
            return new GuardTowerSpec(DynamicModel.string(tower, "displayName"), DynamicModel.integer(tower, "health"),
                    DynamicModel.decimal(tower, "sightRange"), DynamicModel.integer(tower, "maxWallsOver"),
                    DynamicModel.decimal(tower, "rpgIntervalSeconds"), DynamicModel.decimal(tower, "rpgSpeed"),
                    DynamicModel.decimal(tower, "rpgBlastRadius"), DynamicModel.integer(tower, "rpgPlayerDamage"),
                    DynamicModel.integer(tower, "rpgWallDamage"), DynamicModel.integer(tower, "gatlingRounds"),
                    DynamicModel.decimal(tower, "gatlingRoundIntervalSeconds"), DynamicModel.integer(tower, "gatlingDamage"),
                    DynamicModel.integer(tower, "deathBlastDamage"), DynamicModel.string(tower, "sprite"),
                    DynamicModel.string(tower, "rpgLaunchSound"), DynamicModel.string(tower, "rpgExplosionSound"),
                    DynamicModel.string(tower, "gatlingSound"), DynamicModel.string(tower, "hitSound"),
                    DynamicModel.string(tower, "destroyedSound"));
        } catch (IllegalArgumentException invalid) {
            throw new IllegalStateException("level " + number + ": " + invalid.getMessage(), invalid);
        }
    }

    private static TeleportRulesSpec teleportOf(EObject rules) {
        return new TeleportRulesSpec(DynamicModel.decimal(rules, "cooldownSeconds"),
                DynamicModel.string(rules, "bannerText"), DynamicModel.decimal(rules, "bannerSeconds"),
                DynamicModel.string(rules, "zapSound"), DynamicModel.string(rules, "voiceSound"));
    }

    private static SergeantSpec sergeantOf(EObject sergeant) {
        return new SergeantSpec(DynamicModel.string(sergeant, "displayName"), DynamicModel.integer(sergeant, "health"),
                DynamicModel.decimal(sergeant, "sightRange"), DynamicModel.integer(sergeant, "roundsPerBurst"),
                DynamicModel.integer(sergeant, "roundDamage"), DynamicModel.decimal(sergeant, "roundIntervalSeconds"),
                DynamicModel.decimal(sergeant, "burstCooldownSeconds"), DynamicModel.string(sergeant, "sprite"),
                DynamicModel.string(sergeant, "fireSound"),
                DropWeapon.valueOf(DynamicModel.literalName(sergeant, "dropWeapon")),
                DynamicModel.decimal(sergeant, "postDistanceFromHeart"));
    }
}
