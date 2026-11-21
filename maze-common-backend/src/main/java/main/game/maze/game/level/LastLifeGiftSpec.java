package main.game.maze.game.level;

import main.game.maze.game.items.Weapon;

/**
 * The gift of the last life, as defined in {@code levels.xmi}: a weapon and how many shots of it the player is given when
 * they start their last life. The weapon becomes the one in hand and stays so until the player chooses another.
 */
public record LastLifeGiftSpec(Weapon weapon, int shots) {

    public LastLifeGiftSpec {
        if (weapon == null || shots < 1) {
            throw new IllegalArgumentException("a last-life gift needs a weapon and at least one shot");
        }
    }

    /** Reads the weapon by its name (the model holds it as text). */
    public static LastLifeGiftSpec of(String weaponName, int shots) {
        try {
            return new LastLifeGiftSpec(Weapon.valueOf(weaponName), shots);
        } catch (IllegalArgumentException | NullPointerException unknown) {
            throw new IllegalArgumentException("unknown weapon for the last life gift: " + weaponName, unknown);
        }
    }

    /** Puts the gift in the arsenal and in the hands. */
    public void give(main.game.maze.game.items.PlayerArsenal arsenal) {
        arsenal.add(weapon, shots);
        arsenal.select(weapon);
    }
}
