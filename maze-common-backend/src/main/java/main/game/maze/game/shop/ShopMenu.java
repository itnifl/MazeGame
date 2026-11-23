package main.game.maze.game.shop;

import java.util.ArrayList;
import java.util.List;

/**
 * The shop screen's model, shared by the JavaFX and libGDX views (MVC: the views only draw {@link #rows()} and
 * {@link #status()}, and pass the keys and clicks here). One row per item, cheap first; Up/Down move the selection,
 * Enter or Space buys the selected item.
 */
public final class ShopMenu {

    /**
     * One line of the shop: the item, what the player has of it, whether it can be bought now, and the level whose shop
     * first sells it ({@code lockedUntil}: 0 when it is sold now).
     */
    public record Row(ShopItem item, int stock, boolean active, boolean affordable, boolean selected, int lockedUntil) {

        public boolean locked() {
            return lockedUntil > 0;
        }

        /** e.g. {@code "Grenades x5 - 600"}. */
        public String title() {
            String amount = item.kind().isBoost() ? "" : " x" + item.amount();
            return item.displayName() + amount;
        }

        /** e.g. {@code "have 3"}, {@code "ACTIVE"}, {@code "after level 3"} (not sold yet) or {@code ""}. */
        public String ownedText() {
            if (locked()) {
                return afterLevelText(lockedUntil);
            }
            if (active) {
                return "ACTIVE";
            }
            return stock > 0 ? "have " + stock : "";
        }
    }

    /** A row on a shelf, with its place in {@link #rows()} (what {@link #select} takes). */
    public record ShelfItem(int index, Row row) {
    }

    /**
     * The items of one tier, as both shop screens show them side by side: the tier, the level whose shop first sells it
     * ({@code lockedUntil}: 0 when it is sold now) and its items, in menu order.
     */
    public record Shelf(ShopTier tier, int lockedUntil, List<ShelfItem> items) {
        public Shelf {
            items = List.copyOf(items);
        }

        public boolean locked() {
            return lockedUntil > 0;
        }

        /** e.g. {@code "after level 3"} for a tier not sold yet, else empty. */
        public String lockedText() {
            return locked() ? afterLevelText(lockedUntil) : "";
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

    /** The banner of the shop screen; {@link #SUBTITLE} under it. */
    public static final String TITLE = "SUPPLY DEPOT";
    public static final String SUBTITLE = "Pay with your score - what you buy is yours for the next level";
    public static final String HELP = "Arrows choose, Enter or Space buy, Esc close";

    private final LevelShop shop;
    private int selected;
    private String status = "";
    private LevelShop.Result lastResult;

    public ShopMenu(LevelShop shop) {
        this.shop = shop;
    }

    /** Opening the shop: the first item selected, no message. */
    public void reset() {
        selected = 0;
        status = "";
        lastResult = null;
    }

    public List<Row> rows() {
        List<ShopItem> items = shop.catalog().items();
        List<Row> rows = new ArrayList<>(items.size());
        for (int i = 0; i < items.size(); i++) {
            ShopItem item = items.get(i);
            int lockedUntil = shop.isSold(item) ? 0 : shop.catalog().unlockLevel(item.tier());
            rows.add(new Row(item, shop.stock(item), shop.isActive(item), lockedUntil == 0 && shop.canAfford(item),
                    i == selected, lockedUntil));
        }
        return rows;
    }

    /** The rows grouped by tier, cheapest tier first; a tier without items has no shelf. */
    public List<Shelf> shelves() {
        List<Row> rows = rows();
        List<Shelf> shelves = new ArrayList<>();
        for (ShopTier tier : ShopTier.values()) {
            List<ShelfItem> items = new ArrayList<>();
            for (int i = 0; i < rows.size(); i++) {
                if (rows.get(i).item().tier() == tier) {
                    items.add(new ShelfItem(i, rows.get(i)));
                }
            }
            if (!items.isEmpty()) {
                shelves.add(new Shelf(tier, items.get(0).row().lockedUntil(), items));
            }
        }
        return shelves;
    }

    /** Left: the item at the same height on the shelf to the left (the lowest one when that shelf is shorter). */
    public void moveLeft() {
        moveAcross(-1);
    }

    /** Right: the item at the same height on the shelf to the right. */
    public void moveRight() {
        moveAcross(1);
    }

    private void moveAcross(int step) {
        List<Shelf> shelves = shelves();
        for (int s = 0; s < shelves.size(); s++) {
            List<ShelfItem> items = shelves.get(s).items();
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).index() == selected) {
                    List<ShelfItem> next = shelves.get(Math.floorMod(s + step, shelves.size())).items();
                    selected = next.get(Math.min(i, next.size() - 1)).index();
                    return;
                }
            }
        }
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
        lastResult = result;
        return result;
    }

    /** Whether the last purchase went through (the views show {@link #status()} as good news), false before any. */
    public boolean statusIsGood() {
        return lastResult == LevelShop.Result.BOUGHT;
    }

    /** What the selected item does (its description in the shop model), or empty. */
    public String briefing() {
        List<ShopItem> items = shop.catalog().items();
        if (items.isEmpty() || items.get(selected).description() == null) {
            return "";
        }
        return items.get(selected).description();
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

    /** The shop before level {@code level} is the one after level {@code level - 1}: {@code "after level 3"}. */
    static String afterLevelText(int level) {
        return "after level " + (level - 1);
    }

    String message(LevelShop.Result result, ShopItem item) {
        return switch (result) {
            case BOUGHT -> "Bought " + item.displayName() + " for " + item.price();
            case NOT_ENOUGH_SCORE -> "Not enough score for " + item.displayName() + " (" + item.price() + ")";
            case ALREADY_ACTIVE -> item.displayName() + " is already on for the next level";
            case LOCKED -> item.tier().label() + " items are sold " + afterLevelText(shop.catalog().unlockLevel(item.tier()));
            case CLOSED -> "The shop is open only between levels";
        };
    }
}
