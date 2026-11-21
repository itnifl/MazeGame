package main.game.maze.mazeworld.flame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import main.game.maze.mazeworld.BreakableWall;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.WallMaterialSpec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PulseBeamEngine: a beam that breaks through the walls that are too weak to stop it")
class PulseBeamEngineTest {

    private static final double RANGE = 1200;
    private static final double HALF_WIDTH = 14;

    private static FlameTarget enemy(double cx, double cy, int hp, AtomicInteger remaining) {
        remaining.set(hp);
        return new FlameTarget() {
            @Override public double centerX() { return cx; }
            @Override public double centerY() { return cy; }
            @Override public int hitPoints() { return remaining.get(); }
            @Override public void applyDamage(int amount) { remaining.addAndGet(-amount); }
            @Override public boolean isPassThrough() { return false; }
        };
    }

    private static GameMazeWorld worldWith(List<Vector2D> walls, String materialId, int hp) {
        GameMazeWorld world = new GameMazeWorld(() -> new ArrayList<>(List.of(new Vector2D(0, 5000, 10, 5000))));   // a far-off wall out of the beam
        WallMaterialSpec material = new WallMaterialSpec(materialId, materialId, hp);
        for (Vector2D wall : walls) {
            world.addBuiltWall(wall, material);   // every wall breakable, none left to chance
        }
        return world;
    }

    private static List<Vector2D> threeWalls() {
        return List.of(new Vector2D(100, 0, 100, 100), new Vector2D(200, 0, 200, 100), new Vector2D(300, 0, 300, 100));
    }

    @Test
    @DisplayName("a strong beam breaks through several walls in a row and hits the enemy behind them with what is left")
    void piercesSeveralWalls() {
        GameMazeWorld world = worldWith(threeWalls(), "WOOD_BASIC", 20);
        AtomicInteger hp = new AtomicInteger();
        FlameTarget behind = enemy(500, 50, 5000, hp);

        LaserBeamEngine.Shot shot = PulseBeamEngine.fire(List.of(behind), world, world.getMazeVectors(),
                0, 50, 1, 0, 1000, RANGE, HALF_WIDTH);

        assertEquals(LaserBeamEngine.Outcome.ENEMY, shot.outcome());
        assertEquals(1, world.getMazeVectors().size(), "all three walls are gone, only the far-off one is left");
        assertEquals(5000 - (1000 - 60), hp.get(), "the enemy takes what is left of the power: 1000 - 3 x 20");
        assertTrue(shot.reach() > 490 && shot.reach() <= 510, "reach " + shot.reach());
    }

    @Test
    @DisplayName("a beam too weak to break the wall stops at it and only damages it")
    void stopsAtAStrongWall() {
        GameMazeWorld world = worldWith(threeWalls(), "WOOD_BASIC", 20);
        BreakableWall first = world.findBreakableWall(world.getMazeVectors().get(1));   // [0] is the far-off wall
        assertNotNull(first);

        LaserBeamEngine.Shot shot = PulseBeamEngine.fire(List.of(), world, world.getMazeVectors(),
                0, 50, 1, 0, 15, RANGE, HALF_WIDTH);

        assertEquals(LaserBeamEngine.Outcome.WALL_DAMAGED, shot.outcome());
        assertEquals(5, first.getRemainingHp());
        assertEquals(4, world.getMazeVectors().size(), "nothing broke");
        assertEquals(100, shot.reach(), 1e-9);
    }

    @Test
    @DisplayName("the walls use up the power: it breaks two and is stopped by the third")
    void powerRunsOut() {
        GameMazeWorld world = worldWith(threeWalls(), "WOOD_BASIC", 20);

        PulseBeamEngine.fire(List.of(), world, world.getMazeVectors(), 0, 50, 1, 0, 50, RANGE, HALF_WIDTH);

        assertEquals(2, world.getMazeVectors().size(), "the third wall is left (and the far-off one)");
        assertEquals(10, world.findBreakableWall(world.getMazeVectors().get(1)).getRemainingHp(), "damaged by the last 10 power");
    }

    @Test
    @DisplayName("steel stops the beam however strong it is")
    void steelStopsIt() {
        GameMazeWorld world = worldWith(threeWalls(), "STEEL_SOLID", 30);
        AtomicInteger hp = new AtomicInteger();
        FlameTarget behind = enemy(500, 50, 100, hp);

        LaserBeamEngine.Shot shot = PulseBeamEngine.fire(List.of(behind), world, world.getMazeVectors(),
                0, 50, 1, 0, 100_000, RANGE, HALF_WIDTH);

        assertEquals(LaserBeamEngine.Outcome.BLOCKED, shot.outcome());
        assertEquals(100, hp.get());
        assertEquals(4, world.getMazeVectors().size());
    }

    @Test
    @DisplayName("without walls it is the laser: the first enemy takes the whole power")
    void noWalls() {
        AtomicInteger hp = new AtomicInteger();
        FlameTarget target = enemy(400, 50, 5000, hp);
        LaserBeamEngine.Shot shot = PulseBeamEngine.fire(List.of(target), null, List.of(), 0, 50, 1, 0, 3000, RANGE, HALF_WIDTH);
        assertEquals(LaserBeamEngine.Outcome.ENEMY, shot.outcome());
        assertEquals(2000, hp.get());
        assertEquals(400, shot.reach(), 1e-9);
    }
}
