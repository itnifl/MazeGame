package main.game.maze.game.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import main.game.maze.game.items.PlayerArsenal;
import main.game.maze.game.items.Weapon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("Savepoint file: kept between games, gone on a new game")
class FileSavePointStoreTest {

    @TempDir
    Path dir;

    @Test
    @DisplayName("a savepoint survives a restart of the program (a new store on the same file reads it back)")
    void savedAndReadBack() {
        PlayerArsenal arsenal = new PlayerArsenal(3);
        arsenal.add(Weapon.LASER_RIFLE, 4);
        arsenal.add(Weapon.GATLING_GUN, 2);
        arsenal.cycle();
        new FileSavePointStore(dir.resolve("data/savepoint.properties")).save(new SavePoint(3, arsenal.snapshot()));

        SavePoint read = new FileSavePointStore(dir.resolve("data/savepoint.properties")).load().orElseThrow();
        assertEquals(3, read.level());
        assertEquals(3, read.arsenal().counts().get(Weapon.BOMB));
        assertEquals(4, read.arsenal().counts().get(Weapon.LASER_RIFLE));
        assertEquals(2, read.arsenal().counts().get(Weapon.GATLING_GUN));
        assertEquals(arsenal.selected(), read.arsenal().selected());
    }

    @Test
    @DisplayName("a level that starts from scratch is saved without weapons")
    void noWeapons() {
        FileSavePointStore store = new FileSavePointStore(dir.resolve("s.properties"));
        store.save(new SavePoint(2, null));
        assertEquals(2, store.load().orElseThrow().level());
        assertEquals(null, store.load().orElseThrow().arsenal());
    }

    @Test
    @DisplayName("no file means no savepoint; clearing removes it; a damaged file is ignored")
    void emptyClearedAndDamaged() throws Exception {
        Path file = dir.resolve("s.properties");
        FileSavePointStore store = new FileSavePointStore(file);
        assertFalse(store.load().isPresent());
        store.save(new SavePoint(2, null));
        assertTrue(Files.exists(file));
        store.clear();
        assertFalse(Files.exists(file));
        Files.writeString(file, "level=banana\n");
        assertFalse(store.load().isPresent());
    }

    @Test
    @DisplayName("a savepoint is for level 2 or later")
    void levelOneHasNoSavepoint() {
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> new SavePoint(1, null));
    }

    @Test
    @DisplayName("a savepoint that cannot be written leaves the previous one as it was")
    void aFailedWriteKeepsTheOldSavePoint() throws Exception {
        Path file = dir.resolve("s.properties");
        FileSavePointStore store = new FileSavePointStore(file);
        store.save(new SavePoint(2, null));
        // the temporary file cannot be created: something else (a non-empty directory) is in its place
        Path blocker = dir.resolve("s.properties.tmp");
        Files.createDirectories(blocker);
        Files.writeString(blocker.resolve("x"), "x");
        store.save(new SavePoint(4, null));
        assertEquals(2, store.load().orElseThrow().level(), "the old savepoint is still there");
    }

    @Test
    @DisplayName("a new savepoint replaces the old one and leaves no temporary file")
    void replacesCleanly() throws Exception {
        Path file = dir.resolve("s.properties");
        FileSavePointStore store = new FileSavePointStore(file);
        store.save(new SavePoint(2, null));
        store.save(new SavePoint(3, null));
        assertEquals(3, store.load().orElseThrow().level());
        assertFalse(Files.exists(dir.resolve("s.properties.tmp")));
    }

    @Test
    @DisplayName("the carried score is saved in the file, and a file from before it was saved reads as 0")
    void scoreInTheFile() throws Exception {
        Path file = dir.resolve("s.properties");
        FileSavePointStore store = new FileSavePointStore(file);
        store.save(new SavePoint(3, null, 58_000));
        assertEquals(58_000, new FileSavePointStore(file).load().orElseThrow().score());
        Files.writeString(file, "level=2" + System.lineSeparator());
        assertEquals(0, store.load().orElseThrow().score());
    }
}
