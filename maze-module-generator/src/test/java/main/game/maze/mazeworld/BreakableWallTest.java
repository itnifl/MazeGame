package main.game.maze.mazeworld;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BreakableWall")
class BreakableWallTest {

    private static Vector2D wall() {
        return new Vector2D(0, 0, 100, 0);
    }

    private static WallMaterialSpec dirtSpec(int hp) {
        return new WallMaterialSpec("DIRT_BASIC", "Dirt", hp);
    }

    @Test
    @DisplayName("constructor rejects null material")
    void constructorRejectsNullMaterial() {
        assertThrows(NullPointerException.class, () -> new BreakableWall(wall(), (WallMaterialSpec) null));
    }

    @Test
    @DisplayName("WallMaterialSpec rejects zero or negative hitPoints")
    void materialSpecRejectsNonPositiveHp() {
        assertThrows(IllegalArgumentException.class, () -> new WallMaterialSpec("ID", "Name", 0));
        assertThrows(IllegalArgumentException.class, () -> new WallMaterialSpec("ID", "Name", -5));
    }

    @Test
    @DisplayName("constructor rejects null geometry")
    void constructorRejectsNullGeometry() {
        assertThrows(NullPointerException.class,
                () -> new BreakableWall(null, dirtSpec(10)));
    }

    @Test
    @DisplayName("starts with full hit points from material and is not destroyed")
    void initialState() {
        BreakableWall bw = new BreakableWall(wall(), dirtSpec(10));
        assertEquals(10, bw.getRemainingHp());
        assertFalse(bw.isDestroyed());
    }

    @Test
    @DisplayName("material field is accessible and matches spec")
    void materialFieldIsAccessible() {
        WallMaterialSpec spec = new WallMaterialSpec("GLASS_BASIC", "Glass", 5);
        BreakableWall bw = new BreakableWall(wall(), spec);
        assertSame(spec, bw.material);
        assertEquals("GLASS_BASIC", bw.material.id());
        assertEquals(5, bw.material.hitPoints());
    }

    @Test
    @DisplayName("HP tiers: Glass=5, Dirt=10, Wood=20, Stone=40")
    void materialHpTiers() {
        assertEquals(5,  new BreakableWall(wall(), new WallMaterialSpec("GLASS_BASIC", "Glass",  5)).getRemainingHp());
        assertEquals(10, new BreakableWall(wall(), new WallMaterialSpec("DIRT_BASIC",  "Dirt",  10)).getRemainingHp());
        assertEquals(20, new BreakableWall(wall(), new WallMaterialSpec("WOOD_BASIC",  "Wood",  20)).getRemainingHp());
        assertEquals(40, new BreakableWall(wall(), new WallMaterialSpec("STONE_BASIC", "Stone", 40)).getRemainingHp());
    }

    @Nested
    @DisplayName("applyDamage")
    class ApplyDamage {

        @Test
        @DisplayName("reduces hit points by the damage amount")
        void reducesByDamage() {
            BreakableWall bw = new BreakableWall(wall(), dirtSpec(10));
            int remaining = bw.applyDamage(4);
            assertEquals(6, remaining);
            assertEquals(6, bw.getRemainingHp());
            assertFalse(bw.isDestroyed());
        }

        @Test
        @DisplayName("wall is destroyed when damage equals hit points")
        void destroyedOnExactDamage() {
            BreakableWall bw = new BreakableWall(wall(), dirtSpec(10));
            int remaining = bw.applyDamage(10);
            assertEquals(0, remaining);
            assertTrue(bw.isDestroyed());
        }

        @Test
        @DisplayName("wall is destroyed when damage exceeds hit points")
        void destroyedOnOverkill() {
            BreakableWall bw = new BreakableWall(wall(), new WallMaterialSpec("GLASS_BASIC", "Glass", 5));
            int remaining = bw.applyDamage(999);
            assertEquals(0, remaining);
            assertTrue(bw.isDestroyed());
        }

        @Test
        @DisplayName("hit points never go below zero")
        void hpFloorIsZero() {
            BreakableWall bw = new BreakableWall(wall(), new WallMaterialSpec("GLASS_BASIC", "Glass", 5));
            bw.applyDamage(5);
            bw.applyDamage(100);
            assertEquals(0, bw.getRemainingHp());
        }

        @Test
        @DisplayName("rejects negative damage")
        void rejectsNegativeDamage() {
            BreakableWall bw = new BreakableWall(wall(), dirtSpec(10));
            assertThrows(IllegalArgumentException.class, () -> bw.applyDamage(-1));
        }

        @Test
        @DisplayName("zero damage leaves hit points unchanged")
        void zeroDamageDoesNotChangeHp() {
            BreakableWall bw = new BreakableWall(wall(), dirtSpec(10));
            int remaining = bw.applyDamage(0);
            assertEquals(10, remaining);
            assertEquals(10, bw.getRemainingHp());
            assertFalse(bw.isDestroyed());
        }

        @Test
        @DisplayName("multiple partial hits accumulate")
        void cumulativeDamage() {
            BreakableWall bw = new BreakableWall(wall(), new WallMaterialSpec("WOOD_BASIC", "Wood", 20));
            bw.applyDamage(5);
            bw.applyDamage(5);
            assertFalse(bw.isDestroyed());
            bw.applyDamage(10);
            assertTrue(bw.isDestroyed());
        }
    }

    @Test
    @DisplayName("geometry field references the supplied Vector2D")
    void geometryIdentity() {
        Vector2D v = wall();
        BreakableWall bw = new BreakableWall(v, dirtSpec(10));
        assertSame(v, bw.geometry);
    }
}
