package main.game.maze.game.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import main.game.maze.game.items.PlayerArsenal;
import main.game.maze.game.items.Weapon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("Load button of the main menu: the last level entered, as it was entered")
class LoadGameMenuTest {

    @Test
    @DisplayName("nothing to load before a level has been completed; then the level, weapons, score and difficulty are loaded")
    void loadsTheStateTheLevelWasEnteredAt(@TempDir Path dir) {
        SavePointStore store = new FileSavePointStore(dir.resolve("data").resolve("savepoint.properties"));
        assertTrue(LoadGameMenu.latest(store).isEmpty());
        assertEquals("Load (no saved level)", LoadGameMenu.label(LoadGameMenu.latest(store)));

        LevelProgress first = new LevelProgress(LevelCatalogLoader.loadDefault(), store);
        PlayerArsenal arsenal = new PlayerArsenal(3);
        arsenal.add(Weapon.LASER_RIFLE, 4);
        first.completeLevel(arsenal, 28_000, "Hard");

        Optional<SavePoint> saved = LoadGameMenu.latest(store);
        assertEquals("Load Level 2", LoadGameMenu.label(saved));
        assertEquals("Hard", saved.orElseThrow().difficulty());

        // a new session of the game: a fresh progress that loads what the file keeps
        LevelProgress second = new LevelProgress(LevelCatalogLoader.loadDefault(), store);
        assertTrue(second.resumeFromSavePoint());
        assertEquals(2, second.number());
        assertEquals(28_000, second.carriedScore());
        assertEquals(4, second.inheritedArsenal().count(Weapon.LASER_RIFLE), "the weapons it was entered with");
    }

    @Test
    @DisplayName("the difficulty it was saved on is chosen, else the one chosen in the menu")
    void difficultyChoice() {
        List<String> options = List.of("Easy", "Medium", "Hard");
        SavePoint onHard = new SavePoint(2, null, 100, "hard");
        assertEquals("Hard", LoadGameMenu.difficultyFor(onHard, options, s -> s, "Easy"));
        assertEquals("Easy", LoadGameMenu.difficultyFor(new SavePoint(2, null), options, s -> s, "Easy"), "unknown: the menu's choice");
        assertEquals("Medium", LoadGameMenu.difficultyFor(new SavePoint(2, null, 0, "Nightmare"), options, s -> s, "Medium"));
        assertFalse(LoadGameMenu.latest(new InMemorySavePointStore()).isPresent());
    }
}
