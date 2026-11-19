package main.game.maze.common.graphics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("DamageStateResolver")
class DamageStateResolverTest {

    private Logger resolverLogger;
    private TestLogHandler logHandler;

    @BeforeEach
    void setUpLogger() {
        resolverLogger = Logger.getLogger(DamageStateResolver.class.getName());
        logHandler = new TestLogHandler();
        resolverLogger.addHandler(logHandler);
    }

    @AfterEach
    void tearDownLogger() {
        if (resolverLogger != null && logHandler != null) {
            resolverLogger.removeHandler(logHandler);
        }
    }

    @Test
    @DisplayName("null or blank base path returns base path unchanged")
    void nullOrBlankBasePathReturnsUnchanged() {
        assertNull(DamageStateResolver.deriveStatePath(null, DamageState.DAMAGED));
        assertEquals("", DamageStateResolver.deriveStatePath("", DamageState.DAMAGED));
        assertEquals("   ", DamageStateResolver.deriveStatePath("   ", DamageState.DAMAGED));
    }

    @Test
    @DisplayName("null state or HEALTHY state returns base path unchanged")
    void healthyOrNullStateReturnsBasePathUnchanged() {
        assertEquals("zombie1-right.png", DamageStateResolver.deriveStatePath("zombie1-right.png", null));
        assertEquals("zombie1-right.png", DamageStateResolver.deriveStatePath("zombie1-right.png", DamageState.HEALTHY));
        assertEquals("/main/game/maze/pumpkinbomber.png",
                DamageStateResolver.deriveStatePath("/main/game/maze/pumpkinbomber.png", DamageState.HEALTHY));
    }

    @ParameterizedTest(name = "{0} + {1} -> {2}")
    @CsvSource({
        "zombie1-right.png, DAMAGED, zombie1-damaged-right.png",
        "zombie1-left.png, BLEEDING, zombie1-bleeding-left.png",
        "zombie2-up.png, TIRED, zombie2-tired-up.png",
        "zombie3-down.png, DAMAGED, zombie3-damaged-down.png",
        "/main/game/maze/zombie1-right.png, DAMAGED, /main/game/maze/zombie1-damaged-right.png",
        "/main/game/maze/zombie2-down.png, BLEEDING, /main/game/maze/zombie2-bleeding-down.png",
        "C:\\assets\\zombie3-up.png, TIRED, C:\\assets\\zombie3-tired-up.png"
    })
    @DisplayName("direction-suffixed paths insert damage state label before suffix")
    void directionSuffixedPathsInsertStateBeforeSuffix(String basePath, DamageState state, String expected) {
        assertEquals(expected, DamageStateResolver.deriveStatePath(basePath, state));
    }

    @ParameterizedTest(name = "{0} + {1} -> {2}")
    @CsvSource({
        "pumpkinbomber.png, DAMAGED, pumpkinbomber-damaged.png",
        "/main/game/maze/shooter.png, BLEEDING, /main/game/maze/shooter-bleeding.png",
        "boss-character.jpg, TIRED, boss-character-tired.jpg",
        "enemy, DAMAGED, enemy-damaged"
    })
    @DisplayName("non-suffixed paths insert damage state label before extension")
    void nonSuffixedPathsInsertStateBeforeExtension(String basePath, DamageState state, String expected) {
        assertEquals(expected, DamageStateResolver.deriveStatePath(basePath, state));
    }

    @Test
    @DisplayName("resolveWithFallback returns derived path when resource exists on classpath")
    void resolveWithFallbackReturnsDerivedPathWhenResourceExists() {
        String base = "main/game/maze/common/graphics/test-right.png";
        String resolved = DamageStateResolver.resolveWithFallback(
                DamageStateResolverTest.class, base, DamageState.DAMAGED);
        assertEquals("main/game/maze/common/graphics/test-damaged-right.png", resolved);

        String baseNonDirectional = "main/game/maze/common/graphics/test.png";
        String resolvedNonDirectional = DamageStateResolver.resolveWithFallback(
                DamageStateResolverTest.class, baseNonDirectional, DamageState.DAMAGED);
        assertEquals("main/game/maze/common/graphics/test-damaged.png", resolvedNonDirectional);
    }

    @Test
    @DisplayName("resolveWithFallback returns base path when derived resource is missing, with no warning logs")
    void resolveWithFallbackReturnsBasePathWhenResourceMissingWithoutWarning() {
        String base = "main/game/maze/common/graphics/test-right.png";
        // BLEEDING state resource doesn't exist
        String resolved = DamageStateResolver.resolveWithFallback(
                DamageStateResolverTest.class, base, DamageState.BLEEDING);
        assertEquals(base, resolved);

        // Verify no WARNING or SEVERE logs were emitted
        assertTrue(logHandler.records.stream()
                .noneMatch(r -> r.getLevel().intValue() >= Level.WARNING.intValue()),
                "Expected no WARNING or higher log records when resource is missing");
    }

    @Test
    @DisplayName("resolveWithFallback with null/blank/HEALTHY returns base path directly")
    void resolveWithFallbackNullOrHealthyReturnsBaseDirectly() {
        assertNull(DamageStateResolver.resolveWithFallback(null, DamageState.DAMAGED));
        assertEquals("", DamageStateResolver.resolveWithFallback("", DamageState.DAMAGED));
        assertEquals("some/path.png", DamageStateResolver.resolveWithFallback("some/path.png", DamageState.HEALTHY));
        assertEquals("some/path.png", DamageStateResolver.resolveWithFallback("some/path.png", null));
    }

    private static class TestLogHandler extends Handler {
        final List<LogRecord> records = new ArrayList<>();

        @Override
        public void publish(LogRecord record) {
            records.add(record);
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() throws SecurityException {
        }
    }
}
