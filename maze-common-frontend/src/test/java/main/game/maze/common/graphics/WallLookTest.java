package main.game.maze.common.graphics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Wall look: every wall material has its own sprite")
class WallLookTest {

    @Test
    @DisplayName("a material id maps to the look of its base material, whatever the variant suffix")
    void byMaterialId() {
        assertEquals(WallLook.GLASS, WallLook.of("GLASS_BASIC"));
        assertEquals(WallLook.DIRT, WallLook.of("DIRT_BASIC"));
        assertEquals(WallLook.WOOD, WallLook.of("wood_basic"));
        assertEquals(WallLook.STONE, WallLook.of("STONE_REINFORCED"));
        assertEquals(WallLook.STEEL, WallLook.of("STEEL_BASIC"));
        assertEquals(WallLook.STEEL, WallLook.of("STEEL_SOLID"));
    }

    @Test
    @DisplayName("an unknown or missing material has no look: the classic wall is used")
    void unknown() {
        assertNull(WallLook.of("UNOBTAINIUM_BASIC"));
        assertNull(WallLook.of(null));
        assertNull(WallLook.of(""));
    }

    @Test
    @DisplayName("every look has a sprite on the classpath")
    void spritesExist() {
        for (WallLook look : WallLook.values()) {
            assertNotNull(WallLookTest.class.getResource(look.imagePath()), "missing " + look.imagePath());
        }
    }

    @Test
    @DisplayName("a solid wall (no material id) gets the classic sprite instead of failing on an immutable map")
    void solidWallUsesTheClassicSprite() {
        java.util.Map<WallLook, String> sprites = java.util.Map.copyOf(java.util.Map.of(WallLook.STONE, "stone"));
        assertEquals("classic", WallLook.spriteFor(sprites, null, "classic"));
        assertEquals("classic", WallLook.spriteFor(sprites, "UNOBTAINIUM_BASIC", "classic"));
        assertEquals("classic", WallLook.spriteFor(sprites, "GLASS_BASIC", "classic"), "no sprite loaded for glass");
        assertEquals("stone", WallLook.spriteFor(sprites, "STONE_BASIC", "classic"));
    }
}
