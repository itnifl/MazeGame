package main.game.maze.common.graphics.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Default {@link MazeConfigLoader}: reads a .properties file from one of
 * three sources, in order of priority:
 * <ol>
 *   <li>An explicit filesystem path passed to {@link #fromFile(Path)}.</li>
 *   <li>The classpath resource named by the constructor (default
 *       {@code maze-runtime.properties}).</li>
 *   <li>{@link MazeRuntimeConfig#DEFAULT} if neither source is present.</li>
 * </ol>
 *
 * <p>Unknown keys are ignored; missing keys fall back to the corresponding
 * default value. Numeric parse errors throw {@link IllegalStateException}
 * so a malformed config fails loudly at startup instead of silently
 * reverting to defaults.
 */
public final class PropertiesMazeConfigLoader implements MazeConfigLoader {

    public static final String DEFAULT_RESOURCE = "maze-runtime.properties";

    private final String resourceName;
    private final Path filePath;

    public PropertiesMazeConfigLoader() {
        this(DEFAULT_RESOURCE, null);
    }

    public PropertiesMazeConfigLoader(String resourceName) {
        this(resourceName, null);
    }

    private PropertiesMazeConfigLoader(String resourceName, Path filePath) {
        this.resourceName = resourceName;
        this.filePath = filePath;
    }

    public static PropertiesMazeConfigLoader fromFile(Path filePath) {
        if (filePath == null) throw new IllegalArgumentException("filePath must not be null");
        return new PropertiesMazeConfigLoader(null, filePath);
    }

    @Override
    public MazeRuntimeConfig load() {
        Properties props = readProperties();
        if (props == null) return MazeRuntimeConfig.DEFAULT;
        MazeRuntimeConfig d = MazeRuntimeConfig.DEFAULT;
        return new MazeRuntimeConfig(
            intProp(props, "windowWidth",  d.windowWidth()),
            intProp(props, "windowHeight", d.windowHeight()),
            intProp(props, "mazeCols",     d.mazeCols()),
            intProp(props, "mazeRows",     d.mazeRows()),
            floatProp(props, "cellSize",    d.cellSize()),
            floatProp(props, "playerSpeed", d.playerSpeed()),
            boolProp(props, "useRealMaze", d.useRealMaze()));
    }

    private Properties readProperties() {
        if (filePath != null) {
            if (!Files.isRegularFile(filePath)) return null;
            try (InputStream in = Files.newInputStream(filePath)) {
                Properties p = new Properties();
                p.load(in);
                return p;
            } catch (IOException ex) {
                throw new IllegalStateException("Failed to read maze config from " + filePath, ex);
            }
        }
        if (resourceName == null) return null;
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) cl = PropertiesMazeConfigLoader.class.getClassLoader();
        try (InputStream in = cl.getResourceAsStream(resourceName)) {
            if (in == null) return null;
            Properties p = new Properties();
            p.load(in);
            return p;
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to read maze config resource " + resourceName, ex);
        }
    }

    private static int intProp(Properties p, String key, int fallback) {
        String v = p.getProperty(key);
        if (v == null || v.isBlank()) return fallback;
        try { return Integer.parseInt(v.trim()); }
        catch (NumberFormatException ex) {
            throw new IllegalStateException("Invalid integer for " + key + ": " + v, ex);
        }
    }

    private static float floatProp(Properties p, String key, float fallback) {
        String v = p.getProperty(key);
        if (v == null || v.isBlank()) return fallback;
        try { return Float.parseFloat(v.trim()); }
        catch (NumberFormatException ex) {
            throw new IllegalStateException("Invalid float for " + key + ": " + v, ex);
        }
    }

    private static boolean boolProp(Properties p, String key, boolean fallback) {
        String v = p.getProperty(key);
        if (v == null || v.isBlank()) return fallback;
        return Boolean.parseBoolean(v.trim());
    }
}
