package main.game.maze.game.items;

/** What the player holds and uses with the attack key; Tab cycles through the ones in stock. */
public enum Weapon {
    BOMB,
    LASER_RIFLE,
    SLEDGEHAMMER,
    /** Taken from a fallen zombie sergeant: a burst of rounds along a line, like the laser rifle. */
    GATLING_GUN,
    /** The gift of the last life: charges for two seconds, then one beam that blows up a tower. */
    PULSE_CANNON,
    /** Shop item: thrown along the facing, lands short of the next wall and goes off after a short fuse. */
    GRENADE,
    /** Shop item: a stronger thrown grenade with a wider blast. */
    PLASMA_GRENADE,
    /** Shop item: a small nuclear grenade, the strongest and widest thrown blast; it can hurt the thrower. */
    NUKE_GRENADE,
    /** Shop item: a rocket that pierces several layers of walls and blows up at the end of its flight. */
    BUNKER_BUSTER,
    /** Shop item: a planted bomb with much longer flames and much more damage than the bomb. */
    FAT_BOMB,
    /** Shop item: called in with the attack key; bombers carpet bomb the approximate centre of the maze. */
    AIRSTRIKE,
    /** Shop item: called in with the attack key; a squad of soldiers arrives and hunts every enemy. */
    INFANTRY_STRIKE,
    /** Shop item ("Extra life" in the shop): used with the attack key, it fills the player's health bar. */
    LIFE_REFILL;

    /** The pickup that refills this weapon (it has the same name). */
    public PickupKind pickup() {
        return PickupKind.valueOf(name());
    }

    /** Thrown weapons: they land along the facing (short of the next wall) and go off after their fuse. */
    public boolean isThrown() {
        return this == GRENADE || this == PLASMA_GRENADE || this == NUKE_GRENADE;
    }

    /**
     * Whether the weapon is drawn in the player's hands: guns, the hammer, the rocket and the grenades are; bombs are
     * planted, the strikes are called in by radio and the extra life is a medkit, so they are not.
     */
    public boolean heldInHands() {
        return switch (this) {
            case BOMB, FAT_BOMB, AIRSTRIKE, INFANTRY_STRIKE, LIFE_REFILL -> false;
            default -> true;
        };
    }

    /** Weapons whose shot breaks through breakable walls (the pulse beam, the bunker buster rocket). */
    public boolean piercesWalls() {
        return this == PULSE_CANNON || this == BUNKER_BUSTER;
    }
}
