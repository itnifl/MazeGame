package main.game.maze.generated;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the generated OpponentRegistry.
 * Validates that model-driven code generation produces correct opponent registry.
 */
@DisplayName("OpponentRegistry Tests")
class OpponentRegistryTest {

    /**
     * Helper to capture output from both System.out and Logger.
     * Works with both old generated code (System.out) and new (Logger).
     */
    static class OutputCapture {
        private final ByteArrayOutputStream sysOutStream = new ByteArrayOutputStream();
        private final PrintStream originalOut = System.out;
        private final List<String> logMessages = new ArrayList<>();
        private final Logger logger = Logger.getLogger(OpponentRegistry.class.getName());
        private Handler logHandler;

        void start() {
            System.setOut(new PrintStream(sysOutStream));
            logHandler = new Handler() {
                @Override
                public void publish(LogRecord record) {
                    logMessages.add(record.getMessage());
                }
                @Override
                public void flush() {}
                @Override
                public void close() throws SecurityException {}
            };
            logHandler.setLevel(Level.ALL);
            logger.addHandler(logHandler);
            logger.setLevel(Level.ALL);
        }

        void stop() {
            System.setOut(originalOut);
            if (logHandler != null) {
                logger.removeHandler(logHandler);
            }
        }

        String getOutput() {
            StringBuilder sb = new StringBuilder();
            sb.append(sysOutStream.toString());
            for (String msg : logMessages) {
                sb.append(msg).append("\n");
            }
            return sb.toString();
        }
    }

    @Nested
    @DisplayName("GAME_NAME constant")
    class GameNameTests {

        @Test
        @DisplayName("should have non-null game name")
        void shouldHaveNonNullGameName() {
            assertNotNull(OpponentRegistry.GAME_NAME, "GAME_NAME should not be null");
        }

        @Test
        @DisplayName("should have non-empty game name")
        void shouldHaveNonEmptyGameName() {
            assertFalse(OpponentRegistry.GAME_NAME.isEmpty(), 
                "GAME_NAME should not be empty");
        }

        @Test
        @DisplayName("should match expected game name from model")
        void shouldMatchExpectedGameName() {
            assertEquals("Testing Game", OpponentRegistry.GAME_NAME,
                "GAME_NAME should match the value from the opponent model");
        }
    }

    @Nested
    @DisplayName("listEnemies() method")
    class ListEnemiesTests {

        @Test
        @DisplayName("should output enemy information without throwing exceptions")
        void shouldOutputEnemyInformation() {
            assertDoesNotThrow(() -> {
                OpponentRegistry.listEnemies();
            }, "listEnemies() should execute without throwing exceptions");
        }

        @Test
        @DisplayName("should output enemy information")
        void shouldOutputToSystemOutOrLogger() {
            OutputCapture capture = new OutputCapture();
            capture.start();
            
            try {
                OpponentRegistry.listEnemies();
                String output = capture.getOutput();
                
                assertFalse(output.isEmpty(), "Should produce output");
                assertTrue(output.contains("Enemy:"), "Output should contain 'Enemy:' prefix");
            } finally {
                capture.stop();
            }
        }

        @Test
        @DisplayName("should list enemy types from the model")
        void shouldListEnemyTypesFromModel() {
            OutputCapture capture = new OutputCapture();
            capture.start();
            
            try {
                OpponentRegistry.listEnemies();
                String output = capture.getOutput();
                
                assertTrue(output.contains("Zombie"), "Should list Zombie enemies");
                assertTrue(output.contains("Ghost"), "Should list Ghost enemies");
                assertTrue(output.contains("Pumpkin Bomber"), "Should list Pumpkin Bomber enemies");
            } finally {
                capture.stop();
            }
        }

        @Test
        @DisplayName("should include health information for enemies")
        void shouldIncludeHealthInformation() {
            OutputCapture capture = new OutputCapture();
            capture.start();
            
            try {
                OpponentRegistry.listEnemies();
                String output = capture.getOutput();
                
                assertTrue(output.contains("Health:"), "Should include health information");
                assertTrue(output.matches("(?s).*Health:\\s*\\d+.*"), 
                    "Health should be followed by a number");
            } finally {
                capture.stop();
            }
        }

        @Test
        @DisplayName("should output multiple enemy entries")
        void shouldOutputMultipleEnemyEntries() {
            OutputCapture capture = new OutputCapture();
            capture.start();
            
            try {
                OpponentRegistry.listEnemies();
                String output = capture.getOutput();
                
                int enemyCount = 0;
                String[] lines = output.split("\n");
                for (String line : lines) {
                    if (line.contains("Enemy:")) {
                        enemyCount++;
                    }
                }
                
                assertTrue(enemyCount > 0, "Should list at least one enemy");
            } finally {
                capture.stop();
            }
        }
    }

    @Nested
    @DisplayName("Model-Driven Generation Validation")
    class ModelDrivenValidationTests {

        @Test
        @DisplayName("GAME_NAME should be generated from model")
        void gameNameShouldBeFromModel() {
            // Verify that GAME_NAME is a public static final field
            try {
                java.lang.reflect.Field field = OpponentRegistry.class.getField("GAME_NAME");
                assertTrue(java.lang.reflect.Modifier.isPublic(field.getModifiers()),
                    "GAME_NAME should be public");
                assertTrue(java.lang.reflect.Modifier.isStatic(field.getModifiers()),
                    "GAME_NAME should be static");
                assertTrue(java.lang.reflect.Modifier.isFinal(field.getModifiers()),
                    "GAME_NAME should be final");
            } catch (NoSuchFieldException e) {
                fail("GAME_NAME field should exist");
            }
        }

        @Test
        @DisplayName("registry class should have proper visibility")
        void registryClassShouldHaveProperVisibility() {
            assertTrue(java.lang.reflect.Modifier.isPublic(OpponentRegistry.class.getModifiers()),
                "OpponentRegistry class should be public");
        }

        @Test
        @DisplayName("listEnemies method should be public and static")
        void listEnemiesMethodShouldBePublicStatic() {
            try {
                java.lang.reflect.Method method = OpponentRegistry.class.getMethod("listEnemies");
                assertTrue(java.lang.reflect.Modifier.isPublic(method.getModifiers()),
                    "listEnemies() should be public");
                assertTrue(java.lang.reflect.Modifier.isStatic(method.getModifiers()),
                    "listEnemies() should be static");
            } catch (NoSuchMethodException e) {
                fail("listEnemies() method should exist");
            }
        }

        @Test
        @DisplayName("generated code should be deterministic")
        void generatedCodeShouldBeDeterministic() {
            OutputCapture capture1 = new OutputCapture();
            OutputCapture capture2 = new OutputCapture();
            
            capture1.start();
            try {
                OpponentRegistry.listEnemies();
            } finally {
                capture1.stop();
            }
            
            capture2.start();
            try {
                OpponentRegistry.listEnemies();
            } finally {
                capture2.stop();
            }
            
            assertEquals(capture1.getOutput(), capture2.getOutput(),
                "Multiple calls should produce identical output");
        }
    }
}
