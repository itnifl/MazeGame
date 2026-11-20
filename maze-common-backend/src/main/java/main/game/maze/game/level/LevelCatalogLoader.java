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
            levels.add(levelOf(level));
        }
        for (int index = 0; index < levels.size(); index++) {
            if (levels.get(index).number() != index + 1) {
                throw new IllegalStateException("levels must be numbered 1.." + levels.size() + " in order, but level "
                        + levels.get(index).number() + " is number " + (index + 1));
            }
        }
        return new LevelCatalog(levels);
    }

    private static LevelSpec levelOf(EObject level) {
        int number = DynamicModel.integer(level, "number");
        String name = DynamicModel.string(level, "name");

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

        SergeantSpec sergeant = null;
        List<EObject> sergeants = DynamicModel.value(level, "sergeant") instanceof EObject one ? List.of(one) : List.of();
        if (!sergeants.isEmpty()) {
            sergeant = sergeantOf(sergeants.get(0));
        }
        Map<DifficultyTier, Integer> posts = new EnumMap<>(DifficultyTier.class);
        for (EObject post : DynamicModel.list(level, "sergeantPosts")) {
            int count = DynamicModel.integer(post, "count");
            if (count < 0) {
                throw new IllegalStateException("level " + number + ": a sergeant count cannot be negative");
            }
            posts.put(DifficultyTier.valueOf(DynamicModel.literalName(post, "difficulty")), count);
        }
        if (sergeant != null) {
            Set<DifficultyTier> missing = new HashSet<>(List.of(DifficultyTier.values()));
            missing.removeAll(posts.keySet());
            if (!missing.isEmpty()) {
                throw new IllegalStateException("level " + number + ": no sergeant count for " + missing);
            }
        }
        double threatCapacityFactor = DynamicModel.decimal(level, "threatCapacityFactor");
        if (!(threatCapacityFactor > 0)) {
            throw new IllegalStateException("level " + number + ": threatCapacityFactor must be positive");
        }
        return new LevelSpec(number, name, threatCapacityFactor,
                Boolean.TRUE.equals(DynamicModel.value(level, "showWallMaterials")),
                !Boolean.FALSE.equals(DynamicModel.value(level, "inheritWeapons")),
                DynamicModel.integer(level, "maxCascadeDepth"), zones, sergeant, posts);
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
