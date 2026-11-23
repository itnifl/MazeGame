package main.game.maze.game.shop;

import main.game.maze.game.items.Weapon;

/** What a shop item is; mirrors the {@code ShopItemKind} enum of {@code xmi/shop/shop.ecore}. */
public enum ShopItemKind {
    /** Boost: shields last longer during the next level. */
    SHIELD_BOOST,
    /** Boost: bomb flames reach further and do more damage during the next level. */
    FLAME_BOOST,
    GRENADE,
    PLASMA_GRENADE,
    NUKE_GRENADE,
    BUNKER_BUSTER,
    FAT_BOMB,
    AIRSTRIKE,
    INFANTRY_STRIKE,
    /** "Extra life": a weapon slot that fills the health bar when used. */
    EXTRA_LIFE;

    /** The weapon a purchase adds to the arsenal, or null for a boost. */
    public Weapon weapon() {
        return switch (this) {
            case SHIELD_BOOST, FLAME_BOOST -> null;
            case EXTRA_LIFE -> Weapon.LIFE_REFILL;
            default -> Weapon.valueOf(name());
        };
    }

    public boolean isBoost() {
        return weapon() == null;
    }
}
