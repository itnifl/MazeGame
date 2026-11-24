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

    @Test
    @DisplayName("completing a level saves the savepoint for the next one; it is kept until a new game")
    void completingASavesThePoint() {
        InMemorySavePointStore store = new InMemorySavePointStore();
        LevelProgress saved = new LevelProgress(LevelCatalogLoader.loadDefault(), store);
        PlayerArsenal arsenal = new PlayerArsenal(3);
        arsenal.add(Weapon.LASER_RIFLE, 4);
        saved.completeLevel(arsenal);
        assertEquals(2, store.load().orElseThrow().level());
        assertEquals(1, saved.number(), "still on level 1 until the player goes on");
        saved.advance(new PlayerArsenal(99));   // what is passed now does not matter: the savepoint was made at the completion
        assertEquals(2, saved.number());
        assertEquals(4, saved.inheritedArsenal().count(Weapon.LASER_RIFLE));
        assertEquals(3, saved.inheritedArsenal().count(Weapon.BOMB));
        saved.restart();
        assertTrue(store.load().isEmpty(), "a new game removes the savepoint");
    }

    @Test
    @DisplayName("restarting a later level restores it exactly as entered; restarting level 1 is a new game")
    void restartLevelUsesTheSavePoint() {
        InMemorySavePointStore store = new InMemorySavePointStore();
        LevelProgress saved = new LevelProgress(LevelCatalogLoader.loadDefault(), store);
        PlayerArsenal entry = new PlayerArsenal(3);
        entry.add(Weapon.SLEDGEHAMMER, 2);
        saved.completeLevel(entry);
        saved.advance(entry);
        saved.restartLevel();
        assertEquals(2, saved.number());
        assertEquals(2, saved.inheritedArsenal().count(Weapon.SLEDGEHAMMER));
        assertTrue(store.load().isPresent());
        // level 2 played, completed, level 3 entered, restarted: level 3 with level 2's end state, not level 2's
        PlayerArsenal end = new PlayerArsenal(1);
        end.add(Weapon.LASER_RIFLE, 7);
        saved.completeLevel(end);
        saved.advance(end);
        saved.restartLevel();
        assertEquals(3, saved.number());
        assertEquals(7, saved.inheritedArsenal().count(Weapon.LASER_RIFLE));

        LevelProgress first = new LevelProgress(LevelCatalogLoader.loadDefault(), store);
        first.restartLevel();
        assertEquals(1, first.number());
        assertNull(first.inheritedArsenal());
        assertTrue(store.load().isEmpty(), "level 1 restarts as a new game: no savepoint");
    }

    @Test
    @DisplayName("the game can be picked up again from the kept savepoint")
    void resumeFromSavePoint() {
        InMemorySavePointStore store = new InMemorySavePointStore();
        LevelProgress before = new LevelProgress(LevelCatalogLoader.loadDefault(), store);
        PlayerArsenal arsenal = new PlayerArsenal(5);
        before.completeLevel(arsenal);

        LevelProgress after = new LevelProgress(LevelCatalogLoader.loadDefault(), store);
        assertTrue(after.resumeFromSavePoint());
        assertEquals(2, after.number());
        assertEquals(5, after.inheritedArsenal().count(Weapon.BOMB));
        assertFalse(new LevelProgress(LevelCatalogLoader.loadDefault(), new InMemorySavePointStore()).resumeFromSavePoint());
    }

    @Test
    @DisplayName("the last level makes no savepoint")
    void lastLevelHasNoSavepoint() {
        InMemorySavePointStore store = new InMemorySavePointStore();
        LevelProgress p = new LevelProgress(LevelCatalogLoader.loadDefault(), store);
        while (p.hasNext()) {
            p.advance(new PlayerArsenal(1));
        }
        store.clear();
        p.completeLevel(new PlayerArsenal(1));
        assertTrue(store.load().isEmpty());
    }

    @Test
    @DisplayName("the score is carried forward, not reset: 28 000 at the end of a level on hard starts the next level on 58 000")
    void scoreIsCarriedForward() {
        InMemorySavePointStore store = new InMemorySavePointStore();
        LevelProgress p = new LevelProgress(LevelCatalogLoader.loadDefault(), store);
        int hard = main.game.maze.common.scoring.GameScoringConstants.HARD_BASE_SCORE;
        assertEquals(0, p.carriedScore(), "nothing is carried into level 1");
        assertEquals(30_000, hard + p.carriedScore(), "level 1 starts at 30 000");

        p.completeLevel(new PlayerArsenal(3), 28_000);   // the level ended on 28 000
        assertEquals(0, p.carriedScore(), "still on level 1 until the player goes on");
        p.advance(new PlayerArsenal(3));
        assertEquals(28_000, p.carriedScore());
        assertEquals(58_000, hard + p.carriedScore(), "level 2 starts at 58 000");

        p.completeLevel(new PlayerArsenal(3), 61_500);   // level 2 ended on 61 500 (58 000 plus what was earned)
        p.advance(new PlayerArsenal(3));
        assertEquals(61_500, p.carriedScore());
        assertEquals(91_500, hard + p.carriedScore(), "and so on");
    }

    @Test
    @DisplayName("a restarted level goes back to the score it was entered with; a new game starts from nothing")
    void restartingKeepsTheEntryScore() {
        InMemorySavePointStore store = new InMemorySavePointStore();
        LevelProgress p = new LevelProgress(LevelCatalogLoader.loadDefault(), store);
        p.completeLevel(new PlayerArsenal(3), 28_000);
        p.advance(new PlayerArsenal(3));
        p.restartLevel();
        assertEquals(28_000, p.carriedScore(), "level 2 restarts from its savepoint, score included");
        assertEquals(28_000, store.load().orElseThrow().score());
        p.restartLevel();
        p.completeLevel(new PlayerArsenal(3));   // a completion with no score given adds nothing new
        p.advance(new PlayerArsenal(3));
        assertEquals(28_000, p.carriedScore());

        p.restart();
        assertEquals(0, p.carriedScore(), "a new game: no score carried");
        p.restartLevel();
        assertEquals(0, p.carriedScore(), "restarting level 1 is a new game");
    }

    @Test
    @DisplayName("the carried score is part of the savepoint: picked up again, it is still there")
    void scoreIsInTheSavePoint() {
        InMemorySavePointStore store = new InMemorySavePointStore();
        LevelProgress before = new LevelProgress(LevelCatalogLoader.loadDefault(), store);
        before.completeLevel(new PlayerArsenal(3), 28_000);
        LevelProgress after = new LevelProgress(LevelCatalogLoader.loadDefault(), store);
        assertTrue(after.resumeFromSavePoint());
        assertEquals(28_000, after.carriedScore());
        before.completeLevel(new PlayerArsenal(3), -5);
        assertEquals(0, store.load().orElseThrow().score(), "a negative score carries as nothing");
    }
}
