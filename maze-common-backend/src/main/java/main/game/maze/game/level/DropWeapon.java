package main.game.maze.game.level;

import main.game.maze.game.items.PickupKind;

/** What a killed sergeant drops; mirrors the {@code DropWeapon} enum of {@code levels.ecore}. */
public enum DropWeapon {
    GATLING_GUN(PickupKind.GATLING_GUN);

    private final PickupKind pickup;

    DropWeapon(PickupKind pickup) {
        this.pickup = pickup;
    }

    /** The pickup lying where the sergeant fell. */
    public PickupKind pickup() {
        return pickup;
    }
}
