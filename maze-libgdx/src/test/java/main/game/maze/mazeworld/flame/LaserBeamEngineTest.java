package main.game.maze.mazeworld.flame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import main.game.maze.mazeworld.BreakableWall;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.WallMaterialSpec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("LaserBeamEngine")
class LaserBeamEngineTest {

    private static final double RANGE = 600;
    private static final double HALF_WIDTH = 14;

    private static FlameTarget enemy(double cx, double cy, int hp) {
        AtomicInteger remaining = new AtomicInteger(hp);
        return new FlameTarget() {
            @Override public double centerX() { return cx; }
            @Override public double centerY() { return cy; }
            @Override public int hitPoints() { return remaining.get(); }
            @Override public void applyDamage(int amount) { remaining.addAndGet(-amount); }
            @Override public boolean isPassThrough() { return false; }
        };
    }

    private static GameMazeWorld worldWith(Vector2D wall, String materialId) {
        GameMazeWorld world = new GameMazeWorld(() -> List.of(wall));
        world.assignBreakableWalls(0L, materialId == null
                ? List.of()
                : List.of(new WallMaterialSpec(materialId, materialId, 30)));
        return world;
    }

    @Test
    @DisplayName("hits the nearest enemy in line, and only that one")
    void hitsNearestEnemyOnly() {
        FlameTarget near = enemy(200, 50, 100);
        FlameTarget far = enemy(400, 50, 100);

        LaserBeamEngine.Shot shot = LaserBeamEngine.fire(List.of(far, near), null, List.of(),
                0, 50, 1, 0, 40, RANGE, HALF_WIDTH);

        assertEquals(LaserBeamEngine.Outcome.ENEMY, shot.outcome());
        assertEquals(60, near.hitPoints());
        assertEquals(100, far.hitPoints(), "the beam stops at the first enemy");
        assertEquals(200, shot.reach(), 1e-9);
    }

    @Test
    @DisplayName("damages a breakable wall in the way instead of the enemy behind it")
    void damagesBreakableWall() {
        Vector2D wall = new Vector2D(120, 0, 120, 100);
        GameMazeWorld world = worldWith(wall, "WOOD_BASIC");
        BreakableWall breakable = world.findBreakableWall(wall);
        assertNotNull(breakable);
        FlameTarget behind = enemy(200, 50, 100);

        LaserBeamEngine.Shot shot = LaserBeamEngine.fire(List.of(behind), world, world.getMazeVectors(),
                0, 50, 1, 0, 20, RANGE, HALF_WIDTH);

        assertEquals(LaserBeamEngine.Outcome.WALL_DAMAGED, shot.outcome());
        assertEquals(10, breakable.getRemainingHp());
        assertEquals(100, behind.hitPoints());
        assertEquals(120, shot.reach(), 1e-9);
    }

    @Test
    @DisplayName("cannot harm steel walls: the beam stops and the wall is untouched")
    void steelIsImmune() {
        Vector2D wall = new Vector2D(120, 0, 120, 100);
        GameMazeWorld world = worldWith(wall, "STEEL_SOLID");
        BreakableWall steel = world.findBreakableWall(wall);
        assertNotNull(steel);

        LaserBeamEngine.Shot shot = LaserBeamEngine.fire(List.of(enemy(200, 50, 100)), world,
                world.getMazeVectors(), 0, 50, 1, 0, 1_000, RANGE, HALF_WIDTH);

        assertEquals(LaserBeamEngine.Outcome.BLOCKED, shot.outcome());
        assertEquals(30, steel.getRemainingHp(), "steel takes no laser damage");
        assertTrue(world.getMazeVectors().contains(wall));
    }

    @Test
    @DisplayName("solid walls block; nothing in range means the beam runs to full range")
    void blockedOrMiss() {
        Vector2D wall = new Vector2D(120, 0, 120, 100);
        LaserBeamEngine.Shot blocked = LaserBeamEngine.fire(List.of(enemy(200, 50, 100)), worldWith(wall, null),
                List.of(wall), 0, 50, 1, 0, 40, RANGE, HALF_WIDTH);
        assertEquals(LaserBeamEngine.Outcome.BLOCKED, blocked.outcome());
        assertEquals(120, blocked.reach(), 1e-9);

        LaserBeamEngine.Shot miss = LaserBeamEngine.fire(List.of(enemy(50, 300, 100)), null, List.of(),
                0, 50, 1, 0, 40, RANGE, HALF_WIDTH);
        assertEquals(LaserBeamEngine.Outcome.NOTHING, miss.outcome());
        assertEquals(RANGE, miss.reach(), 1e-9);
    }

    @Test
    @DisplayName("works in every direction; dead and pass-through targets are ignored")
    void directionsAndIgnoredTargets() {
        FlameTarget north = enemy(0, -150, 100);
        LaserBeamEngine.Shot up = LaserBeamEngine.fire(List.of(north), null, List.of(), 0, 0, 0, -1, 40, RANGE, HALF_WIDTH);
        assertEquals(LaserBeamEngine.Outcome.ENEMY, up.outcome());
        assertEquals(60, north.hitPoints());

        FlameTarget dead = enemy(100, 0, 0);
        LaserBeamEngine.Shot past = LaserBeamEngine.fire(List.of(dead), null, List.of(), 0, 0, 1, 0, 40, RANGE, HALF_WIDTH);
        assertEquals(LaserBeamEngine.Outcome.NOTHING, past.outcome());
        assertFalse(past.reach() < RANGE);
    }

    @Test
    void steelIsDetectedFromTheModelMaterialId() {
        assertTrue(new WallMaterialSpec("STEEL_SOLID", "Steel", 80).isSteel());
        assertTrue(new WallMaterialSpec("steel_basic", "Steel", 80).isSteel());
        assertFalse(new WallMaterialSpec("STONE_BASIC", "Stone", 40).isSteel());
    }
}
