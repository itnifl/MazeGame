package main.game.maze.game.items;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Arsenal snapshot: what the player carries into the next level")
class ArsenalSnapshotTest {

    @Test
    @DisplayName("a restored arsenal has the same stock and weapon in hand, including the gatling gun")
    void roundTrip() {
        PlayerArsenal original = new PlayerArsenal(2);
        original.add(Weapon.GATLING_GUN, 4);
        original.add(Weapon.SLEDGEHAMMER, 1);
        original.cycle();

        PlayerArsenal copy = PlayerArsenal.restore(original.snapshot());

        for (Weapon weapon : Weapon.values()) {
            assertEquals(original.count(weapon), copy.count(weapon), weapon.name());
        }
        assertEquals(original.selected(), copy.selected());
    }

    @Test
    @DisplayName("the copy is independent of the original")
    void independent() {
        PlayerArsenal original = new PlayerArsenal(2);
        PlayerArsenal copy = PlayerArsenal.restore(original.snapshot());
        copy.use();
        assertEquals(2, original.count(Weapon.BOMB));
        assertEquals(1, copy.count(Weapon.BOMB));
    }

    @Test
    @DisplayName("the weapon in hand is kept even when it is the picked-up one")
    void pickedUpWeaponInHand() {
        PlayerArsenal original = new PlayerArsenal(0);
        original.add(Weapon.LASER_RIFLE, 2);   // laser in hand since the bombs are empty
        PlayerArsenal copy = PlayerArsenal.restore(original.snapshot());
        assertEquals(Weapon.LASER_RIFLE, copy.selected());
    }
}
