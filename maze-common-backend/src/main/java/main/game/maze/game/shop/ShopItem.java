package main.game.maze.game.shop;

/**
 * One item of the shop between levels, as defined in {@code xmi/shop/shop.xmi}.
 *
 * @param price  score points it costs
 * @param amount weapons: uses one purchase gives
 * @param factor boosts: how many times longer the shield lasts / how many times stronger the bombs are
 */
public record ShopItem(ShopItemKind kind, String displayName, String description, ShopTier tier, int price, int amount,
                       double factor) {

    public ShopItem {
        if (kind == null || tier == null) {
            throw new IllegalArgumentException("a shop item needs a kind and a tier");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("a shop item costs something: " + kind + " price " + price);
        }
        if (!kind.isBoost() && amount <= 0) {
            throw new IllegalArgumentException("a weapon in the shop gives at least one use: " + kind);
        }
        if (kind.isBoost() && !(factor > 1.0)) {
            throw new IllegalArgumentException("a boost makes something stronger (factor above 1): " + kind);
        }
        displayName = displayName == null || displayName.isBlank() ? kind.name() : displayName;
        description = description == null ? "" : description;
    }
}
