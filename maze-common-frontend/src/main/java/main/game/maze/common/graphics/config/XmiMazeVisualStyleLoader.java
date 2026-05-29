package main.game.maze.common.graphics.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Loads MazeVisualStyleConfig from an XMI file or classpath resource.
 */
public final class XmiMazeVisualStyleLoader {

    public static final String DEFAULT_RESOURCE = "xmi/maze-visual-style.xmi";

    private final String resourceName;
    private final Path filePath;

    public XmiMazeVisualStyleLoader() {
        this(DEFAULT_RESOURCE, null);
    }

    public XmiMazeVisualStyleLoader(String resourceName) {
        this(resourceName, null);
    }

    private XmiMazeVisualStyleLoader(String resourceName, Path filePath) {
        this.resourceName = resourceName;
        this.filePath = filePath;
    }

    public static XmiMazeVisualStyleLoader fromFile(Path filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException("filePath must not be null");
        }
        return new XmiMazeVisualStyleLoader(null, filePath);
    }

    public MazeVisualStyleConfig load() {
        try (InputStream in = open()) {
            if (in == null) {
                return MazeVisualStyleConfig.DEFAULT;
            }
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            Document doc = dbf.newDocumentBuilder().parse(in);
            Element root = doc.getDocumentElement();
            if (root == null) {
                return MazeVisualStyleConfig.DEFAULT;
            }
            MazeVisualStyleConfig d = MazeVisualStyleConfig.DEFAULT;
            return new MazeVisualStyleConfig(
                    attr(root, "easyBackgroundImagePath", d.easyBackgroundImagePath()),
                    attr(root, "normalBackgroundImagePath", d.normalBackgroundImagePath()),
                    attr(root, "hardBackgroundImagePath", d.hardBackgroundImagePath()),
                    attr(root, "menuIconImagePath", d.menuIconImagePath()),
                    attr(root, "goalImagePath", d.goalImagePath()),
                    attr(root, "easyWallTypeId", d.easyWallTypeId()),
                    attr(root, "normalWallTypeId", d.normalWallTypeId()),
                    attr(root, "hardWallTypeId", d.hardWallTypeId()),
                    attr(root, "menuMusicPath", d.menuMusicPath()),
                    attr(root, "menuSelectSoundPath", d.menuSelectSoundPath()),
                    attr(root, "inGameMusicPath", d.inGameMusicPath()),
                    attr(root, "winSoundPath", d.winSoundPath()));
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to load visual style XMI", ex);
        }
    }

    private InputStream open() throws IOException {
        if (filePath != null) {
            if (!Files.isRegularFile(filePath)) {
                return null;
            }
            return Files.newInputStream(filePath);
        }
        if (resourceName == null) {
            return null;
        }
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            cl = XmiMazeVisualStyleLoader.class.getClassLoader();
        }
        return cl.getResourceAsStream(resourceName);
    }

    private static String attr(Element root, String key, String fallback) {
        String value = root.getAttribute(key);
        if (value == null || value.isBlank()) {
            return Objects.requireNonNull(fallback);
        }
        return value.trim();
    }
}
