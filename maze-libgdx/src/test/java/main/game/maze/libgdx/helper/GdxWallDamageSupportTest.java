package main.game.maze.libgdx.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.function.Consumer;
import main.game.maze.mazeworld.BreakableWall;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.RealMaze;
import main.game.maze.mazeworld.generators.WallSegment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("GdxWallDamageSupport")
class GdxWallDamageSupportTest {

    private static final int WORLD_W = 800;
    private static final int WORLD_H = 800;

    private static MazeArena stubArena() {
        return new MazeArena() {
            @Override public List<WallSegment> walls() { return List.of(); }
            @Override public float widthPx()  { return WORLD_W; }
            @Override public float heightPx() { return WORLD_H; }
            @Override public float startX()   { return 24f; }
            @Override public float startY()   { return 24f; }
            @Override public float goalX()    { return WORLD_W - 24f; }
            @Override public float goalY()    { return WORLD_H - 24f; }
        };
    }

    @Test
    @DisplayName("wallHitCallback returns null for stub (non-RealMaze) arena")
    void wallHitCallback_stubArena_returnsNull() {
        Consumer<float[]> cb = GdxWallDamageSupport.wallHitCallback(stubArena(), 10);
        assertNull(cb, "Stub arena has no GameMazeWorld — callback must be null");
    }

    @Test
    @DisplayName("wallHitCallback returns non-null for RealMaze arena")
    void wallHitCallback_realMaze_returnsNonNull() {
        RealMaze realMaze = new RealMaze(new GameMazeWorld(), WORLD_W, WORLD_H);
        Consumer<float[]> cb = GdxWallDamageSupport.wallHitCallback(realMaze, 5);
        assertNotNull(cb, "RealMaze arena must produce a non-null wall-hit callback");
    }

    @Test
    @DisplayName("callback at breakable wall mid-point reduces HP")
    void wallHitCallback_atBreakableWallMidpoint_reducesHp() {
        GameMazeWorld world = new GameMazeWorld();
        RealMaze realMaze = new RealMaze(world, WORLD_W, WORLD_H);

        List<BreakableWall> bwalls = world.getBreakableWalls();
        assertTrue(!bwalls.isEmpty(), "Expected at least one breakable wall");

        BreakableWall bw = bwalls.get(0);
        int hpBefore = bw.getRemainingHp();

        Vector2D wallVec = bw.geometry;
        double midX = (wallVec.getStart().getX() + wallVec.getEnd().getX()) / 2.0;
        double midY = (wallVec.getStart().getY() + wallVec.getEnd().getY()) / 2.0;

        Consumer<float[]> cb = GdxWallDamageSupport.wallHitCallback(realMaze, hpBefore);
        assertNotNull(cb);
        cb.accept(new float[]{(float) midX, (float) midY});

        assertTrue(bw.getRemainingHp() < hpBefore,
                "Breakable wall HP must decrease after callback fires at its midpoint");
    }

    @Test
    @DisplayName("callback at indestructible wall position does not change any breakable HP")
    void wallHitCallback_atIndestructibleWall_noHpChange() {
        GameMazeWorld world = new GameMazeWorld();
        RealMaze realMaze = new RealMaze(world, WORLD_W, WORLD_H);

        Vector2D nonBreakable = world.getMazeVectors().stream()
                .filter(v -> world.findBreakableWall(v) == null)
                .findFirst().orElse(null);

        org.junit.jupiter.api.Assumptions.assumeTrue(nonBreakable != null,
                "All walls are breakable — indestructible-wall test skipped");

        double midX = (nonBreakable.getStart().getX() + nonBreakable.getEnd().getX()) / 2.0;
        double midY = (nonBreakable.getStart().getY() + nonBreakable.getEnd().getY()) / 2.0;

        int totalHpBefore = world.getBreakableWalls().stream()
                .mapToInt(BreakableWall::getRemainingHp).sum();

        Consumer<float[]> cb = GdxWallDamageSupport.wallHitCallback(realMaze, 10);
        assertNotNull(cb);
        cb.accept(new float[]{(float) midX, (float) midY});

        int totalHpAfter = world.getBreakableWalls().stream()
                .mapToInt(BreakableWall::getRemainingHp).sum();
        assertEquals(totalHpBefore, totalHpAfter,
                "Hit at a non-breakable wall must not change any breakable wall HP");
    }

    @Test
    @DisplayName("callback at position far from all walls does nothing")
    void wallHitCallback_atEmptyPosition_noHpChange() {
        GameMazeWorld world = new GameMazeWorld();
        RealMaze realMaze = new RealMaze(world, WORLD_W, WORLD_H);

        int totalHpBefore = world.getBreakableWalls().stream()
                .mapToInt(BreakableWall::getRemainingHp).sum();

        Consumer<float[]> cb = GdxWallDamageSupport.wallHitCallback(realMaze, 99);
        assertNotNull(cb);
        // Open centre of the arena — no wall should be nearby
        cb.accept(new float[]{WORLD_W / 2f, WORLD_H / 2f});

        int totalHpAfter = world.getBreakableWalls().stream()
                .mapToInt(BreakableWall::getRemainingHp).sum();
        assertEquals(totalHpBefore, totalHpAfter,
                "Callback at an open position must not change any wall HP");
    }

    @Test
    @DisplayName("applyProjectileDamageToWall reduces HP of the matching breakable wall")
    void applyProjectileDamageToWall_reducesHp() {
        GameMazeWorld world = new GameMazeWorld();

        List<BreakableWall> bwalls = world.getBreakableWalls();
        assertTrue(!bwalls.isEmpty(), "Expected at least one breakable wall");

        BreakableWall bw = bwalls.get(0);
        int hpBefore = bw.getRemainingHp();

        GdxWallDamageSupport.applyProjectileDamageToWall(world, bw.geometry, 3);

        assertTrue(bw.getRemainingHp() < hpBefore,
                "applyProjectileDamageToWall must reduce the matching wall's HP");
    }

    @Test
    @DisplayName("applyProjectileDamageToWall on non-breakable wall does not throw")
    void applyProjectileDamageToWall_nonBreakableWall_doesNotThrow() {
        GameMazeWorld world = new GameMazeWorld();

        Vector2D nonBreakable = world.getMazeVectors().stream()
                .filter(v -> world.findBreakableWall(v) == null)
                .findFirst().orElse(null);

        org.junit.jupiter.api.Assumptions.assumeTrue(nonBreakable != null,
                "All walls are breakable — this test is skipped");

        org.junit.jupiter.api.Assertions.assertDoesNotThrow(
                () -> GdxWallDamageSupport.applyProjectileDamageToWall(world, nonBreakable, 10),
                "applyProjectileDamageToWall on non-breakable wall must not throw");
    }
}
