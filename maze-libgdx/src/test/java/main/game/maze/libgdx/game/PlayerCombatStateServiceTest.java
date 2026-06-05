package main.game.maze.libgdx.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.WallSegment;
import main.game.maze.opponents.BehaviorType;

class PlayerCombatStateServiceTest {

    @Test
    void contactDamageReducesHpRatioAndAppliesFlash() {
        PlayerCombatStateService service = new PlayerCombatStateService();
        service.reset(100);

        EnemySpawn enemy = new EnemySpawn("z1", "/main/game/maze/zombie1-down.png", 10f, 10f, 40f, 1f, 5, 0, "", 1f);
        var frame = service.update(1f / 30f, 10f, 10f, 15f, List.of(enemy));

        assertTrue(frame.hpRatio() < 1f);
        assertTrue(frame.tintGreen() < 1f);
        assertFalse(frame.dead());
    }

    @Test
    void lethalDamageMarksPlayerDead() {
        PlayerCombatStateService service = new PlayerCombatStateService();
        service.reset(20);

        EnemySpawn enemy = new EnemySpawn("z2", "/main/game/maze/zombie1-down.png", 10f, 10f, 40f, 101f, 1, 0, "", 1f);
        var frame = service.update(1f / 30f, 10f, 10f, 15f, List.of(enemy));

        assertTrue(frame.dead());
        assertTrue(service.isDead());
    }

    @Test
    void infectionTickEventuallyAppliesOngoingDamage() {
        PlayerCombatStateService service = new PlayerCombatStateService(() -> 0.0);
        service.reset(1000);

        EnemySpawn enemy = new EnemySpawn("z3", "/main/game/maze/zombie1-down.png", 10f, 10f, 40f, 1f, 100, 100, "/main/game/maze/zombieScream.mp3", 1f);
        service.update(1f / 30f, 10f, 10f, 15f, List.of(enemy));

        boolean hpDroppedBelowImmediateHit = false;
        for (int i = 0; i < 240; i++) {
            var frame = service.update(1f / 30f, 300f, 300f, 15f, List.of(enemy));
            if (frame.hpRatio() < 0.90f) {
                hpDroppedBelowImmediateHit = true;
                break;
            }
        }

        assertTrue(hpDroppedBelowImmediateHit);
    }

    // -----------------------------------------------------------------------
    // Wall-blocking tests (tasks #3 / #4)
    // -----------------------------------------------------------------------

    /**
     * A tangible enemy (nonTangibilityEnergy = 0) separated from the player by a
     * wall must NOT deal damage.
     */
    @Test
    void wallBetweenEnemyAndPlayer_blocksContactDamage() {
        PlayerCombatStateService service = new PlayerCombatStateService();
        service.reset(100);

        // Vertical wall at x=20 from y=0 to y=40.
        MazeArena arenaWithWall = new MazeArena() {
            @Override public List<WallSegment> walls() { return List.of(new WallSegment(20, 0, 20, 40)); }
            @Override public float widthPx()  { return 400; }
            @Override public float heightPx() { return 400; }
            @Override public float startX()   { return 10; }
            @Override public float startY()   { return 10; }
            @Override public float goalX()    { return 390; }
            @Override public float goalY()    { return 390; }
        };
        service.setMaze(arenaWithWall);

        // Enemy at x=10 (left of wall), player at x=30 (right of wall).
        EnemySpawn enemy = new EnemySpawn("z_wall", "", 10f, 10f, 40f, 0f, 100, 0, "", 1f);
        var frame = service.update(1f / 30f, 30f, 10f, 5f, List.of(enemy));

        assertEquals(1f, frame.hpRatio(), 0.001f,
                "Wall between enemy and player must prevent contact damage");
    }

    /**
     * A tangible enemy on the SAME side of a wall as the player must still deal damage.
     */
    @Test
    void wallNotBetweenEnemyAndPlayer_doesNotBlockDamage() {
        PlayerCombatStateService service = new PlayerCombatStateService();
        service.reset(100);

        // Wall far to the right — irrelevant to a collision at x=10..30.
        MazeArena arenaWithDistantWall = new MazeArena() {
            @Override public List<WallSegment> walls() { return List.of(new WallSegment(200, 0, 200, 40)); }
            @Override public float widthPx()  { return 400; }
            @Override public float heightPx() { return 400; }
            @Override public float startX()   { return 10; }
            @Override public float startY()   { return 10; }
            @Override public float goalX()    { return 390; }
            @Override public float goalY()    { return 390; }
        };
        service.setMaze(arenaWithDistantWall);

        // Enemy at (10,10) and player at (12,10) — same side of the wall; within contact range.
        EnemySpawn enemy = new EnemySpawn("z_same", "", 10f, 10f, 40f, 0f, 100, 0, "", 1f);
        var frame = service.update(1f / 30f, 12f, 10f, 5f, List.of(enemy));

        assertTrue(frame.hpRatio() < 1f,
                "Enemy on same side as player (no blocking wall) must deal contact damage");
    }

    /**
     * A phasing ghost (nonTangibilityEnergy > 0) MUST deal damage on contact.
     * Wall-blocking checks are skipped for phasing ghosts.
     */
    @Test
    void phasingGhostDoesDamage_evenWithoutWall() {
        PlayerCombatStateService service = new PlayerCombatStateService();
        service.reset(100);

        // No wall — enemy is phasing (nonTangibilityEnergy = 5.0 > 0).
        EnemySpawn enemy = new EnemySpawn("ghost", "", 10f, 10f, 40f, 0f, 100, 0, "", BehaviorType.WANDER, 1f, 5.0);
        var frame = service.update(1f / 30f, 10f, 10f, 15f, List.of(enemy));

        assertTrue(frame.hpRatio() < 1f,
                "Phasing ghost (nonTangibilityEnergy > 0) must still deal contact damage");
    }

    /**
     * A phasing ghost behind a wall must deal damage because phasing ghosts bypass
     * wall-blocking checks entirely.
     */
    @Test
    void phasingGhostBypassesWallCheck_dealsDamage() {
        PlayerCombatStateService service = new PlayerCombatStateService();
        service.reset(100);

        // Vertical wall at x=20 from y=0 to y=40 — same wall that blocks solid enemies.
        MazeArena arenaWithWall = new MazeArena() {
            @Override public List<WallSegment> walls() { return List.of(new WallSegment(20, 0, 20, 40)); }
            @Override public float widthPx()  { return 400; }
            @Override public float heightPx() { return 400; }
            @Override public float startX()   { return 10; }
            @Override public float startY()   { return 10; }
            @Override public float goalX()    { return 390; }
            @Override public float goalY()    { return 390; }
        };
        service.setMaze(arenaWithWall);

        // Enemy at x=10 (left of wall), player at x=30 (right of wall) — wall between them.
        // Ghost is phasing (energy=50) so wall check must be skipped.
        EnemySpawn phasingGhost = new EnemySpawn("ghost_wall", "", 10f, 10f, 40f, 0f, 100, 0, "", BehaviorType.WANDER, 1f, 50.0);
        var frame = service.update(1f / 30f, 30f, 10f, 25f, List.of(phasingGhost));

        assertTrue(frame.hpRatio() < 1f,
                "Phasing ghost must deal damage even when a wall separates it from the player");
    }
}



