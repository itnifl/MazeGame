package main.game.maze.game.items;

/** What the player holds and uses with the attack key; Tab cycles through the ones in stock. */
public enum Weapon {
    BOMB,
    LASER_RIFLE,
    SLEDGEHAMMER;

    /** The pickup that refills this weapon. */
    public PickupKind pickup() {
        return switch (this) {
            case BOMB -> PickupKind.BOMB;
            case LASER_RIFLE -> PickupKind.LASER_RIFLE;
            case SLEDGEHAMMER -> PickupKind.SLEDGEHAMMER;
        };
    }
}
