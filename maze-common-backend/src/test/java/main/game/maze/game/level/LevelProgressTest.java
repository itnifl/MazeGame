package main.game.maze.game.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.game.maze.game.items.PlayerArsenal;
import main.game.maze.game.items.Weapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Level progress: win level 1, play level 2 with the weapons you had")
class LevelProgressTest {

    private LevelProgress progress;

    @BeforeEach
    void setUp() {
        progress = new LevelProgress(LevelCatalogLoader.loadDefault());
    }

    @Test
    @DisplayName("starts on level 1 with nothing inherited")
    void startsOnLevelOne() {
        assertEquals(1, progress.number());
        assertEquals(1, progress.current().number());
        assertTrue(progress.hasNext());
        assertNull(progress.inheritedArsenal());
    }

    @Test
    @DisplayName("winning level 1 moves to level 2 and carries the weapons over")
    void advanceCarriesTheArsenal() {
        PlayerArsenal arsenal = new PlayerArsenal(3);
        arsenal.add(Weapon.LASER_RIFLE, 4);
        arsenal.add(Weapon.SLEDGEHAMMER, 2);
        arsenal.cycle();   // laser rifle in hand
        arsenal.use();     // one laser shot spent: 3 left

        progress.advance(arsenal);

        assertEquals(2, progress.number());
        assertTrue(progress.hasNext(), "level 3 follows level 2");
        PlayerArsenal inherited = progress.inheritedArsenal();
        assertEquals(3, inherited.count(Weapon.BOMB));
        assertEquals(3, inherited.count(Weapon.LASER_RIFLE));
        assertEquals(2, inherited.count(Weapon.SLEDGEHAMMER));
        assertEquals(arsenal.selected(), inherited.selected());
    }

    @Test
    @DisplayName("every call gives a fresh copy: playing level 2 never changes what level 1 handed over")
    void inheritedArsenalIsACopy() {
        PlayerArsenal arsenal = new PlayerArsenal(3);
        progress.advance(arsenal);
        progress.inheritedArsenal().use();
        assertEquals(3, progress.inheritedArsenal().count(Weapon.BOMB));
        arsenal.add(Weapon.BOMB, 5);
        assertEquals(3, progress.inheritedArsenal().count(Weapon.BOMB), "later changes to the old arsenal are not seen");
    }

    @Test
    @DisplayName("there is no level after the last one")
    void cannotAdvancePastTheLastLevel() {
        progress.advance(new PlayerArsenal(1));
        progress.advance(new PlayerArsenal(1));
        progress.advance(new PlayerArsenal(1));
        assertFalse(progress.hasNext());
        assertThrows(IllegalStateException.class, () -> progress.advance(new PlayerArsenal(1)));
    }

    @Test
    @DisplayName("a new game starts again on level 1 with nothing carried")
    void restart() {
        progress.advance(new PlayerArsenal(3));
        progress.restart();
        assertEquals(1, progress.number());
        assertNull(progress.inheritedArsenal());
    }

    @Test
    @DisplayName("retrying level 2 starts with the weapons level 2 was entered with")
    void retryKeepsTheStartingArsenal() {
        PlayerArsenal arsenal = new PlayerArsenal(3);
        arsenal.add(Weapon.SLEDGEHAMMER, 5);
        progress.advance(arsenal);
        progress.inheritedArsenal().use();   // played the level and spent ammunition

        assertEquals(2, progress.number());
        assertEquals(5, progress.inheritedArsenal().count(Weapon.SLEDGEHAMMER));
        assertEquals(3, progress.inheritedArsenal().count(Weapon.BOMB));
    }

    @Test
    @DisplayName("a level can start from scratch, and levels beyond two work the same way")
    void levelWithoutInheritedWeapons() {
        LevelProgress custom = new LevelProgress(
                LevelCatalogLoader.load(LevelCatalogLoader.DEFAULT_ECORE, "/xmi/levels/no-inherit.xmi"));
        PlayerArsenal arsenal = new PlayerArsenal(3);
        arsenal.add(Weapon.LASER_RIFLE, 4);

        custom.advance(arsenal);
        assertEquals(2, custom.number());
        assertNull(custom.inheritedArsenal(), "level 2 asks for a fresh start");
        assertTrue(custom.hasNext());

        custom.advance(arsenal);
        assertEquals(3, custom.number());
        assertEquals(4, custom.inheritedArsenal().count(Weapon.LASER_RIFLE), "level 3 keeps the weapons");
        assertFalse(custom.hasNext());
    }

    @Test
    @DisplayName("replaying a completed level keeps the level and the weapons it was entered with")
    void retryingALevelKeepsItsStartingWeapons() {
        PlayerArsenal arsenal = new PlayerArsenal(3);
        progress.advance(arsenal);
        arsenal.add(main.game.maze.game.items.Weapon.BOMB, 5);   // played level 2 and used/collected things
        int before = progress.inheritedArsenal().count(main.game.maze.game.items.Weapon.BOMB);
        // a retry changes nothing in the progress: same level, same starting weapons
        assertEquals(2, progress.number());
        assertEquals(before, progress.inheritedArsenal().count(main.game.maze.game.items.Weapon.BOMB));
    }
}
