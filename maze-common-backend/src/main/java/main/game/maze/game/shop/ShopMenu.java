package main.game.maze.game.shop;

import java.util.ArrayList;
import java.util.List;

/**
 * The shop screen's model, shared by the JavaFX and libGDX views (MVC: the views only draw {@link #rows()} and
 * {@link #status()}, and pass the keys and clicks here). One row per item, cheap first; Up/Down move the selection,
 * Enter or Space buys the selected item.
 */
public final class ShopMenu {

    /** One line of the shop: the item, what the player has of it, and whether it can be bought now. */
    public record Row(ShopItem item, int stock, boolean active, boolean affordable, boolean selected) {

        /** e.g. {@code "Grenades x5 - 600"}. */
        public String title() {
            String amount = item.kind().isBoost() ? "" : " x" + item.amount();
            return item.displayName() + amount;
        }

        /** e.g. {@code "have 3"}, {@code "ACTIVE"} or {@code ""}. */
        public String ownedText() {
            if (active) {
                return "ACTIVE";
            }
            return stock > 0 ? "have " + stock : "";
        }
    }

    /**
     * The icon of a shop item, the same in both shop screens: a weapon's own sprite from the items model; the shield boost
     * shows the shield cube and the flame boost the bomb.
     */
    public static String iconOf(ShopItemKind kind, main.game.maze.game.items.PickupCatalog items) {
        return switch (kind) {
            case SHIELD_BOOST -> items.spec(main.game.maze.game.items.PickupKind.SHIELD).sprite();
            case FLAME_BOOST -> items.spec(main.game.maze.game.items.PickupKind.BOMB).sprite();
            default -> items.spec(kind.weapon().pickup()).sprite();
        };
    }

    /** Played when something is bought; a refused purchase plays {@link #REFUSED_SOUND}. */
    public static final String BUY_SOUND = "/main/game/maze/sounds/shop-buy.wav";
    public static final String REFUSED_SOUND = "/main/game/maze/sounds/weapon-switch.wav";

    /** The sound for the outcome of a purchase. */
    public static String soundFor(LevelShop.Result result) {
        return result == LevelShop.Result.BOUGHT ? BUY_SOUND : REFUSED_SOUND;
    }

    public static final String TITLE = "SHOP - pay with your score";
    public static final String HELP = "Up/Down choose, Enter or Space buy, Esc close";

    private final LevelShop shop;
    private int selected;
    private String status = "";

    public ShopMenu(LevelShop shop) {
        this.shop = shop;
    }

    /** Opening the shop: the first item selected, no message. */
    public void reset() {
        selected = 0;
        status = "";
    }

    public List<Row> rows() {
        List<ShopItem> items = shop.catalog().items();
        List<Row> rows = new ArrayList<>(items.size());
        for (int i = 0; i < items.size(); i++) {
            ShopItem item = items.get(i);
            rows.add(new Row(item, shop.stock(item), shop.isActive(item), shop.canAfford(item), i == selected));
        }
        return rows;
    }

    public int selectedIndex() {
        return selected;
    }

    public void moveUp() {
        int size = shop.catalog().items().size();
        selected = size == 0 ? 0 : (selected - 1 + size) % size;
    }

    public void moveDown() {
        int size = shop.catalog().items().size();
        selected = size == 0 ? 0 : (selected + 1) % size;
    }

    /** Selects a row (a mouse click on it); ignored outside the list. */
    public void select(int index) {
        if (index >= 0 && index < shop.catalog().items().size()) {
            selected = index;
        }
    }

    public LevelShop.Result buySelected() {
        List<ShopItem> items = shop.catalog().items();
        if (items.isEmpty()) {
            return LevelShop.Result.CLOSED;
        }
        ShopItem item = items.get(selected);
        LevelShop.Result result = shop.buy(item);
        status = message(result, item);
        return result;
    }

    /** The score the player can spend. */
    public int credits() {
        return shop.credits();
    }

    /** e.g. {@code "Score to spend: 12 400"}. */
    public String creditsText() {
        return "Score to spend: " + credits();
    }

    /** The outcome of the last purchase ("Bought Grenades", "Not enough score", ...), or empty. */
    public String status() {
        return status;
    }

    static String message(LevelShop.Result result, ShopItem item) {
        return switch (result) {
            case BOUGHT -> "Bought " + item.displayName() + " for " + item.price();
            case NOT_ENOUGH_SCORE -> "Not enough score for " + item.displayName() + " (" + item.price() + ")";
            case ALREADY_ACTIVE -> item.displayName() + " is already on for the next level";
            case CLOSED -> "The shop is open only between levels";
        };
    }
}
