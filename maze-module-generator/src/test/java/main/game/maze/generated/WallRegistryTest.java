package main.game.maze.generated;

import main.game.maze.walls.WallMaterialBaseType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the generated WallRegistry.
 * Validates that model-driven code generation produces correct wall material registry.
 */
@DisplayName("WallRegistry Tests")
class WallRegistryTest {

    @Nested
    @DisplayName("get() method")
    class GetMethodTests {

        @Test
        @DisplayName("should return DIRT_BASIC wall definition")
        void shouldReturnDirtBasic() {
            WallRegistry.WallDefinition wall = WallRegistry.get("DIRT_BASIC");
            
            assertNotNull(wall, "DIRT_BASIC should be registered");
            assertEquals("DIRT_BASIC", wall.id);
            assertEquals("Dirt", wall.displayName);
            assertEquals(WallMaterialBaseType.DIRT, wall.baseType);
            assertTrue(wall.breakable, "Dirt should be breakable");
            assertEquals(10, wall.hitPoints);
            assertEquals("/main/game/maze/dirtWall.png", wall.baseImage);
        }

        @Test
        @DisplayName("should return WOOD_BASIC wall definition")
        void shouldReturnWoodBasic() {
            WallRegistry.WallDefinition wall = WallRegistry.get("WOOD_BASIC");
            
            assertNotNull(wall, "WOOD_BASIC should be registered");
            assertEquals("WOOD_BASIC", wall.id);
            assertEquals("Wood", wall.displayName);
            assertEquals(WallMaterialBaseType.WOOD, wall.baseType);
            assertTrue(wall.breakable, "Wood should be breakable");
            assertEquals(20, wall.hitPoints);
            assertEquals("/main/game/maze/woodWall.png", wall.baseImage);
        }

        @Test
        @DisplayName("should return STEEL_SOLID wall definition")
        void shouldReturnSteelSolid() {
            WallRegistry.WallDefinition wall = WallRegistry.get("STEEL_SOLID");
            
            assertNotNull(wall, "STEEL_SOLID should be registered");
            assertEquals("STEEL_SOLID", wall.id);
            assertEquals("Steel", wall.displayName);
            assertEquals(WallMaterialBaseType.STEEL, wall.baseType);
            assertFalse(wall.breakable, "Steel should not be breakable");
            assertEquals(0, wall.hitPoints);
            assertEquals("/main/game/maze/steelWall.png", wall.baseImage);
        }

        @ParameterizedTest
        @ValueSource(strings = {"UNKNOWN", "CONCRETE", "GLASS", "", "dirt_basic"})
        @DisplayName("should return null for unknown wall IDs")
        void shouldReturnNullForUnknownIds(String wallId) {
            WallRegistry.WallDefinition wall = WallRegistry.get(wallId);
            assertNull(wall, wallId + " should not be found in registry");
        }

        @Test
        @DisplayName("should be case-sensitive")
        void shouldBeCaseSensitive() {
            assertNull(WallRegistry.get("dirt_basic"), "Should be case-sensitive");
            assertNull(WallRegistry.get("Dirt_Basic"), "Should be case-sensitive");
            assertNotNull(WallRegistry.get("DIRT_BASIC"), "Exact case should match");
        }
    }

    @Nested
    @DisplayName("all() method")
    class AllMethodTests {

        @Test
        @DisplayName("should return all registered wall materials")
        void shouldReturnAllWallMaterials() {
            Map<String, WallRegistry.WallDefinition> allWalls = WallRegistry.all();
            
            assertNotNull(allWalls, "Wall map should not be null");
            assertEquals(3, allWalls.size(), "Should have exactly 3 wall materials");
        }

        @Test
        @DisplayName("should contain all expected wall IDs")
        void shouldContainAllExpectedIds() {
            Map<String, WallRegistry.WallDefinition> allWalls = WallRegistry.all();
            
            assertTrue(allWalls.containsKey("DIRT_BASIC"), "Should contain DIRT_BASIC");
            assertTrue(allWalls.containsKey("WOOD_BASIC"), "Should contain WOOD_BASIC");
            assertTrue(allWalls.containsKey("STEEL_SOLID"), "Should contain STEEL_SOLID");
        }

        @Test
        @DisplayName("should return unmodifiable map")
        void shouldReturnUnmodifiableMap() {
            Map<String, WallRegistry.WallDefinition> allWalls = WallRegistry.all();
            
            assertThrows(UnsupportedOperationException.class, () -> {
                allWalls.put("TEST_WALL", null);
            }, "Map should be unmodifiable");
        }

        @Test
        @DisplayName("map values should match get() method results")
        void mapValuesShouldMatchGetMethod() {
            Map<String, WallRegistry.WallDefinition> allWalls = WallRegistry.all();
            
            for (Map.Entry<String, WallRegistry.WallDefinition> entry : allWalls.entrySet()) {
                WallRegistry.WallDefinition fromMap = entry.getValue();
                WallRegistry.WallDefinition fromGet = WallRegistry.get(entry.getKey());
                
                assertSame(fromMap, fromGet, 
                    "Definition from map should be same instance as from get()");
            }
        }
    }

    @Nested
    @DisplayName("WallDefinition inner class")
    class WallDefinitionTests {

        @Test
        @DisplayName("should store all properties correctly")
        void shouldStoreAllProperties() {
            WallRegistry.WallDefinition wall = new WallRegistry.WallDefinition(
                "TEST_ID",
                "Test Wall",
                WallMaterialBaseType.DIRT,
                true,
                50,
                "/test/image.png"
            );
            
            assertEquals("TEST_ID", wall.id);
            assertEquals("Test Wall", wall.displayName);
            assertEquals(WallMaterialBaseType.DIRT, wall.baseType);
            assertTrue(wall.breakable);
            assertEquals(50, wall.hitPoints);
            assertEquals("/test/image.png", wall.baseImage);
        }

        @Test
        @DisplayName("should handle non-breakable walls with zero hit points")
        void shouldHandleNonBreakableWalls() {
            WallRegistry.WallDefinition steel = WallRegistry.get("STEEL_SOLID");
            
            assertNotNull(steel);
            assertFalse(steel.breakable);
            assertEquals(0, steel.hitPoints, "Non-breakable walls should have 0 hit points");
        }
    }

    @Nested
    @DisplayName("Model-Driven Generation Validation")
    class ModelDrivenValidationTests {

        @Test
        @DisplayName("all registered walls should be retrievable by get()")
        void allWallsShouldBeRetrievable() {
            Map<String, WallRegistry.WallDefinition> allWalls = WallRegistry.all();
            
            for (String wallId : allWalls.keySet()) {
                WallRegistry.WallDefinition wall = WallRegistry.get(wallId);
                assertNotNull(wall, "Wall " + wallId + " should be retrievable via get()");
                assertEquals(wallId, wall.id, "Wall ID should match registry key");
            }
        }

        @Test
        @DisplayName("wall IDs should match their registry keys")
        void wallIdsShouldMatchKeys() {
            Map<String, WallRegistry.WallDefinition> allWalls = WallRegistry.all();
            
            for (Map.Entry<String, WallRegistry.WallDefinition> entry : allWalls.entrySet()) {
                assertEquals(entry.getKey(), entry.getValue().id,
                    "Registry key should match wall definition ID");
            }
        }

        @Test
        @DisplayName("all walls should have valid image paths")
        void allWallsShouldHaveValidImagePaths() {
            Map<String, WallRegistry.WallDefinition> allWalls = WallRegistry.all();
            
            for (WallRegistry.WallDefinition wall : allWalls.values()) {
                assertNotNull(wall.baseImage, "Wall " + wall.id + " should have an image path");
                assertFalse(wall.baseImage.isEmpty(), "Image path should not be empty");
                assertTrue(wall.baseImage.startsWith("/"), 
                    "Image path should be absolute: " + wall.baseImage);
            }
        }

        @Test
        @DisplayName("breakable walls should have positive hit points")
        void breakableWallsShouldHavePositiveHitPoints() {
            Map<String, WallRegistry.WallDefinition> allWalls = WallRegistry.all();
            
            for (WallRegistry.WallDefinition wall : allWalls.values()) {
                if (wall.breakable) {
                    assertTrue(wall.hitPoints > 0, 
                        "Breakable wall " + wall.id + " should have positive hit points");
                }
            }
        }
    }
}


