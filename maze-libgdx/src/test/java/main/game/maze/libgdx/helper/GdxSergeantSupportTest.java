package main.game.maze.libgdx.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.common.graphics.fire.BulletSprayEffect;
import main.game.maze.common.movement.WorldView;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.game.items.Pickup;
import main.game.maze.game.items.PickupKind;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.SergeantSpec;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.game.PlayerCombatStateService;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.WallSegment;
import main.game.maze.opponents.BehaviorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("libGDX zombie sergeants: stand still, fire on sight, drop the gun")
class GdxSergeantSupportTest {

    private static final float HEIGHT = 400f;
    private static final SergeantSpec SPEC = LevelCatalogLoader.loadDefault().level(2).sergeant();

    private GameWorldModel world;
    private PlayerCombatStateService combat;
    private GdxSergeantSupport support;

    @BeforeEach
    void setUp() {
        world = new GameWorldModel();
        combat = new PlayerCombatStateService();
        combat.reset(100);
        support = new GdxSergeantSupport();
    }

    private static GdxEnemyRuntime sergeant(float x, float y) {
        EnemySpawn spawn = new EnemySpawn(SergeantSpec.SPAWN_ID_PREFIX + "-1", SPEC.sprite(), x, y, 40f,
                5f, 0, 0, "", BehaviorType.PASSIVE, 1f, 0d, EnemySpawn.DEFAULT_VISIBILITY_LEVEL,
                EnemySpawn.DEFAULT_PROJECTILE_TYPE, 0f, 0f, 0f, 0, 0f, 0, SPEC.health());
        return GdxEnemyRuntime.fromSpawn(spawn, 0, openWorld(), 30f, 8);
    }

    private static GdxEnemyRuntime zombie(float x, float y) {
        EnemySpawn spawn = new EnemySpawn("zombie_weak_1", "/main/game/maze/zombie1-down.png", x, y, 40f,
                1.2f, 5, 0, "", BehaviorType.WANDER, 1f, 0d, EnemySpawn.DEFAULT_VISIBILITY_LEVEL,
                EnemySpawn.DEFAULT_PROJECTILE_TYPE, 0f, 0f, 0f, 0, 0f, 0, 60);
        return GdxEnemyRuntime.fromSpawn(spawn, 0, openWorld(), 30f, 8);
    }

    @Test
    @DisplayName("a sergeant is recognised by its spawn id and never moves")
    void stationary() {
        GdxEnemyRuntime sergeant = sergeant(200, 200);
        assertTrue(sergeant.isStationary());
        assertFalse(zombie(100, 100).isStationary());

        for (int i = 0; i < 60; i++) {
            sergeant.advance(openWorld(), null, null, null, null, 0.05f);
        }
        assertEquals(200f, sergeant.x(), 1e-6);
        assertEquals(200f, sergeant.y(), 1e-6);
    }

    @Test
    @DisplayName("fires at a player in sight: damage, a tracer and a burst of rounds")
    void firesOnSight() {
        PlayerState player = new PlayerState(300, 200, 24);
        List<GdxEnemyRuntime> enemies = List.of(sergeant(200, 200));

        int total = 0;
        for (int i = 0; i < 40; i++) {
            total += support.tick(world, enemies, openMaze(), player, combat, SPEC, 0.05f);
        }

        // two seconds: a burst at once, a rest of 1.2 s from its start, then a second burst
        assertEquals(SPEC.roundsPerBurst() * 2, total, "bursts of " + SPEC.roundsPerBurst() + " rounds with a rest between");
    }

    @Test
    @DisplayName("every round hurts the player by the round damage and shows a tracer")
    void roundsHurtAndShowTracers() {
        PlayerState player = new PlayerState(300, 200, 24);
        List<GdxEnemyRuntime> enemies = List.of(sergeant(200, 200));
        int before = combat.currentHitPointsAsInt();

        int rounds = support.tick(world, enemies, openMaze(), player, combat, SPEC, 0f);

        assertEquals(1, rounds, "the first round is fired at once");
        assertEquals(before - SPEC.roundDamage(), combat.currentHitPointsAsInt());
        assertTrue(world.effects().stream().anyMatch(e -> e.visual().effect() instanceof BulletSprayEffect));
    }

    @Test
    @DisplayName("holds fire when a wall is in between or the player is too far away")
    void holdsFire() {
        PlayerState player = new PlayerState(300, 200, 24);
        List<GdxEnemyRuntime> enemies = List.of(sergeant(200, 200));
        MazeArena walled = arenaWith(new WallSegment(250, 100, 250, 300));
        assertEquals(0, support.tick(world, enemies, walled, player, combat, SPEC, 0.1f));

        PlayerState far = new PlayerState(200 + (float) SPEC.sightRange() + 60, 200, 24);
        assertEquals(0, support.tick(world, enemies, openMaze(), far, combat, SPEC, 0.1f));
        assertEquals(100, combat.currentHitPointsAsInt());
    }

    @Test
    @DisplayName("other enemies are left alone")
    void ignoresOtherEnemies() {
        PlayerState player = new PlayerState(300, 200, 24);
        assertEquals(0, support.tick(world, List.of(zombie(250, 200)), openMaze(), player, combat, SPEC, 0.1f));
    }

    @Test
    @DisplayName("a killed sergeant drops its gatling gun where it fell, once")
    void dropsTheGun() {
        PlayerState player = new PlayerState(300, 380, 24);
        GdxEnemyRuntime sergeant = sergeant(200, 100);
        List<GdxEnemyRuntime> enemies = new ArrayList<>(List.of(sergeant));
        support.tick(world, enemies, openMaze(), player, combat, SPEC, 0.1f);   // seen alive

        sergeant.subtractHitPoints(SPEC.health(), DeathCause.SHOT, "player");
        enemies.remove(sergeant);                                              // the game removes dead enemies
        support.tick(world, enemies, openMaze(), player, combat, SPEC, 0.1f);
        support.tick(world, enemies, openMaze(), player, combat, SPEC, 0.1f);

        List<Pickup> guns = world.pickupField().items().stream()
                .filter(item -> item.kind() == PickupKind.GATLING_GUN).toList();
        assertEquals(1, guns.size(), "dropped once");
        assertEquals(200, guns.get(0).x(), 1e-6);
        assertEquals(HEIGHT - 100, guns.get(0).y(), 1e-6, "world space is y-down");
    }

    @Test
    @DisplayName("a fresh level forgets the old sergeants")
    void reset() {
        GdxEnemyRuntime sergeant = sergeant(200, 100);
        support.tick(world, List.of(sergeant), openMaze(), new PlayerState(300, 380, 24), combat, SPEC, 0.1f);
        support.reset();
        support.tick(world, List.of(), openMaze(), new PlayerState(300, 380, 24), combat, SPEC, 0.1f);
        assertTrue(world.pickupField().items().isEmpty());
    }

    private static MazeArena arenaWith(WallSegment... walls) {
        return new MazeArena() {
            @Override public List<WallSegment> walls() { return List.of(walls); }
            @Override public float widthPx() { return 400; }
            @Override public float heightPx() { return HEIGHT; }
            @Override public float startX() { return 8; }
            @Override public float startY() { return 8; }
            @Override public float goalX() { return 380; }
            @Override public float goalY() { return 380; }
        };
    }

    private static MazeArena openMaze() {
        return arenaWith();
    }

    private static WorldView openWorld() {
        return new WorldView() {
            @Override public double playerX() { return 0; }
            @Override public double playerY() { return 0; }
            @Override public double minX() { return 0; }
            @Override public double minY() { return 0; }
            @Override public double maxX() { return 400; }
            @Override public double maxY() { return 400; }
            @Override public boolean wouldCollide(double cx, double cy, double size) { return false; }
        };
    }

    @Test
    @DisplayName("fires at a soldier of the infantry strike in sight before the player (SR-400)")
    void soldiersFirst() {
        PlayerState player = new PlayerState(300, 200, 24);
        int before = combat.currentHitPointsAsInt();
        List<Integer> soldierHits = new java.util.ArrayList<>();
        main.game.maze.game.targeting.SoldierTargets soldiers = main.game.maze.game.targeting.SoldierTargets.of(
                () -> List.of(new main.game.maze.mazeworld.Point2D(250, 200)), (at, damage) -> soldierHits.add(damage));

        int rounds = support.tick(world, List.of(sergeant(200, 200)), openMaze(), player, combat, SPEC, 0f, soldiers);

        assertEquals(1, rounds);
        assertEquals(List.of(SPEC.roundDamage()), soldierHits);
        assertEquals(before, combat.currentHitPointsAsInt(), "the player was not hit");
    }
}
