package main.game.maze.common.graphics;

import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Shared utility for resolving enemy visual damage-state sprite paths.
 *
 * <p>Convention:
 * <ul>
 *   <li>If state is {@link DamageState#HEALTHY}, the base path is returned as-is.</li>
 *   <li>For paths with a direction suffix ({@code -left}, {@code -right}, {@code -up},
 *       {@code -down}), the state label is inserted before the suffix.
 *       Example: {@code zombie1-right.png} &rarr; {@code zombie1-damaged-right.png}.</li>
 *   <li>For paths without a direction suffix, the state label is inserted before
 *       the file extension. Example: {@code pumpkinbomber.png} &rarr; {@code pumpkinbomber-damaged.png}.</li>
 * </ul>
 *
 * <p>When resolving with fallback, if the derived resource cannot be located on the
 * classpath, the base path is returned silently without throwing or logging warnings.
 */
public final class DamageStateResolver {

    private static final Logger LOGGER = Logger.getLogger(DamageStateResolver.class.getName());
    private static final List<String> DIRECTION_SUFFIXES = List.of("-left", "-right", "-up", "-down");

    private DamageStateResolver() {
    }

    /**
     * Derives the state-specific sprite path according to naming conventions.
     *
     * @param basePath the base sprite path (e.g. {@code /main/game/maze/zombie1-right.png})
     * @param state the current damage state
     * @return the derived path with the state label inserted, or {@code basePath}
     *         if state is null/HEALTHY or basePath is blank
     */
    public static String deriveStatePath(String basePath, DamageState state) {
        if (basePath == null || basePath.isBlank() || state == null || state == DamageState.HEALTHY) {
            return basePath;
        }

        int lastSlash = Math.max(basePath.lastIndexOf('/'), basePath.lastIndexOf('\\'));
        String parent = lastSlash >= 0 ? basePath.substring(0, lastSlash + 1) : "";
        String filename = lastSlash >= 0 ? basePath.substring(lastSlash + 1) : basePath;

        int lastDot = filename.lastIndexOf('.');
        String baseName = lastDot >= 0 ? filename.substring(0, lastDot) : filename;
        String ext = lastDot >= 0 ? filename.substring(lastDot) : "";

        String stateLabel = state.label();
        String lowerBaseName = baseName.toLowerCase(Locale.ROOT);

        for (String suffix : DIRECTION_SUFFIXES) {
            if (lowerBaseName.endsWith(suffix)) {
                String prefix = baseName.substring(0, baseName.length() - suffix.length());
                String derivedFilename = prefix + "-" + stateLabel + suffix + ext;
                return parent + derivedFilename;
            }
        }

        String derivedFilename = baseName + "-" + stateLabel + ext;
        return parent + derivedFilename;
    }

    /**
     * Attempts to resolve the derived state path on the classpath via the provided anchor class.
     * If the derived resource does not exist, returns {@code basePath} and logs at FINE level.
     *
     * @param anchor the class used as a classpath anchor (if null, defaults to {@code DamageStateResolver.class})
     * @param basePath the original base sprite path
     * @param state the current damage state
     * @return the derived path if available on the classpath, otherwise {@code basePath}
     */
    public static String resolveWithFallback(Class<?> anchor, String basePath, DamageState state) {
        if (basePath == null || basePath.isBlank() || state == null || state == DamageState.HEALTHY) {
            return basePath;
        }

        String derivedPath = deriveStatePath(basePath, state);
        Class<?> clazz = anchor != null ? anchor : DamageStateResolver.class;

        if (resourceExists(clazz, derivedPath)) {
            return derivedPath;
        }

        LOGGER.log(Level.FINE, "Derived damage state sprite not found on classpath: {0}; falling back to: {1}",
                new Object[]{derivedPath, basePath});
        return basePath;
    }

    /**
     * Convenience overload for {@link #resolveWithFallback(Class, String, DamageState)}
     * using {@code DamageStateResolver.class} as the classpath anchor.
     *
     * @param basePath the original base sprite path
     * @param state the current damage state
     * @return the derived path if available on the classpath, otherwise {@code basePath}
     */
    public static String resolveWithFallback(String basePath, DamageState state) {
        return resolveWithFallback(DamageStateResolver.class, basePath, state);
    }

    private static boolean resourceExists(Class<?> clazz, String path) {
        if (path == null || path.isBlank()) {
            return false;
        }

        URL url = clazz.getResource(path);
        if (url != null) {
            return true;
        }

        if (!path.startsWith("/")) {
            url = clazz.getResource("/" + path);
            if (url != null) {
                return true;
            }
        }

        if (clazz.getClassLoader() != null) {
            String noSlash = path.startsWith("/") ? path.substring(1) : path;
            url = clazz.getClassLoader().getResource(noSlash);
            if (url != null) {
                return true;
            }
        }

        return false;
    }
}
