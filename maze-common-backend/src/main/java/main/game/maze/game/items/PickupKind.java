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
    PULSE_CANNON;

    /** The weapon this pickup refills, or null for shield / heart / extra life. */
    public Weapon weapon() {
        return switch (this) {
            case BOMB -> Weapon.BOMB;
            case LASER_RIFLE -> Weapon.LASER_RIFLE;
            case SLEDGEHAMMER -> Weapon.SLEDGEHAMMER;
            case GATLING_GUN -> Weapon.GATLING_GUN;
            case PULSE_CANNON -> Weapon.PULSE_CANNON;
            default -> null;
        };
    }
}
