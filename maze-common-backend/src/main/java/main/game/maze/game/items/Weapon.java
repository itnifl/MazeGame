package main.game.maze.game.items;

/** What the player holds and uses with the attack key; Tab cycles through the ones in stock. */
public enum Weapon {
    BOMB,
    LASER_RIFLE,
    SLEDGEHAMMER,
    /** Taken from a fallen zombie sergeant: a burst of rounds along a line, like the laser rifle. */
    GATLING_GUN;

    /** The pickup that refills this weapon. */
    public PickupKind pickup() {
        return switch (this) {
            case BOMB -> PickupKind.BOMB;
            case LASER_RIFLE -> PickupKind.LASER_RIFLE;
            case SLEDGEHAMMER -> PickupKind.SLEDGEHAMMER;
            case GATLING_GUN -> PickupKind.GATLING_GUN;
        };
    }
}
