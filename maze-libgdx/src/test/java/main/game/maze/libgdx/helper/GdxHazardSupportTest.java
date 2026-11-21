package main.game.maze.libgdx.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import main.game.maze.common.graphics.fire.HoleEffect;
import main.game.maze.common.graphics.fire.MinePulseEffect;
import main.game.maze.common.movement.WorldView;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.LevelSpec;
import main.game.maze.game.level.TeleportGhostSpec;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.opponents.BehaviorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("libGDX mines, holes and teleports")
class GdxHazardSupportTest {

    private static final float HEIGHT = 600f;
    private static final LevelSpec LEVEL = LevelCatalogLoader.loadDefault().level(3);

    private GameWorldModel model;
    private GdxHazardSupport hazards;

    /** Free points on a grid over 800 x 600 in world space (y-down), like a maze's corridor points. */
    private static List<Point2D> grid() {
        List<Point2D> points = new ArrayList<>();
        for (int x = 15; x < 800; x += 30) {
            for (int y = 15; y < 600; y += 30) {
                points.add(new Point2D(x, y));
            }
        }
        return points;
    }

    @BeforeEach
    void setUp() {
        model = new GameWorldModel();
        hazards = new GdxHazardSupport(new Random(5));
        // one sergeant post at world (400, 200); screen y = 600 - 200 = 400
        hazards.start(model, HEIGHT, LEVEL, List.of(new Point2D(400, 200)), grid(), spot -> true, (from, to) -> true);
    }

    private static GdxEnemyRuntime enemy(String id, float x, float y) {
        EnemySpawn spawn = new EnemySpawn(id, "/main/game/maze/ghost-portal.png", x, y, 40f, 3f, 0, 0, "",
                BehaviorType.WANDER, 1f, 0d, EnemySpawn.DEFAULT_VISIBILITY_LEVEL, EnemySpawn.DEFAULT_PROJECTILE_TYPE,
                0f, 0f, 0f, 0, 0f, 0, 60);
        return GdxEnemyRuntime.fromSpawn(spawn, 0, new WorldView() {
            @Override public double playerX() { return 0; }
            @Override public double playerY() { return 0; }
            @Override public double minX() { return 0; }
            @Override public double minY() { return 0; }
            @Override public double maxX() { return 800; }
            @Override public double maxY() { return 600; }
            @Override public boolean wouldCollide(double cx, double cy, double size) { return false; }
        }, 30f, 8);
    }

    private static long effectsOf(GameWorldModel model, Class<?> type) {
        return model.effects().stream().filter(e -> type.isInstance(e.visual().effect())).count();
    }

    @Test
    @DisplayName("level 3 puts a ring of pulsating mines round the sergeant, each one drawn")
    void minesPlaced() {
        assertEquals(LEVEL.mines().count(), hazards.field().mines().size());
        assertEquals(LEVEL.mines().count(), effectsOf(model, MinePulseEffect.class));
        assertNotNull(hazards.mineSpec());
    }

    @Test
    @DisplayName("a level without mines has none")
    void noMinesOnLevelTwo() {
        GdxHazardSupport plain = new GdxHazardSupport(new Random(1));
        GameWorldModel other = new GameWorldModel();
        plain.start(other, HEIGHT, LevelCatalogLoader.loadDefault().level(2), List.of(new Point2D(400, 200)), grid(),
                spot -> true, (from, to) -> true);
        assertNull(plain.mineSpec());
        assertEquals(0, effectsOf(other, MinePulseEffect.class));
    }

    @Test
    @DisplayName("touching a mine blows it: the mine's glow goes, a hole is drawn, and the player is sent to the opposite edge")
    void touchingAMine() {
        var mine = hazards.field().mines().get(0);
        PlayerState player = new PlayerState((float) mine.x(), HEIGHT - (float) mine.y(), 30);

        var result = hazards.tick(model, player, List.of(), 0.016f, 100.0);

        assertEquals(1, result.detonations().size());
        assertEquals(LEVEL.mines().count() - 1, effectsOf(model, MinePulseEffect.class), "the mine's glow is gone");
        assertEquals(1, effectsOf(model, HoleEffect.class), "a hole in the ground");
        assertNotNull(result.teleport(), "the player stands on the hole and falls in");
        assertEquals(GdxHazardSupport.Cause.HOLE, result.teleport().cause());
        // the mine is in the middle of the board horizontally, so the exit is at a side or the top or bottom
        float x = result.teleport().x();
        float y = result.teleport().y();
        assertTrue(x < 100 || x > 700 || y < 100 || y > 500, "an edge of the map: " + x + "," + y);
    }

    @Test
    @DisplayName("the exit of a hole is a free point, never inside a wall")
    void exitIsFree() {
        var mine = hazards.field().mines().get(0);
        PlayerState player = new PlayerState((float) mine.x(), HEIGHT - (float) mine.y(), 30);
        var teleport = hazards.tick(model, player, List.of(), 0.016f, 100.0).teleport();
        assertTrue(grid().contains(new Point2D(teleport.x(), HEIGHT - teleport.y())));
    }

    @Test
    @DisplayName("a second teleport waits for the cooldown")
    void cooldown() {
        var mine = hazards.field().mines().get(0);
        var other = hazards.field().mines().get(1);
        PlayerState player = new PlayerState((float) mine.x(), HEIGHT - (float) mine.y(), 30);
        assertNotNull(hazards.tick(model, player, List.of(), 0.016f, 100.0).teleport());

        player.teleportTo((float) other.x(), HEIGHT - (float) other.y());
        assertNull(hazards.tick(model, player, List.of(), 0.016f, 100.5).teleport(), "still cooling down");
        assertNotNull(hazards.tick(model, player, List.of(), 0.016f, 100.0 + LEVEL.teleportGhost().cooldownSeconds() + 0.1)
                .teleport(), "the player is still over a hole: falls once ready");
    }

    @Test
    @DisplayName("a portal ghost that touches the player sends them to a random spot at the edge of the map")
    void portalGhost() {
        PlayerState player = new PlayerState(400, 300, 30);
        GdxEnemyRuntime ghost = enemy(TeleportGhostSpec.SPAWN_ID_PREFIX + "_0", 410, 300);
        assertTrue(ghost.isPortalGhost());

        var result = hazards.tick(model, player, List.of(ghost), 0.016f, 50.0);

        assertNotNull(result.teleport());
        assertEquals(GdxHazardSupport.Cause.PORTAL_GHOST, result.teleport().cause());
        float x = result.teleport().x();
        float y = result.teleport().y();
        assertTrue(x < 100 || x > 700 || y < 100 || y > 500, "an edge of the map: " + x + "," + y);
        assertTrue(grid().contains(new Point2D(x, HEIGHT - y)), "never inside a wall");
    }

    @Test
    @DisplayName("a portal ghost far away, or an ordinary enemy next to the player, teleports nobody")
    void noTeleport() {
        PlayerState player = new PlayerState(400, 300, 30);
        assertNull(hazards.tick(model, player, List.of(enemy(TeleportGhostSpec.SPAWN_ID_PREFIX + "_0", 600, 300)), 0.016f, 10.0).teleport());
        GdxEnemyRuntime zombie = enemy("zombie_1", 405, 300);
        assertFalse(zombie.isPortalGhost());
        assertNull(hazards.tick(model, player, List.of(zombie), 0.016f, 20.0).teleport());
    }

    @Test
    @DisplayName("a portal ghost does not count as a contact that hurts")
    void portalGhostDoesNotHurt() {
        GdxEnemyRuntime ghost = enemy(TeleportGhostSpec.SPAWN_ID_PREFIX + "_0", 400, 300);
        GdxEnemyRuntime zombie = enemy("zombie_1", 400, 300);
        assertEquals(1, GdxGameRuntimeSupport.currentEnemyContacts(List.of(ghost, zombie)).size());
    }

    @Test
    @DisplayName("a new level forgets the mines, holes and their drawings")
    void reset() {
        var mine = hazards.field().mines().get(0);
        hazards.tick(model, new PlayerState((float) mine.x(), HEIGHT - (float) mine.y(), 30), List.of(), 0.016f, 1.0);
        hazards.reset(model);
        assertNull(hazards.mineSpec());
        assertEquals(0, effectsOf(model, MinePulseEffect.class));
    }
}
