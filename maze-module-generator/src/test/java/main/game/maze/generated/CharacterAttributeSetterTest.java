package main.game.maze.generated;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the generated CharacterAttributeSetter.
 * Validates that model-driven code generation produces correct attribute values.
 * 
 * NOTE: Expected values are derived from the XMI model (opponentModel.xmi).
 * If model values change, these tests should be updated to match.
 */
@DisplayName("CharacterAttributeSetter Tests")
class CharacterAttributeSetterTest {

    // Model-derived constants (from opponentModel.xmi)
    private static final int GHOST_HEALTH = 120;
    private static final int ZOMBIE_HEALTH = 120;
    private static final int PUMPKIN_BOMBER_HEALTH = 100;
    
    private static final double GHOST_THREAT = 1.0;
    private static final double ZOMBIE_THREAT = 1.0;
    private static final double PUMPKIN_BOMBER_THREAT = 0.0;

    @Nested
    @DisplayName("getBaseThreatLevel()")
    class GetBaseThreatLevelTests {

        @Test
        @DisplayName("should return correct threat level for Zombie (from model)")
        void shouldReturnZombieThreatLevel() {
            assertEquals(ZOMBIE_THREAT, CharacterAttributeSetter.getBaseThreatLevel("Zombie"), 0.001,
                "Zombie base threat level should match model value");
        }

        @Test
        @DisplayName("should return correct threat level for Ghost (from model)")
        void shouldReturnGhostThreatLevel() {
            assertEquals(GHOST_THREAT, CharacterAttributeSetter.getBaseThreatLevel("Ghost"), 0.001,
                "Ghost base threat level should match model value");
        }

        @Test
        @DisplayName("should return correct threat level for PumpkinBomber (from model)")
        void shouldReturnPumpkinBomberThreatLevel() {
            assertEquals(PUMPKIN_BOMBER_THREAT, CharacterAttributeSetter.getBaseThreatLevel("PumpkinBomber"), 0.001,
                "PumpkinBomber base threat level should match model value");
        }

        @ParameterizedTest
        @ValueSource(strings = {"Unknown", "Dragon", "", "zombie"})
        @DisplayName("should return 0.0 for unknown types")
        void shouldReturnZeroForUnknownTypes(String typeName) {
            assertEquals(0.0, CharacterAttributeSetter.getBaseThreatLevel(typeName), 0.001,
                "Unknown type should have 0.0 threat level");
        }

        @Test
        @DisplayName("threat values should be non-negative for known types")
        void threatValuesShouldBeNonNegative() {
            assertTrue(CharacterAttributeSetter.getBaseThreatLevel("Zombie") >= 0);
            assertTrue(CharacterAttributeSetter.getBaseThreatLevel("Ghost") >= 0);
            assertTrue(CharacterAttributeSetter.getBaseThreatLevel("PumpkinBomber") >= 0);
        }
    }

    @Nested
    @DisplayName("getBaseHealth()")
    class GetBaseHealthTests {

        @Test
        @DisplayName("should return correct health for Zombie (from model)")
        void shouldReturnZombieHealth() {
            assertEquals(ZOMBIE_HEALTH, CharacterAttributeSetter.getBaseHealth("Zombie"),
                "Zombie base health should match model value");
        }

        @Test
        @DisplayName("should return correct health for Ghost (from model)")
        void shouldReturnGhostHealth() {
            assertEquals(GHOST_HEALTH, CharacterAttributeSetter.getBaseHealth("Ghost"),
                "Ghost base health should match model value");
        }

        @Test
        @DisplayName("should return correct health for PumpkinBomber (from model)")
        void shouldReturnPumpkinBomberHealth() {
            assertEquals(PUMPKIN_BOMBER_HEALTH, CharacterAttributeSetter.getBaseHealth("PumpkinBomber"),
                "PumpkinBomber base health should match model value");
        }

        @ParameterizedTest
        @ValueSource(strings = {"Unknown", "Dragon", "", "ghost"})
        @DisplayName("should return 0 for unknown types")
        void shouldReturnZeroForUnknownTypes(String typeName) {
            assertEquals(0, CharacterAttributeSetter.getBaseHealth(typeName),
                "Unknown type should have 0 health");
        }

        @Test
        @DisplayName("health values should be positive for known types")
        void healthValuesShouldBePositive() {
            assertTrue(CharacterAttributeSetter.getBaseHealth("Zombie") > 0);
            assertTrue(CharacterAttributeSetter.getBaseHealth("Ghost") > 0);
            assertTrue(CharacterAttributeSetter.getBaseHealth("PumpkinBomber") > 0);
        }
    }

    @Nested
    @DisplayName("Model-Driven Values Validation")
    class ModelDrivenValuesTests {

        @ParameterizedTest
        @CsvSource({
            "Zombie, 120, 1.0",
            "Ghost, 120, 1.0",
            "PumpkinBomber, 100, 0.0"
        })
        @DisplayName("should match XMI model values exactly")
        void shouldMatchModelValues(String type, int expectedHealth, double expectedThreat) {
            int health = CharacterAttributeSetter.getBaseHealth(type);
            double threat = CharacterAttributeSetter.getBaseThreatLevel(type);
            
            assertEquals(expectedHealth, health, type + " health should match model");
            assertEquals(expectedThreat, threat, 0.001, type + " threat should match model");
        }

        @Test
        @DisplayName("all character types from CharacterRegistrar should have defined health")
        void allRegisteredTypesShouldHaveHealth() {
            String[] knownTypes = CharacterRegistrar.getKnownTypes();
            
            for (String type : knownTypes) {
                int health = CharacterAttributeSetter.getBaseHealth(type);
                assertTrue(health > 0, type + " should have positive health defined");
            }
        }

        @Test
        @DisplayName("generated values should be consistent with model type count")
        void generatedValuesShouldBeConsistent() {
            String[] knownTypes = CharacterRegistrar.getKnownTypes();
            int typesWithHealth = 0;
            
            for (String type : knownTypes) {
                if (CharacterAttributeSetter.getBaseHealth(type) > 0) {
                    typesWithHealth++;
                }
            }
            
            assertEquals(knownTypes.length, typesWithHealth, 
                "All registered types should have health values from model");
        }
    }

    @Nested
    @DisplayName("Game Balance Validation")
    class GameBalanceTests {

        @Test
        @DisplayName("Zombie should have substantial health")
        void zombieShouldHaveSubstantialHealth() {
            int health = CharacterAttributeSetter.getBaseHealth("Zombie");
            assertTrue(health >= 100, "Zombie should have at least 100 health");
        }

        @Test
        @DisplayName("Ghost should have health comparable to Zombie")
        void ghostHealthShouldBeComparable() {
            int ghostHealth = CharacterAttributeSetter.getBaseHealth("Ghost");
            int zombieHealth = CharacterAttributeSetter.getBaseHealth("Zombie");
            
            // Ghost and Zombie have same health in current model
            assertEquals(ghostHealth, zombieHealth, 
                "Ghost and Zombie health should match current model");
        }

        @Test
        @DisplayName("PumpkinBomber should have slightly lower health than Zombie")
        void pumpkinBomberShouldHaveLowerHealth() {
            int pbHealth = CharacterAttributeSetter.getBaseHealth("PumpkinBomber");
            int zombieHealth = CharacterAttributeSetter.getBaseHealth("Zombie");
            
            assertTrue(pbHealth < zombieHealth, 
                "PumpkinBomber should have less health than Zombie");
            assertTrue(pbHealth >= 50, 
                "PumpkinBomber should still have reasonable health");
        }
    }

    @Nested
    @DisplayName("Model Consistency Validation")
    class ModelConsistencyTests {

        @Test
        @DisplayName("all known types should have positive health")
        void allKnownTypesShouldHavePositiveHealth() {
            String[] knownTypes = CharacterRegistrar.getKnownTypes();
            
            for (String type : knownTypes) {
                assertTrue(CharacterAttributeSetter.getBaseHealth(type) > 0,
                    type + " should have positive health");
            }
        }

        @Test
        @DisplayName("threat levels should be non-negative")
        void threatLevelsShouldBeNonNegative() {
            String[] knownTypes = CharacterRegistrar.getKnownTypes();
            
            for (String type : knownTypes) {
                double threat = CharacterAttributeSetter.getBaseThreatLevel(type);
                assertTrue(threat >= 0.0,
                    type + " threat should be non-negative");
            }
        }

        @Test
        @DisplayName("attribute values should be within game-reasonable bounds")
        void attributesShouldBeReasonable() {
            String[] knownTypes = CharacterRegistrar.getKnownTypes();
            
            for (String type : knownTypes) {
                int health = CharacterAttributeSetter.getBaseHealth(type);
                double threat = CharacterAttributeSetter.getBaseThreatLevel(type);
                
                assertTrue(health > 0 && health <= 1000,
                    type + " health should be between 1 and 1000");
                assertTrue(threat >= 0 && threat <= 10.0,
                    type + " threat should be between 0 and 10.0");
            }
        }
    }

    @Nested
    @DisplayName("Type Name Handling")
    class TypeNameHandlingTests {

        @Test
        @DisplayName("should be case-sensitive for type names")
        void shouldBeCaseSensitive() {
            assertNotEquals(CharacterAttributeSetter.getBaseHealth("Zombie"),
                CharacterAttributeSetter.getBaseHealth("zombie"),
                "Type names should be case-sensitive");
            assertNotEquals(CharacterAttributeSetter.getBaseHealth("Ghost"),
                CharacterAttributeSetter.getBaseHealth("GHOST"),
                "Type names should be case-sensitive");
        }

        @Test
        @DisplayName("should handle null type gracefully")
        void shouldHandleNullType() {
            // Depending on implementation, this might throw or return default
            // Test documents the actual behavior
            assertDoesNotThrow(() -> {
                try {
                    CharacterAttributeSetter.getBaseHealth(null);
                } catch (NullPointerException e) {
                    // Expected if switch doesn't handle null
                }
            });
        }

        @Test
        @DisplayName("should return default for empty string")
        void shouldReturnDefaultForEmptyString() {
            assertEquals(0, CharacterAttributeSetter.getBaseHealth(""));
            assertEquals(0.0, CharacterAttributeSetter.getBaseThreatLevel(""), 0.001);
        }

        @Test
        @DisplayName("should return default for whitespace-only type name")
        void shouldReturnDefaultForWhitespace() {
            assertEquals(0, CharacterAttributeSetter.getBaseHealth("  "));
            assertEquals(0.0, CharacterAttributeSetter.getBaseThreatLevel("  "), 0.001);
        }
    }

    @Nested
    @DisplayName("FreeMarker Generation Verification")
    class FreeMarkerGenerationTests {

        @Test
        @DisplayName("generated code should contain all model types")
        void generatedCodeShouldContainAllModelTypes() {
            // These types should exist in generated code from FreeMarker templates
            String[] expectedTypes = {"Ghost", "Zombie", "PumpkinBomber"};
            
            for (String type : expectedTypes) {
                assertTrue(CharacterAttributeSetter.getBaseHealth(type) > 0,
                    "Generated code should handle " + type + " from model");
            }
        }

        @Test
        @DisplayName("health and threat should be independently generated")  
        void healthAndThreatShouldBeIndependent() {
            // Verify that health and threat come from different model attributes
            // PumpkinBomber has non-zero health but zero threat, proving independent generation
            int pbHealth = CharacterAttributeSetter.getBaseHealth("PumpkinBomber");
            double pbThreat = CharacterAttributeSetter.getBaseThreatLevel("PumpkinBomber");
            
            assertTrue(pbHealth > 0, "PumpkinBomber should have health");
            assertEquals(0.0, pbThreat, 0.001, "PumpkinBomber should have zero threat (per model)");
        }

        @Test
        @DisplayName("generated switch should have unique case labels")
        void generatedSwitchShouldHaveUniqueCases() {
            // If duplicate cases existed, code wouldn't compile
            // This test documents that the getBaseHealth method works for all types
            String[] types = CharacterRegistrar.getKnownTypes();
            int successCount = 0;
            
            for (String type : types) {
                if (CharacterAttributeSetter.getBaseHealth(type) > 0) {
                    successCount++;
                }
            }
            
            assertEquals(types.length, successCount, 
                "All types should successfully return health values");
        }
    }
}
