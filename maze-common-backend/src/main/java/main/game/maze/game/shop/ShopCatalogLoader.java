package main.game.maze.game.shop;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.runtime.DynamicModel;
import org.eclipse.emf.ecore.EObject;

/**
 * Loads the shop from the shop model with dynamic EMF: the metamodel ({@code shop.ecore}) is read at runtime and the
 * instance ({@code shop.xmi}, generated from {@code shop.mazegame} by the MazeGame DSL) is validated against it, so what
 * the shop sells and what it costs live in the model rather than in code.
 */
public final class ShopCatalogLoader {

    public static final String DEFAULT_ECORE = "/xmi/shop/shop.ecore";
    public static final String DEFAULT_MODEL = "/xmi/shop/shop.xmi";

    private static volatile ShopCatalog defaultCatalog;

    private ShopCatalogLoader() {
    }

    /** The shipped shop (loaded once). */
    public static ShopCatalog loadDefault() {
        ShopCatalog catalog = defaultCatalog;
        if (catalog == null) {
            synchronized (ShopCatalogLoader.class) {
                if (defaultCatalog == null) {
                    defaultCatalog = load(DEFAULT_ECORE, DEFAULT_MODEL);
                }
                catalog = defaultCatalog;
            }
        }
        return catalog;
    }

    /** Loads a shop from classpath metamodel and model paths. */
    public static ShopCatalog load(String ecorePath, String modelPath) {
        EObject root = DynamicModel.load(ShopCatalogLoader.class, ecorePath, modelPath, "shop");
        List<ShopItem> items = new ArrayList<>();
        for (EObject item : DynamicModel.list(root, "items")) {
            items.add(new ShopItem(
                    ShopItemKind.valueOf(DynamicModel.literalName(item, "kind")),
                    DynamicModel.string(item, "displayName"),
                    DynamicModel.string(item, "description"),
                    ShopTier.valueOf(DynamicModel.literalName(item, "tier")),
                    DynamicModel.integer(item, "price"),
                    DynamicModel.integer(item, "amount"),
                    DynamicModel.decimal(item, "factor")));
        }
        return new ShopCatalog(items);
    }
}
