package main.game.maze.common.graphics;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link DamagePathResolver} that derives paths with {@link DamageStateResolver#deriveStatePath}
 * and checks each derived path once. Renderers call it every frame, so the existence check
 * (a classpath lookup) must not repeat. Thread-safe.
 */
public final class CachingDamagePathResolver implements DamagePathResolver {

    private static final Logger LOGGER = Logger.getLogger(CachingDamagePathResolver.class.getName());

    private record Key(String basePath, DamageState state) {
    }

    private final Predicate<String> resourceExists;
    private final Map<Key, String> resolved = new ConcurrentHashMap<>();

    /** @param resourceExists answers whether a derived sprite path can be loaded */
    public CachingDamagePathResolver(Predicate<String> resourceExists) {
        this.resourceExists = resourceExists;
    }

    @Override
    public String resolve(String basePath, DamageState state) {
        if (basePath == null || basePath.isBlank() || state == null || state == DamageState.HEALTHY) {
            return basePath;
        }
        return resolved.computeIfAbsent(new Key(basePath, state), this::lookup);
    }

    private String lookup(Key key) {
        String derived = DamageStateResolver.deriveStatePath(key.basePath(), key.state());
        if (resourceExists.test(derived)) {
            return derived;
        }
        LOGGER.log(Level.FINE, "Damage state sprite not found: {0}; using {1}",
                new Object[] {derived, key.basePath()});
        return key.basePath();
    }
}
