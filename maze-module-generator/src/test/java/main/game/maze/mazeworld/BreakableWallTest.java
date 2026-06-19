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

    @Test
    @DisplayName("constructor rejects zero or negative hitPoints")
    void constructorRejectsNonPositiveHp() {
        assertThrows(IllegalArgumentException.class, () -> new BreakableWall(wall(), 0));
        assertThrows(IllegalArgumentException.class, () -> new BreakableWall(wall(), -5));
    }

    @Test
    @DisplayName("starts with full hit points and is not destroyed")
    void initialState() {
        BreakableWall bw = new BreakableWall(wall(), 10);
        assertEquals(10, bw.getRemainingHp());
        assertFalse(bw.isDestroyed());
    }

    @Nested
    @DisplayName("applyDamage")
    class ApplyDamage {

        @Test
        @DisplayName("reduces hit points by the damage amount")
        void reducesByDamage() {
            BreakableWall bw = new BreakableWall(wall(), 10);
            int remaining = bw.applyDamage(4);
            assertEquals(6, remaining);
            assertEquals(6, bw.getRemainingHp());
            assertFalse(bw.isDestroyed());
        }

        @Test
        @DisplayName("wall is destroyed when damage equals hit points")
        void destroyedOnExactDamage() {
            BreakableWall bw = new BreakableWall(wall(), 10);
            int remaining = bw.applyDamage(10);
            assertEquals(0, remaining);
            assertTrue(bw.isDestroyed());
        }

        @Test
        @DisplayName("wall is destroyed when damage exceeds hit points")
        void destroyedOnOverkill() {
            BreakableWall bw = new BreakableWall(wall(), 5);
            int remaining = bw.applyDamage(999);
            assertEquals(0, remaining);
            assertTrue(bw.isDestroyed());
        }

        @Test
        @DisplayName("hit points never go below zero")
        void hpFloorIsZero() {
            BreakableWall bw = new BreakableWall(wall(), 5);
            bw.applyDamage(5);
            bw.applyDamage(100);
            assertEquals(0, bw.getRemainingHp());
        }

        @Test
        @DisplayName("multiple partial hits accumulate")
        void cumulativeDamage() {
            BreakableWall bw = new BreakableWall(wall(), 20);
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
        BreakableWall bw = new BreakableWall(v, 10);
        assertSame(v, bw.geometry);
    }
}
