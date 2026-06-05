package main.game.maze.generated;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the generated CharacterGraphicsFactory.
 * Validates that model-driven code generation produces correct graphics mappings.
 */
@DisplayName("CharacterGraphicsFactory Tests")
class CharacterGraphicsFactoryTest {

    @Nested
    @DisplayName("getAnimationFrameCount()")
    class GetAnimationFrameCountTests {

        @Test
        @DisplayName("should return correct frame count for Zombie")
        void shouldReturnZombieFrameCount() {
            assertEquals(4, CharacterGraphicsFactory.getAnimationFrameCount("Zombie"),
                "Zombie should have 4 animation frames");
        }

        @Test
        @DisplayName("should return correct frame count for Ghost")
        void shouldReturnGhostFrameCount() {
            assertEquals(6, CharacterGraphicsFactory.getAnimationFrameCount("Ghost"),
                "Ghost should have 6 animation frames");
        }

        @Test
        @DisplayName("should return correct frame count for PumpkinBomber")
        void shouldReturnPumpkinBomberFrameCount() {
            assertEquals(4, CharacterGraphicsFactory.getAnimationFrameCount("PumpkinBomber"),
                "PumpkinBomber should have 4 animation frames");
        }

        @ParameterizedTest
        @ValueSource(strings = {"Unknown", "Dragon", ""})
        @DisplayName("should return 1 for unknown types (static sprite)")
        void shouldReturnOneForUnknownTypes(String typeName) {
            assertEquals(1, CharacterGraphicsFactory.getAnimationFrameCount(typeName),
                "Unknown type should have 1 frame (static)");
        }

        @Test
        @DisplayName("frame counts should be positive for all known types")
        void frameCountsShouldBePositive() {
            String[] knownTypes = CharacterRegistrar.getKnownTypes();
            
            for (String type : knownTypes) {
                int frames = CharacterGraphicsFactory.getAnimationFrameCount(type);
                assertTrue(frames >= 1, type + " should have at least 1 frame");
            }
        }
    }

    @Nested
    @DisplayName("getSpriteScale()")
    class GetSpriteScaleTests {

        @Test
        @DisplayName("should return 1.0 for Zombie (normal size)")
        void shouldReturnZombieScale() {
            assertEquals(1.0, CharacterGraphicsFactory.getSpriteScale("Zombie"), 0.001,
                "Zombie should have 1.0 scale");
        }

        @Test
        @DisplayName("should return 0.8 for Ghost (smaller)")
        void shouldReturnGhostScale() {
            assertEquals(0.8, CharacterGraphicsFactory.getSpriteScale("Ghost"), 0.001,
                "Ghost should have 0.8 scale");
        }

        @Test
        @DisplayName("should return 1.2 for PumpkinBomber (larger)")
        void shouldReturnPumpkinBomberScale() {
            assertEquals(1.2, CharacterGraphicsFactory.getSpriteScale("PumpkinBomber"), 0.001,
                "PumpkinBomber should have 1.2 scale");
        }

        @ParameterizedTest
        @ValueSource(strings = {"Unknown", "Dragon", ""})
        @DisplayName("should return 1.0 for unknown types")
        void shouldReturnDefaultForUnknownTypes(String typeName) {
            assertEquals(1.0, CharacterGraphicsFactory.getSpriteScale(typeName), 0.001,
                "Unknown type should have default 1.0 scale");
        }

        @Test
        @DisplayName("scales should be within reasonable visual bounds")
        void scalesShouldBeReasonable() {
            String[] knownTypes = CharacterRegistrar.getKnownTypes();
            
            for (String type : knownTypes) {
                double scale = CharacterGraphicsFactory.getSpriteScale(type);
                assertTrue(scale >= 0.5 && scale <= 2.0,
                    type + " scale should be between 0.5 and 2.0, was: " + scale);
            }
        }
    }

    @Nested
    @DisplayName("Character Visual Differentiation")
    class VisualDifferentiationTests {

        @ParameterizedTest
        @CsvSource({
            "Zombie, 4, 1.0",
            "Ghost, 6, 0.8",
            "PumpkinBomber, 4, 1.2"
        })
        @DisplayName("each character should have distinct visual properties")
        void charactersShouldHaveDistinctVisuals(String type, int expectedFrames, double expectedScale) {
            int frames = CharacterGraphicsFactory.getAnimationFrameCount(type);
            double scale = CharacterGraphicsFactory.getSpriteScale(type);
            
            assertEquals(expectedFrames, frames, type + " frame count mismatch");
            assertEquals(expectedScale, scale, 0.001, type + " scale mismatch");
        }

        @Test
        @DisplayName("Ghost should be visually smallest (scale < 1.0)")
        void ghostShouldBeSmallest() {
            double ghostScale = CharacterGraphicsFactory.getSpriteScale("Ghost");
            double zombieScale = CharacterGraphicsFactory.getSpriteScale("Zombie");
            double bomberScale = CharacterGraphicsFactory.getSpriteScale("PumpkinBomber");
            
            assertTrue(ghostScale < zombieScale, "Ghost should be smaller than Zombie");
            assertTrue(ghostScale < bomberScale, "Ghost should be smaller than PumpkinBomber");
        }

        @Test
        @DisplayName("PumpkinBomber should be visually largest")
        void pumpkinBomberShouldBeLargest() {
            double ghostScale = CharacterGraphicsFactory.getSpriteScale("Ghost");
            double zombieScale = CharacterGraphicsFactory.getSpriteScale("Zombie");
            double bomberScale = CharacterGraphicsFactory.getSpriteScale("PumpkinBomber");
            
            assertTrue(bomberScale > zombieScale, "PumpkinBomber should be larger than Zombie");
            assertTrue(bomberScale > ghostScale, "PumpkinBomber should be larger than Ghost");
        }

        @Test
        @DisplayName("Ghost should have most animation frames (ethereal movement)")
        void ghostShouldHaveMostFrames() {
            int ghostFrames = CharacterGraphicsFactory.getAnimationFrameCount("Ghost");
            int zombieFrames = CharacterGraphicsFactory.getAnimationFrameCount("Zombie");
            int bomberFrames = CharacterGraphicsFactory.getAnimationFrameCount("PumpkinBomber");
            
            assertTrue(ghostFrames >= zombieFrames, 
                "Ghost should have at least as many frames as Zombie");
            assertTrue(ghostFrames >= bomberFrames, 
                "Ghost should have at least as many frames as PumpkinBomber");
        }
    }

    @Nested
    @DisplayName("Model Consistency with CharacterRegistrar")
    class ModelConsistencyTests {

        @Test
        @DisplayName("all registered types should have graphics properties")
        void allTypesShouldHaveGraphics() {
            String[] knownTypes = CharacterRegistrar.getKnownTypes();
            
            for (String type : knownTypes) {
                // Should have at least 1 frame
                assertTrue(CharacterGraphicsFactory.getAnimationFrameCount(type) >= 1,
                    type + " should have animation frames defined");
                
                // Should have non-zero scale
                assertTrue(CharacterGraphicsFactory.getSpriteScale(type) > 0,
                    type + " should have positive scale");
            }
        }
    }
}


