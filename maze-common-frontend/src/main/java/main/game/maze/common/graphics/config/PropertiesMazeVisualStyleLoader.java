package main.game.maze.common.graphics.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Loads shared visual style defaults from a properties resource.
 */
public final class PropertiesMazeVisualStyleLoader {

    public static final String DEFAULT_RESOURCE = "maze-visual-style.properties";

    private final String resourceName;
    private final Path filePath;

    public PropertiesMazeVisualStyleLoader() {
        this(DEFAULT_RESOURCE, null);
    }

    public PropertiesMazeVisualStyleLoader(String resourceName) {
        this(resourceName, null);
    }

    private PropertiesMazeVisualStyleLoader(String resourceName, Path filePath) {
        this.resourceName = resourceName;
        this.filePath = filePath;
    }

    public static PropertiesMazeVisualStyleLoader fromFile(Path filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException("filePath must not be null");
        }
        return new PropertiesMazeVisualStyleLoader(null, filePath);
    }

    public MazeVisualStyleConfig load() {
        Properties props = readProperties();
        if (props == null) {
            return MazeVisualStyleConfig.DEFAULT;
        }

        MazeVisualStyleConfig d = MazeVisualStyleConfig.DEFAULT;
        return new MazeVisualStyleConfig(
                stringProp(props, "easyBackgroundImagePath", d.easyBackgroundImagePath()),
                stringProp(props, "normalBackgroundImagePath", d.normalBackgroundImagePath()),
                stringProp(props, "hardBackgroundImagePath", d.hardBackgroundImagePath()),
                stringProp(props, "menuIconImagePath", d.menuIconImagePath()),
                stringProp(props, "goalImagePath", d.goalImagePath()),
                stringProp(props, "easyWallTypeId", d.easyWallTypeId()),
                stringProp(props, "normalWallTypeId", d.normalWallTypeId()),
                stringProp(props, "hardWallTypeId", d.hardWallTypeId()),
                stringProp(props, "menuMusicPath", d.menuMusicPath()),
                stringProp(props, "menuSelectSoundPath", d.menuSelectSoundPath()),
                stringProp(props, "inGameMusicPath", d.inGameMusicPath()),
                stringProp(props, "winSoundPath", d.winSoundPath()));
    }

    private Properties readProperties() {
        if (filePath != null) {
            if (!Files.isRegularFile(filePath)) {
                return null;
            }
            try (InputStream in = Files.newInputStream(filePath)) {
                Properties p = new Properties();
                p.load(in);
                return p;
            } catch (IOException ex) {
                throw new IllegalStateException("Failed to read visual style config from " + filePath, ex);
            }
        }

        if (resourceName == null) {
            return null;
        }

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            cl = PropertiesMazeVisualStyleLoader.class.getClassLoader();
        }
        try (InputStream in = cl.getResourceAsStream(resourceName)) {
            if (in == null) {
                return null;
            }
            Properties p = new Properties();
            p.load(in);
            return p;
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to read visual style resource " + resourceName, ex);
        }
    }

    private static String stringProp(Properties p, String key, String fallback) {
        String v = p.getProperty(key);
        if (v == null || v.isBlank()) {
            return fallback;
        }
        return v.trim();
    }
}
