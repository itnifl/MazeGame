package main.game.maze.game.items;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.runtime.DynamicModel;
import org.eclipse.emf.ecore.EObject;

/**
 * Loads the pickup catalogue from the items model with dynamic EMF: the metamodel
 * ({@code items.ecore}) is read at runtime and the instance ({@code items.xmi}) is validated
 * against it, so the item rules live in the model rather than in code.
 */
public final class PickupCatalogLoader {

    public static final String DEFAULT_ECORE = "/xmi/items/items.ecore";
    public static final String DEFAULT_MODEL = "/xmi/items/items.xmi";

    private static volatile PickupCatalog defaultCatalog;

    private PickupCatalogLoader() {
    }

    /** The shipped catalogue (loaded once). */
    public static PickupCatalog loadDefault() {
        PickupCatalog catalog = defaultCatalog;
        if (catalog == null) {
            synchronized (PickupCatalogLoader.class) {
                if (defaultCatalog == null) {
                    defaultCatalog = load(DEFAULT_ECORE, DEFAULT_MODEL);
                }
                catalog = defaultCatalog;
            }
        }
        return catalog;
    }

    /** Loads a catalogue from classpath metamodel and model paths. */
    public static PickupCatalog load(String ecorePath, String modelPath) {
        EObject root = DynamicModel.load(PickupCatalogLoader.class, ecorePath, modelPath, "items");

        List<PickupCatalog.Cap> caps = new ArrayList<>();
        for (EObject cap : DynamicModel.list(root, "caps")) {
            caps.add(new PickupCatalog.Cap(DynamicModel.integer(cap, "minBoardWidth"), DynamicModel.integer(cap, "maxItems")));
        }
        Map<PickupKind, PickupSpec> specs = new EnumMap<>(PickupKind.class);
        for (EObject item : DynamicModel.list(root, "items")) {
            PickupKind kind = PickupKind.valueOf(DynamicModel.literalName(item, "kind"));
            specs.put(kind, new PickupSpec(kind, DynamicModel.string(item, "displayName"), DynamicModel.string(item, "sprite"),
                    DynamicModel.string(item, "pickupSound"), DynamicModel.string(item, "useSound"),
                    DynamicModel.integer(item, "spawnWeight"), DynamicModel.integer(item, "amount"),
                    DynamicModel.decimal(item, "durationSeconds"), DynamicModel.integer(item, "damage"),
                    DynamicModel.decimal(item, "range"), DynamicModel.decimal(item, "chargeSeconds"),
                    DynamicModel.string(item, "chargeSound"), DynamicModel.decimal(item, "blastRadius"),
                    DynamicModel.decimal(item, "fuseSeconds"), DynamicModel.integer(item, "units"),
                    DynamicModel.integer(item, "piercePower"), BlastShape.valueOf(DynamicModel.literalName(item, "blastShape")),
                    DynamicModel.integer(item, "unitHealth"), DynamicModel.decimal(item, "lureRadius")));
        }
        double itemSize = DynamicModel.decimal(root, "itemSizeFactor") * StageConstants.PlayerCharacterXYSize;
        return new PickupCatalog(DynamicModel.decimal(root, "spawnIntervalSeconds"), itemSize,
                DynamicModel.decimal(root, "minDistanceFromPlayer"), caps, specs);
    }
}
