package main.game.maze.mazeworld.flame;

import main.game.maze.mazeworld.BreakableWall;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.WallMaterialSpec;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class DirectionalFlameEngineTest {

    // -----------------------------------------------------------------------
    // Test doubles
    // -----------------------------------------------------------------------

    private static FlameTarget blocker(double cx, double cy, int hp) {
        AtomicInteger remaining = new AtomicInteger(hp);
        return new FlameTarget() {
            @Override public double centerX()        { return cx; }
            @Override public double centerY()        { return cy; }
            @Override public int    hitPoints()      { return remaining.get(); }
            @Override public void   applyDamage(int amount) { remaining.addAndGet(-amount); }
            @Override public boolean isPassThrough() { return false; }
        };
    }

    private static FlameTarget passThrough(double cx, double cy, AtomicInteger received) {
        return new FlameTarget() {
            @Override public double centerX()        { return cx; }
            @Override public double centerY()        { return cy; }
            @Override public int    hitPoints()      { return Integer.MAX_VALUE; }
            @Override public void   applyDamage(int amount) { received.addAndGet(amount); }
            @Override public boolean isPassThrough() { return true; }
        };
    }

    // -----------------------------------------------------------------------
    // projectedDistanceOnRay (package-private)
    // -----------------------------------------------------------------------

    @Test
    void projectedDistance_east_returnsAbsoluteDistance() {
        Double dist = DirectionalFlameEngine.projectedDistanceOnRay(0, 50, 200, 50, 1, 0, 400, 120);
        assertNotNull(dist);
        assertEquals(200.0, dist, 0.001);
    }

    @Test
    void projectedDistance_targetBehindOrigin_east_returnsNull() {
        Double dist = DirectionalFlameEngine.projectedDistanceOnRay(300, 50, 100, 50, 1, 0, 400, 120);
        assertNull(dist, "Target behind the origin must be excluded");
    }

    @Test
    void projectedDistance_targetOutsideCorridorPerpendicular_returnsNull() {
        // Target's Y deviates by 200 px which exceeds corridorHalfWidth=120
        Double dist = DirectionalFlameEngine.projectedDistanceOnRay(0, 50, 200, 280, 1, 0, 400, 120);
        assertNull(dist, "Target outside the perpendicular corridor bounds must be excluded");
    }

    @Test
    void projectedDistance_targetBeyondMaxRange_returnsNull() {
        Double dist = DirectionalFlameEngine.projectedDistanceOnRay(0, 50, 500, 50, 1, 0, 400, 120);
        assertNull(dist, "Target beyond maxRange must be excluded");
    }

    @Test
    void projectedDistance_north_inCorridor() {
        // dirY=-1 means north; target above origin (smaller Y in screen coords or larger in world)
        Double dist = DirectionalFlameEngine.projectedDistanceOnRay(50, 300, 50, 100, 0, -1, 400, 120);
        assertNotNull(dist);
        assertEquals(200.0, dist, 0.001);
    }

    @Test
    void projectedDistance_south_targetJustWithinCorridor() {
        // Target at (x=50+119, y=200) — perp offset 119 < 120
        Double dist = DirectionalFlameEngine.projectedDistanceOnRay(50, 100, 169, 200, 0, 1, 400, 120);
        assertNotNull(dist, "Target just within corridor half-width must be included");
    }

    // -----------------------------------------------------------------------
    // findNextWallHit (package-private)
    // -----------------------------------------------------------------------

    @Test
    void findNextWallHit_noWalls_returnsNull() {
        assertNull(DirectionalFlameEngine.findNextWallHit(0, 50, 1, 0, List.of(), 0, 400, 120));
    }

    @Test
    void findNextWallHit_nullWalls_returnsNull() {
        assertNull(DirectionalFlameEngine.findNextWallHit(0, 50, 1, 0, null, 0, 400, 120));
    }

    @Test
    void findNextWallHit_verticalWallInPath_east_returnsCorrectDistance() {
        Vector2D wall = new Vector2D(150, 0, 150, 100);
        var hit = DirectionalFlameEngine.findNextWallHit(0, 50, 1, 0, List.of(wall), 0, 400, 120);
        assertNotNull(hit);
        assertEquals(150.0, hit.distance(), 0.001);
    }

    @Test
    void findNextWallHit_verticalWallOutsideFlameCorridorCompletely_notHit() {
        // Wall at x=150 spans y=[200..300]; even with corridorHalfWidth=120 the corridor
        // reaches only to originY+120 = 50+120 = 170, which is below minY=200.
        Vector2D wall = new Vector2D(150, 200, 150, 300);
        var hit = DirectionalFlameEngine.findNextWallHit(0, 50, 1, 0, List.of(wall), 0, 400, 120);
        assertNull(hit, "Vertical wall whose Y span does not overlap the flame corridor must not be hit");
    }

    @Test
    void findNextWallHit_wallOutsideOriginSpanButInsideCorridor_isFound() {
        // Wall spans y=[60..150]; originY=50 is outside that span (50 < 60) but the corridor
        // extends to 50+120=170 which overlaps [60..150].  Must be found.
        Vector2D wall = new Vector2D(150, 60, 150, 150);
        var hit = DirectionalFlameEngine.findNextWallHit(0, 50, 1, 0, List.of(wall), 0, 400, 120);
        assertNotNull(hit, "Wall overlapping the flame corridor must be detected even when originY is outside the exact span");
        assertEquals(150.0, hit.distance(), 0.001);
    }

    @Test
    void findNextWallHit_wallBehindOrigin_notReturned() {
        Vector2D wall = new Vector2D(-100, 0, -100, 100);
        var hit = DirectionalFlameEngine.findNextWallHit(0, 50, 1, 0, List.of(wall), 0, 400, 120);
        assertNull(hit, "Wall behind the origin in the travel direction must not be hit");
    }

    @Test
    void findNextWallHit_twoWalls_nearestReturned() {
        Vector2D near = new Vector2D(100, 0, 100, 100);
        Vector2D far  = new Vector2D(250, 0, 250, 100);
        var hit = DirectionalFlameEngine.findNextWallHit(0, 50, 1, 0, List.of(far, near), 0, 400, 120);
        assertNotNull(hit);
        assertEquals(100.0, hit.distance(), 0.001, "Nearest wall must be returned");
    }

    @Test
    void findNextWallHit_horizontalWall_south() {
        Vector2D wall = new Vector2D(0, 200, 200, 200);
        var hit = DirectionalFlameEngine.findNextWallHit(100, 50, 0, 1, List.of(wall), 0, 400, 120);
        assertNotNull(hit);
        assertEquals(150.0, hit.distance(), 0.001);
    }

    @Test
    void findNextWallHit_wallPastSearchStart_notReturned() {
        // searchStart=200, wall at x=100 is before that threshold
        Vector2D wall = new Vector2D(100, 0, 100, 100);
        var hit = DirectionalFlameEngine.findNextWallHit(0, 50, 1, 0, List.of(wall), 200, 400, 120);
        assertNull(hit, "Wall at or before searchStart must be excluded");
    }

    // -----------------------------------------------------------------------
    // flameVisualRange
    // -----------------------------------------------------------------------

    @Test
    void flameVisualRange_noWalls_returnsMaxRange() {
        double range = DirectionalFlameEngine.flameVisualRange(0, 50, 1, 0, List.of(), 300, 120);
        assertEquals(300.0, range, 0.001);
    }

    @Test
    void flameVisualRange_wallAtHalf_returnsWallDistance() {
        Vector2D wall = new Vector2D(150, 0, 150, 100);
        double range = DirectionalFlameEngine.flameVisualRange(0, 50, 1, 0, List.of(wall), 300, 120);
        assertEquals(150.0, range, 0.001);
    }

    // -----------------------------------------------------------------------
    // applyDirectionalFlame — budget/target interaction
    // -----------------------------------------------------------------------

    @Test
    void applyDirectionalFlame_zeroBudget_noTargetsDamaged() {
        FlameTarget enemy = blocker(100, 50, 30);
        int applied = DirectionalFlameEngine.applyDirectionalFlame(
                List.of(enemy), null, List.of(), 0, 50, 1, 0, 0, 300, 120);
        assertEquals(0, applied);
        assertEquals(30, enemy.hitPoints());
    }

    @Test
    void applyDirectionalFlame_singleEnemyInCorridor_killed() {
        FlameTarget enemy = blocker(200, 50, 30);
        int applied = DirectionalFlameEngine.applyDirectionalFlame(
                List.of(enemy), null, List.of(), 0, 50, 1, 0, 100, 300, 120);
        assertEquals(30, applied);
        assertEquals(0, enemy.hitPoints());
    }

    @Test
    void applyDirectionalFlame_enemyOutsideCorridor_ignored() {
        FlameTarget enemy = blocker(200, 300, 30); // Y=300, origin Y=50, offset=250>120
        int applied = DirectionalFlameEngine.applyDirectionalFlame(
                List.of(enemy), null, List.of(), 0, 50, 1, 0, 100, 300, 120);
        assertEquals(0, applied);
        assertEquals(30, enemy.hitPoints());
    }

    @Test
    void applyDirectionalFlame_enemySurvives_blocksFlameForEnemyBehind() {
        FlameTarget tough  = blocker(100, 50, 200); // budget=50 < 200 HP
        FlameTarget behind = blocker(200, 50, 30);
        int applied = DirectionalFlameEngine.applyDirectionalFlame(
                List.of(tough, behind), null, List.of(), 0, 50, 1, 0, 50, 300, 120);
        assertEquals(50, applied);
        assertEquals(150, tough.hitPoints(), "Tough enemy retains HP above applied damage");
        assertEquals(30, behind.hitPoints(), "Enemy behind a surviving blocker must not be touched");
    }

    @Test
    void applyDirectionalFlame_passThroughPlayer_receivesRemainingBudget() {
        AtomicInteger received = new AtomicInteger(0);
        FlameTarget player = passThrough(100, 50, received);
        DirectionalFlameEngine.applyDirectionalFlame(
                List.of(player), null, List.of(), 0, 50, 1, 0, 75, 300, 120);
        assertEquals(75, received.get(), "Pass-through player must receive the full remaining budget");
    }

    @Test
    void applyDirectionalFlame_playerDoesNotBlockEnemyBeyond() {
        AtomicInteger playerReceived = new AtomicInteger(0);
        FlameTarget player = passThrough(100, 50, playerReceived);
        FlameTarget enemy  = blocker(200, 50, 20);
        DirectionalFlameEngine.applyDirectionalFlame(
                List.of(player, enemy), null, List.of(), 0, 50, 1, 0, 100, 300, 120);
        assertTrue(playerReceived.get() > 0, "Player must receive damage");
        assertEquals(0, enemy.hitPoints(), "Enemy behind pass-through player must also be hit");
    }

    @Test
    void applyDirectionalFlame_indestructibleWall_stopsFlame() {
        Vector2D wallVec = new Vector2D(120, 0, 120, 100);
        GameMazeWorld world = new GameMazeWorld(() -> List.of(wallVec));
        world.assignBreakableWalls(0L, List.of()); // all walls indestructible

        FlameTarget enemy = blocker(200, 50, 30);
        int applied = DirectionalFlameEngine.applyDirectionalFlame(
                List.of(enemy), world, List.of(wallVec), 0, 50, 1, 0, 100, 300, 120);
        assertEquals(0, applied, "Indestructible wall must stop flame before any enemy");
        assertEquals(30, enemy.hitPoints());
    }

    @Test
    void applyDirectionalFlame_breakableWallDestroyed_flameReachesEnemy() {
        Vector2D wallVec = new Vector2D(120, 0, 120, 100);
        GameMazeWorld world = new GameMazeWorld(() -> List.of(wallVec));
        world.assignBreakableWalls(0L, List.of(new WallMaterialSpec("WOOD", "Wood", 10)));

        FlameTarget enemy = blocker(200, 50, 20);
        int applied = DirectionalFlameEngine.applyDirectionalFlame(
                List.of(enemy), world, world.getMazeVectors(), 0, 50, 1, 0, 100, 300, 120);

        assertTrue(applied >= 30, "Budget must cover wall HP (10) + enemy HP (20)");
        assertEquals(0, enemy.hitPoints(), "Enemy beyond a destroyed wall must be killed");
    }

    @Test
    void applyDirectionalFlame_budgetExhaustedByEnemy_doesNotOvershoot() {
        FlameTarget enemy = blocker(100, 50, 200); // 200 HP
        int applied = DirectionalFlameEngine.applyDirectionalFlame(
                List.of(enemy), null, List.of(), 0, 50, 1, 0, 50, 300, 120);
        assertEquals(50, applied, "Applied damage must not exceed the budget");
    }

    @Test
    void applyDirectionalFlame_multipleEnemiesKilledInSequence() {
        FlameTarget first  = blocker(100, 50, 30);
        FlameTarget second = blocker(200, 50, 30);
        int applied = DirectionalFlameEngine.applyDirectionalFlame(
                List.of(first, second), null, List.of(), 0, 50, 1, 0, 100, 300, 120);
        assertEquals(60, applied, "Both enemies must be killed when budget allows");
        assertEquals(0, first.hitPoints());
        assertEquals(0, second.hitPoints());
    }

    // -----------------------------------------------------------------------
    // All four cardinal directions — wall breaking
    // -----------------------------------------------------------------------

    @Test
    void applyDirectionalFlame_west_breakableWallDestroyed() {
        // Vertical wall to the WEST at x=30 (origin at x=100)
        Vector2D wallVec = new Vector2D(30, 0, 30, 100);
        GameMazeWorld world = new GameMazeWorld(() -> List.of(wallVec));
        world.assignBreakableWalls(0L, List.of(new WallMaterialSpec("GLASS", "Glass", 5)));

        int applied = DirectionalFlameEngine.applyDirectionalFlame(
                List.of(), world, world.getMazeVectors(), 100, 50, -1, 0, 100, 300, 120);

        assertTrue(applied >= 5, "Flame going west must deal at least the wall's HP");
        assertEquals(0, world.getMazeVectors().size(), "Breakable wall to the west must be removed");
    }

    @Test
    void applyDirectionalFlame_south_breakableWallDestroyed() {
        // Horizontal wall to the SOUTH at y=150 (origin at y=50)
        Vector2D wallVec = new Vector2D(0, 150, 100, 150);
        GameMazeWorld world = new GameMazeWorld(() -> List.of(wallVec));
        world.assignBreakableWalls(0L, List.of(new WallMaterialSpec("GLASS", "Glass", 5)));

        int applied = DirectionalFlameEngine.applyDirectionalFlame(
                List.of(), world, world.getMazeVectors(), 50, 50, 0, 1, 100, 300, 120);

        assertTrue(applied >= 5, "Flame going south must deal at least the wall's HP");
        assertEquals(0, world.getMazeVectors().size(), "Breakable wall to the south must be removed");
    }

    @Test
    void applyDirectionalFlame_north_breakableWallDestroyed() {
        // Horizontal wall to the NORTH at y=30 (origin at y=100)
        Vector2D wallVec = new Vector2D(0, 30, 100, 30);
        GameMazeWorld world = new GameMazeWorld(() -> List.of(wallVec));
        world.assignBreakableWalls(0L, List.of(new WallMaterialSpec("GLASS", "Glass", 5)));

        int applied = DirectionalFlameEngine.applyDirectionalFlame(
                List.of(), world, world.getMazeVectors(), 50, 100, 0, -1, 100, 300, 120);

        assertTrue(applied >= 5, "Flame going north must deal at least the wall's HP");
        assertEquals(0, world.getMazeVectors().size(), "Breakable wall to the north must be removed");
    }

    @Test
    void applyDirectionalFlame_allFourCardinalDirections_eachBreaksItsOwnWall() {
        // Arrange a cross of four breakable walls, one in each cardinal direction.
        // Origin at (200, 200).  Each wall is clearly in one corridor only.
        Vector2D eastWall  = new Vector2D(300, 180, 300, 220);  // vertical wall to the east
        Vector2D westWall  = new Vector2D(100, 180, 100, 220);  // vertical wall to the west
        Vector2D southWall = new Vector2D(180, 300, 220, 300);  // horizontal wall to the south
        Vector2D northWall = new Vector2D(180, 100, 220, 100);  // horizontal wall to the north

        GameMazeWorld world = new GameMazeWorld(
                () -> List.of(eastWall, westWall, southWall, northWall));
        // Directly register all four walls as breakable, bypassing the RNG.
        WallMaterialSpec glass = new WallMaterialSpec("GLASS", "Glass", 5);
        world.getBreakableWalls().clear();
        world.getBreakableWalls().add(new BreakableWall(eastWall,  new WallMaterialSpec("GLASS", "Glass", 5)));
        world.getBreakableWalls().add(new BreakableWall(westWall,  new WallMaterialSpec("GLASS", "Glass", 5)));
        world.getBreakableWalls().add(new BreakableWall(southWall, new WallMaterialSpec("GLASS", "Glass", 5)));
        world.getBreakableWalls().add(new BreakableWall(northWall, new WallMaterialSpec("GLASS", "Glass", 5)));

        List<Vector2D> walls = world.getMazeVectors();

        // Fire all four directions; each has independent budget.
        int east  = DirectionalFlameEngine.applyDirectionalFlame(List.of(), world, walls, 200, 200,  1,  0, 100, 300, 120);
        int west  = DirectionalFlameEngine.applyDirectionalFlame(List.of(), world, walls, 200, 200, -1,  0, 100, 300, 120);
        int south = DirectionalFlameEngine.applyDirectionalFlame(List.of(), world, walls, 200, 200,  0,  1, 100, 300, 120);
        int north = DirectionalFlameEngine.applyDirectionalFlame(List.of(), world, walls, 200, 200,  0, -1, 100, 300, 120);

        assertTrue(east  >= 5, "East flame must damage the east wall");
        assertTrue(west  >= 5, "West flame must damage the west wall");
        assertTrue(south >= 5, "South flame must damage the south wall");
        assertTrue(north >= 5, "North flame must damage the north wall");
        assertTrue(world.getMazeVectors().isEmpty(), "All four breakable walls must be destroyed");
    }

    @Test
    void applyDirectionalFlame_wallOutsideOriginSpanButInsideCorridor_isDestroyed() {
        // Wall spans y=[60..120]; originY=50 is OUTSIDE that span but within corridorHalfWidth=120.
        // With the corridor-based span check the wall must be found and damaged.
        Vector2D wallVec = new Vector2D(200, 60, 200, 120);
        GameMazeWorld world = new GameMazeWorld(() -> List.of(wallVec));
        world.assignBreakableWalls(0L, List.of(new WallMaterialSpec("GLASS", "Glass", 5)));

        int applied = DirectionalFlameEngine.applyDirectionalFlame(
                List.of(), world, world.getMazeVectors(), 0, 50, 1, 0, 100, 400, 120);

        assertTrue(applied >= 5,
                "Flame corridor overlapping the wall's span must damage the wall even when originY is outside the exact span");
        assertEquals(0, world.getMazeVectors().size(),
                "Wall inside the flame corridor must be destroyed");
    }
}

