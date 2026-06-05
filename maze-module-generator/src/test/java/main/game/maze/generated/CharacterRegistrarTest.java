package main.game.maze.generated;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the generated CharacterRegistrar.
 * Validates that model-driven code generation produces correct type dispatch.
 */
@DisplayName("CharacterRegistrar Tests")
class CharacterRegistrarTest {

    @Nested
    @DisplayName("getKnownTypes()")
    class GetKnownTypesTests {

        @Test
        @DisplayName("should return all character types from the model")
        void shouldReturnAllCharacterTypes() {
            String[] types = CharacterRegistrar.getKnownTypes();
            
            assertNotNull(types, "Known types should not be null");
            assertEquals(3, types.length, "Should have exactly 3 character types");
        }

        @Test
        @DisplayName("should include Zombie type")
        void shouldIncludeZombie() {
            String[] types = CharacterRegistrar.getKnownTypes();
            assertTrue(containsType(types, "Zombie"), "Should include Zombie");
        }

        @Test
        @DisplayName("should include Ghost type")
        void shouldIncludeGhost() {
            String[] types = CharacterRegistrar.getKnownTypes();
            assertTrue(containsType(types, "Ghost"), "Should include Ghost");
        }

        @Test
        @DisplayName("should include PumpkinBomber type")
        void shouldIncludePumpkinBomber() {
            String[] types = CharacterRegistrar.getKnownTypes();
            assertTrue(containsType(types, "PumpkinBomber"), "Should include PumpkinBomber");
        }

        private boolean containsType(String[] types, String typeName) {
            for (String type : types) {
                if (type.equals(typeName)) return true;
            }
            return false;
        }
    }

    @Nested
    @DisplayName("isKnownType()")
    class IsKnownTypeTests {

        @ParameterizedTest
        @ValueSource(strings = {"Zombie", "Ghost", "PumpkinBomber"})
        @DisplayName("should return true for known types")
        void shouldReturnTrueForKnownTypes(String typeName) {
            assertTrue(CharacterRegistrar.isKnownType(typeName),
                typeName + " should be recognized as a known type");
        }

        @ParameterizedTest
        @ValueSource(strings = {"Unknown", "Dragon", "Skeleton", "", "zombie"})
        @DisplayName("should return false for unknown types")
        void shouldReturnFalseForUnknownTypes(String typeName) {
            assertFalse(CharacterRegistrar.isKnownType(typeName),
                typeName + " should NOT be recognized as a known type");
        }

        @Test
        @DisplayName("should be case-sensitive")
        void shouldBeCaseSensitive() {
            assertFalse(CharacterRegistrar.isKnownType("zombie"), 
                "Type checking should be case-sensitive");
            assertFalse(CharacterRegistrar.isKnownType("ZOMBIE"), 
                "Type checking should be case-sensitive");
            assertTrue(CharacterRegistrar.isKnownType("Zombie"), 
                "Exact case should match");
        }
    }

    @Nested
    @DisplayName("Model-Driven Generation Validation")
    class ModelDrivenValidationTests {

        @Test
        @DisplayName("known types array should match isKnownType checks")
        void knownTypesShouldBeConsistent() {
            String[] knownTypes = CharacterRegistrar.getKnownTypes();
            
            for (String type : knownTypes) {
                assertTrue(CharacterRegistrar.isKnownType(type),
                    "Each type in getKnownTypes() should pass isKnownType() check: " + type);
            }
        }

        @Test
        @DisplayName("should have reasonable number of types for a maze game")
        void shouldHaveReasonableTypeCount() {
            String[] types = CharacterRegistrar.getKnownTypes();
            assertTrue(types.length >= 1, "Should have at least 1 character type");
            assertTrue(types.length <= 20, "Should have reasonable upper bound on types");
        }
    }
}


