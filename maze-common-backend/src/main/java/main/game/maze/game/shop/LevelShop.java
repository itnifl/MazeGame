package main.game.maze.game.shop;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import main.game.maze.game.items.ArsenalSnapshot;
import main.game.maze.game.items.PlayerArsenal;
import main.game.maze.game.items.Weapon;
import main.game.maze.game.level.LevelBoosts;
import main.game.maze.game.level.LevelProgress;
import main.game.maze.game.level.SavePoint;

/**
 * The shop between levels, the same in both frontends. It is open from the moment a level is completed until the next one
 * is entered, and sells against the savepoint made for the next level ({@link LevelProgress#pendingSavePoint()}): the
 * price is taken from the score carried into the next level, a weapon is added to the arsenal carried into it, and a boost
 * is kept for it. So restarting the next level (it is entered from its savepoint) still has what was bought.
 */
public final class LevelShop {

    /** What happened when the player tried to buy. */
    public enum Result {
        BOUGHT,
        /** The score carried forward is lower than the price. */
        NOT_ENOUGH_SCORE,
        /** A boost that is already on for the next level (buying it again would do nothing). */
        ALREADY_ACTIVE,
        /** Not between levels (no next level to buy for). */
        CLOSED
    }

    private final ShopCatalog catalog;
    private final LevelProgress progress;

    public LevelShop(ShopCatalog catalog, LevelProgress progress) {
        this.catalog = catalog;
        this.progress = progress;
    }

    public ShopCatalog catalog() {
        return catalog;
    }

    /** Whether the shop is open: a level has been completed and the next one not entered yet. */
    public boolean isOpen() {
        return progress.pendingSavePoint().isPresent();
    }

    /** The score the player can spend: the score carried into the next level (0 when the shop is closed). */
    public int credits() {
        return progress.pendingSavePoint().map(SavePoint::score).orElse(0);
    }

    /** How many uses of the item's weapon the player carries into the next level (0 for boosts). */
    public int stock(ShopItem item) {
        Weapon weapon = item.kind().weapon();
        Optional<SavePoint> pending = progress.pendingSavePoint();
        if (weapon == null || pending.isEmpty() || pending.get().arsenal() == null) {
            return 0;
        }
        return Math.max(0, pending.get().arsenal().counts().getOrDefault(weapon, 0));
    }

    /** Whether the item is a boost that is already on for the next level. */
    public boolean isActive(ShopItem item) {
        LevelBoosts boosts = progress.pendingSavePoint().map(SavePoint::boosts).orElse(LevelBoosts.NONE);
        return switch (item.kind()) {
            case SHIELD_BOOST -> boosts.shieldDurationFactor() >= item.factor();
            case FLAME_BOOST -> boosts.bombPowerFactor() >= item.factor();
            default -> false;
        };
    }

    public boolean canAfford(ShopItem item) {
        return credits() >= item.price();
    }

    /** Buys one of the item for the next level, paying with the score carried into it. */
    public Result buy(ShopItem item) {
        Optional<SavePoint> open = progress.pendingSavePoint();
        if (open.isEmpty()) {
            return Result.CLOSED;
        }
        if (isActive(item)) {
            return Result.ALREADY_ACTIVE;
        }
        SavePoint pending = open.get();
        if (pending.score() < item.price()) {
            return Result.NOT_ENOUGH_SCORE;
        }
        ArsenalSnapshot arsenal = pending.arsenal();
        LevelBoosts boosts = pending.boosts();
        switch (item.kind()) {
            case SHIELD_BOOST -> boosts = boosts.withShield(item.factor());
            case FLAME_BOOST -> boosts = boosts.withBombPower(item.factor());
            default -> arsenal = withMore(arsenal, item.kind().weapon(), item.amount());
        }
        progress.updatePendingSavePoint(pending.afterPurchase(arsenal, pending.score() - item.price(), boosts));
        return Result.BOUGHT;
    }

    /**
     * The arsenal with {@code amount} more of the weapon. A next level that does not keep the weapons (none does today)
     * still gets what was bought: it starts from an empty arsenal with the purchase in it.
     */
    private static ArsenalSnapshot withMore(ArsenalSnapshot arsenal, Weapon weapon, int amount) {
        PlayerArsenal stock = arsenal != null ? PlayerArsenal.restore(arsenal) : PlayerArsenal.restore(empty());
        Weapon inHand = stock.selected();
        stock.add(weapon, amount);
        if (stock.count(inHand) > 0) {
            stock.select(inHand);   // buying does not change the weapon in hand, unless the hands were empty
        }
        return stock.snapshot();
    }

    private static ArsenalSnapshot empty() {
        Map<Weapon, Integer> counts = new EnumMap<>(Weapon.class);
        for (Weapon weapon : Weapon.values()) {
            counts.put(weapon, 0);
        }
        return new ArsenalSnapshot(counts, Weapon.BOMB);
    }
}
