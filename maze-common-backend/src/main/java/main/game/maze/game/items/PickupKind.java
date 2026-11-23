package main.game.maze.game.items;

/** Kinds of pickup; mirrors the {@code PickupKind} enum of {@code xmi/items/items.ecore}. */
public enum PickupKind {
    BOMB,
    LASER_RIFLE,
    SLEDGEHAMMER,
    SHIELD,
    HEART,
    /** One more life (respawn at the start when the player dies). */
    EXTRA_LIFE,
    /** Never spawns on its own: the gun a killed zombie sergeant drops. */
    GATLING_GUN,
    /** Never spawns: the pulse beam cannon, the gift of the last life. */
    PULSE_CANNON,
    /** Never spawns: the shop weapons below are bought between levels ({@code xmi/shop/shop.xmi}). */
    GRENADE,
    PLASMA_GRENADE,
    NUKE_GRENADE,
    BUNKER_BUSTER,
    FAT_BOMB,
    AIRSTRIKE,
    INFANTRY_STRIKE,
    LIFE_REFILL;

    /** Whether it is only sold in the shop between levels (never lying in the maze, never dropped). */
    public boolean shopOnly() {
        return ordinal() >= GRENADE.ordinal();
    }

    /** The weapon this pickup refills (the weapon of the same name), or null for shield / heart / extra life. */
    public Weapon weapon() {
        return switch (this) {
            case SHIELD, HEART, EXTRA_LIFE -> null;
            default -> Weapon.valueOf(name());
        };
    }
}
