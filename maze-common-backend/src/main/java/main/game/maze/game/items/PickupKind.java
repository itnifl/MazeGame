package main.game.maze.game.items;

/** Kinds of pickup; mirrors the {@code PickupKind} enum of {@code xmi/items/items.ecore}. */
public enum PickupKind {
    BOMB,
    LASER_RIFLE,
    SLEDGEHAMMER,
    SHIELD,
    HEART;

    /** The weapon this pickup refills, or null for shield / heart. */
    public Weapon weapon() {
        return switch (this) {
            case BOMB -> Weapon.BOMB;
            case LASER_RIFLE -> Weapon.LASER_RIFLE;
            case SLEDGEHAMMER -> Weapon.SLEDGEHAMMER;
            default -> null;
        };
    }
}
