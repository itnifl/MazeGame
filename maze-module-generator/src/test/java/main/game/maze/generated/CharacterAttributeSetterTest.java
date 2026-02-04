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
 */
@DisplayName("CharacterAttributeSetter Tests")
class CharacterAttributeSetterTest {

    @Nested
    @DisplayName("getBaseThreatLevel()")
    class GetBaseThreatLevelTests {

        @Test
        @DisplayName("should return correct threat level for Zombie")
        void shouldReturnZombieThreatLevel() {
            assertEquals(1.0, CharacterAttributeSetter.getBaseThreatLevel("Zombie"), 0.001,
                "Zombie base threat level should be 1.0");
        }

        @Test
        @DisplayName("should return correct threat level for Ghost")
        void shouldReturnGhostThreatLevel() {
            assertEquals(0.5, CharacterAttributeSetter.getBaseThreatLevel("Ghost"), 0.001,
                "Ghost base threat level should be 0.5");
        }

        @Test
        @DisplayName("should return correct threat level for PumpkinBomber")
        void shouldReturnPumpkinBomberThreatLevel() {
            assertEquals(1.5, CharacterAttributeSetter.getBaseThreatLevel("PumpkinBomber"), 0.001,
                "PumpkinBomber base threat level should be 1.5");
        }

        @ParameterizedTest
        @ValueSource(strings = {"Unknown", "Dragon", "", "zombie"})
        @DisplayName("should return 0.0 for unknown types")
        void shouldReturnZeroForUnknownTypes(String typeName) {
            assertEquals(0.0, CharacterAttributeSetter.getBaseThreatLevel(typeName), 0.001,
                "Unknown type should have 0.0 threat level");
        }

        @Test
        @DisplayName("threat values should be positive for known types")
        void threatValuesShouldBePositive() {
            assertTrue(CharacterAttributeSetter.getBaseThreatLevel("Zombie") > 0);
            assertTrue(CharacterAttributeSetter.getBaseThreatLevel("Ghost") > 0);
            assertTrue(CharacterAttributeSetter.getBaseThreatLevel("PumpkinBomber") > 0);
        }
    }

    @Nested
    @DisplayName("getBaseHealth()")
    class GetBaseHealthTests {

        @Test
        @DisplayName("should return correct health for Zombie")
        void shouldReturnZombieHealth() {
            assertEquals(100, CharacterAttributeSetter.getBaseHealth("Zombie"),
                "Zombie base health should be 100");
        }

        @Test
        @DisplayName("should return correct health for Ghost")
        void shouldReturnGhostHealth() {
            assertEquals(50, CharacterAttributeSetter.getBaseHealth("Ghost"),
                "Ghost base health should be 50");
        }

        @Test
        @DisplayName("should return correct health for PumpkinBomber")
        void shouldReturnPumpkinBomberHealth() {
            assertEquals(75, CharacterAttributeSetter.getBaseHealth("PumpkinBomber"),
                "PumpkinBomber base health should be 75");
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
    @DisplayName("Game Balance Validation")
    class GameBalanceTests {

        @ParameterizedTest
        @CsvSource({
            "Zombie, 100, 1.0",
            "Ghost, 50, 0.5",
            "PumpkinBomber, 75, 1.5"
        })
        @DisplayName("should have balanced health-to-threat ratios")
        void shouldHaveBalancedRatios(String type, int expectedHealth, double expectedThreat) {
            int health = CharacterAttributeSetter.getBaseHealth(type);
            double threat = CharacterAttributeSetter.getBaseThreatLevel(type);
            
            assertEquals(expectedHealth, health, type + " health mismatch");
            assertEquals(expectedThreat, threat, 0.001, type + " threat mismatch");
        }

        @Test
        @DisplayName("Zombie should be tank-like (high health, medium threat)")
        void zombieShouldBeTank() {
            int health = CharacterAttributeSetter.getBaseHealth("Zombie");
            double threat = CharacterAttributeSetter.getBaseThreatLevel("Zombie");
            
            assertTrue(health >= 100, "Zombie should have high health");
            assertTrue(threat >= 0.5 && threat <= 1.5, "Zombie should have medium threat");
        }

        @Test
        @DisplayName("Ghost should be fragile (low health, low threat)")
        void ghostShouldBeFragile() {
            int health = CharacterAttributeSetter.getBaseHealth("Ghost");
            double threat = CharacterAttributeSetter.getBaseThreatLevel("Ghost");
            
            assertTrue(health <= 75, "Ghost should have lower health");
            assertTrue(threat <= 1.0, "Ghost should have lower threat");
        }

        @Test
        @DisplayName("PumpkinBomber should be glass cannon (medium health, high threat)")
        void pumpkinBomberShouldBeGlassCannon() {
            int health = CharacterAttributeSetter.getBaseHealth("PumpkinBomber");
            double threat = CharacterAttributeSetter.getBaseThreatLevel("PumpkinBomber");
            
            assertTrue(health > 50 && health < 100, "PumpkinBomber should have medium health");
            assertTrue(threat >= 1.0, "PumpkinBomber should have higher threat");
        }
    }

    @Nested
    @DisplayName("Model Consistency Validation")
    class ModelConsistencyTests {

        @Test
        @DisplayName("all known types should have non-zero stats")
        void allKnownTypesShouldHaveStats() {
            String[] knownTypes = CharacterRegistrar.getKnownTypes();
            
            for (String type : knownTypes) {
                assertTrue(CharacterAttributeSetter.getBaseHealth(type) > 0,
                    type + " should have positive health");
                assertTrue(CharacterAttributeSetter.getBaseThreatLevel(type) > 0,
                    type + " should have positive threat level");
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
                assertTrue(threat > 0 && threat <= 10.0,
                    type + " threat should be between 0.1 and 10.0");
            }
        }
    }
}
