package main.game.maze.game.level;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.game.maze.game.items.ArsenalSnapshot;
import main.game.maze.game.items.Weapon;

/**
 * Keeps the savepoint in a small properties file (a relative path, like the high scores). A file that cannot be
 * written or read is logged and treated as "no savepoint": the game itself never fails because of it.
 */
public final class FileSavePointStore implements SavePointStore {

    private static final Logger LOGGER = Logger.getLogger(FileSavePointStore.class.getName());
    private static final String LEVEL = "level";
    private static final String SELECTED = "selected";
    private static final String COUNT_PREFIX = "count.";

    private final Path file;

    public FileSavePointStore(Path file) {
        this.file = file;
    }

    @Override
    public synchronized void save(SavePoint savePoint) {
        Properties properties = new Properties();
        properties.setProperty(LEVEL, Integer.toString(savePoint.level()));
        ArsenalSnapshot arsenal = savePoint.arsenal();
        if (arsenal != null) {
            properties.setProperty(SELECTED, arsenal.selected().name());
            for (Map.Entry<Weapon, Integer> entry : arsenal.counts().entrySet()) {
                properties.setProperty(COUNT_PREFIX + entry.getKey().name(), Integer.toString(entry.getValue()));
            }
        }
        try {
            if (file.getParent() != null) {
                Files.createDirectories(file.getParent());
            }
            try (OutputStream out = Files.newOutputStream(file)) {
                properties.store(out, "MazeGame savepoint");
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Could not save the savepoint to " + file, e);
        }
    }

    @Override
    public synchronized Optional<SavePoint> load() {
        if (!Files.isRegularFile(file)) {
            return Optional.empty();
        }
        Properties properties = new Properties();
        try (InputStream in = Files.newInputStream(file)) {
            properties.load(in);
            int level = Integer.parseInt(properties.getProperty(LEVEL, "").trim());
            String selected = properties.getProperty(SELECTED);
            if (selected == null) {
                return Optional.of(new SavePoint(level, null));
            }
            Map<Weapon, Integer> counts = new EnumMap<>(Weapon.class);
            for (Weapon weapon : Weapon.values()) {
                counts.put(weapon, Math.max(0, Integer.parseInt(properties.getProperty(COUNT_PREFIX + weapon.name(), "0").trim())));
            }
            return Optional.of(new SavePoint(level, new ArsenalSnapshot(counts, Weapon.valueOf(selected))));
        } catch (IOException | IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Ignoring an unreadable savepoint " + file, e);
            return Optional.empty();
        }
    }

    @Override
    public synchronized void clear() {
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Could not remove the savepoint " + file, e);
        }
    }
}
