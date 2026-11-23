package main.game.maze.game.shop;

import java.util.ArrayList;
import java.util.List;

/** Everything the shop sells, in the order of the shop model (cheap first). Loaded by {@link ShopCatalogLoader}. */
public record ShopCatalog(List<ShopItem> items) {

    public ShopCatalog {
        items = List.copyOf(items);
    }

    /** The items of one tier, in model order. */
    public List<ShopItem> tier(ShopTier tier) {
        List<ShopItem> result = new ArrayList<>();
        for (ShopItem item : items) {
            if (item.tier() == tier) {
                result.add(item);
            }
        }
        return List.copyOf(result);
    }

    public ShopItem item(ShopItemKind kind) {
        for (ShopItem item : items) {
            if (item.kind() == kind) {
                return item;
            }
        }
        throw new IllegalArgumentException("the shop does not sell " + kind);
    }
}
