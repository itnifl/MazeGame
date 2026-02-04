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
    @DisplayName("getBaseDamage()")
    class GetBaseDamageTests {

        @Test
        @DisplayName("should return correct damage for Zombie")
        void shouldReturnZombieDamage() {
            assertEquals(10, CharacterAttributeSetter.getBaseDamage("Zombie"),
                "Zombie base damage should be 10");
        }

        @Test
        @DisplayName("should return correct damage for Ghost")
        void shouldReturnGhostDamage() {
            assertEquals(5, CharacterAttributeSetter.getBaseDamage("Ghost"),
                "Ghost base damage should be 5");
        }

        @Test
        @DisplayName("should return correct damage for PumpkinBomber")
        void shouldReturnPumpkinBomberDamage() {
            assertEquals(15, CharacterAttributeSetter.getBaseDamage("PumpkinBomber"),
                "PumpkinBomber base damage should be 15");
        }

        @ParameterizedTest
        @ValueSource(strings = {"Unknown", "Dragon", "", "zombie"})
        @DisplayName("should return 0 for unknown types")
        void shouldReturnZeroForUnknownTypes(String typeName) {
            assertEquals(0, CharacterAttributeSetter.getBaseDamage(typeName),
                "Unknown type should have 0 damage");
        }

        @Test
        @DisplayName("damage values should be positive for known types")
        void damageValuesShouldBePositive() {
            assertTrue(CharacterAttributeSetter.getBaseDamage("Zombie") > 0);
            assertTrue(CharacterAttributeSetter.getBaseDamage("Ghost") > 0);
            assertTrue(CharacterAttributeSetter.getBaseDamage("PumpkinBomber") > 0);
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
            "Zombie, 100, 10",
            "Ghost, 50, 5",
            "PumpkinBomber, 75, 15"
        })
        @DisplayName("should have balanced health-to-damage ratios")
        void shouldHaveBalancedRatios(String type, int expectedHealth, int expectedDamage) {
            int health = CharacterAttributeSetter.getBaseHealth(type);
            int damage = CharacterAttributeSetter.getBaseDamage(type);
            
            assertEquals(expectedHealth, health, type + " health mismatch");
            assertEquals(expectedDamage, damage, type + " damage mismatch");
            
            // Validate health/damage ratio is reasonable (between 5 and 20)
            double ratio = (double) health / damage;
            assertTrue(ratio >= 5 && ratio <= 20,
                type + " health/damage ratio should be between 5 and 20, was: " + ratio);
        }

        @Test
        @DisplayName("Zombie should be tank-like (high health, medium damage)")
        void zombieShouldBeTank() {
            int health = CharacterAttributeSetter.getBaseHealth("Zombie");
            int damage = CharacterAttributeSetter.getBaseDamage("Zombie");
            
            assertTrue(health >= 100, "Zombie should have high health");
            assertTrue(damage >= 5 && damage <= 15, "Zombie should have medium damage");
        }

        @Test
        @DisplayName("Ghost should be fragile (low health, low damage)")
        void ghostShouldBeFragile() {
            int health = CharacterAttributeSetter.getBaseHealth("Ghost");
            int damage = CharacterAttributeSetter.getBaseDamage("Ghost");
            
            assertTrue(health <= 75, "Ghost should have lower health");
            assertTrue(damage <= 10, "Ghost should have lower damage");
        }

        @Test
        @DisplayName("PumpkinBomber should be glass cannon (medium health, high damage)")
        void pumpkinBomberShouldBeGlassCannon() {
            int health = CharacterAttributeSetter.getBaseHealth("PumpkinBomber");
            int damage = CharacterAttributeSetter.getBaseDamage("PumpkinBomber");
            
            assertTrue(health > 50 && health < 100, "PumpkinBomber should have medium health");
            assertTrue(damage >= 10, "PumpkinBomber should have higher damage");
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
                assertTrue(CharacterAttributeSetter.getBaseDamage(type) > 0,
                    type + " should have positive damage");
            }
        }

        @Test
        @DisplayName("attribute values should be within game-reasonable bounds")
        void attributesShouldBeReasonable() {
            String[] knownTypes = CharacterRegistrar.getKnownTypes();
            
            for (String type : knownTypes) {
                int health = CharacterAttributeSetter.getBaseHealth(type);
                int damage = CharacterAttributeSetter.getBaseDamage(type);
                
                assertTrue(health > 0 && health <= 1000,
                    type + " health should be between 1 and 1000");
                assertTrue(damage > 0 && damage <= 100,
                    type + " damage should be between 1 and 100");
            }
        }
    }
}
