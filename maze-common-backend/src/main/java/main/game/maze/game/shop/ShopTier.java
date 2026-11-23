package main.game.maze.game.shop;

/** How strong and costly a shop item is; mirrors the {@code ShopTier} enum of {@code xmi/shop/shop.ecore}. */
public enum ShopTier {
    /** Low cost, weak. */
    CHEAP,
    /** Medium cost, medium strength. */
    MEDIUM,
    /** Expensive, strong. */
    EXPENSIVE;

    /** As shown in the shop. */
    public String label() {
        return switch (this) {
            case CHEAP -> "Cheap";
            case MEDIUM -> "Medium";
            case EXPENSIVE -> "Expensive";
        };
    }
}
