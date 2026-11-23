package main.game.maze.game.shop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Everything the shop sells, in the order of the shop model (cheap first), and from which level each tier is sold.
 * Loaded by {@link ShopCatalogLoader}.
 *
 * @param unlocks the first level a tier is sold for (the shop before that level and every later one); a tier that is
 *                not here is always sold
 */
public record ShopCatalog(List<ShopItem> items, Map<ShopTier, Integer> unlocks) {

    public ShopCatalog {
        items = List.copyOf(items);
        unlocks = Collections.unmodifiableMap(unlocks.isEmpty() ? new EnumMap<>(ShopTier.class) : new EnumMap<>(unlocks));
    }

    /** A shop where every tier is always sold. */
    public ShopCatalog(List<ShopItem> items) {
        this(items, Map.of());
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

    /** The first level the tier is sold for (1 when it is always sold). */
    public int unlockLevel(ShopTier tier) {
        return unlocks.getOrDefault(tier, 1);
    }

    /** Whether the tier is sold in the shop before {@code nextLevel}. */
    public boolean sold(ShopTier tier, int nextLevel) {
        return nextLevel >= unlockLevel(tier);
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
