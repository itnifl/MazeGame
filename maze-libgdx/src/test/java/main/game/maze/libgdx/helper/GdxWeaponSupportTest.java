package main.game.maze.libgdx.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.common.graphics.fire.HitReactionEffect;
import main.game.maze.common.graphics.fire.LaserBeamEffect;
import main.game.maze.common.graphics.fire.SledgeSwingEffect;
import main.game.maze.common.movement.WorldView;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.game.items.PickupCatalog;
import main.game.maze.game.items.PickupKind;
import main.game.maze.game.items.Weapon;
import main.game.maze.game.score.PlayerIds;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.game.PlayerCombatStateService;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.libgdx.model.SpriteVisual;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.WallSegment;
import main.game.maze.opponents.BehaviorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("libGDX weapons, pickups and kill credit")
class GdxWeaponSupportTest {

    private static final float HEIGHT = 400f;

    private final GameWorldModel world = new GameWorldModel();
    private final PickupCatalog catalog = world.pickupField().catalog();

    @Test
    @DisplayName("the laser hits the enemy in front: a shot credited to the shooter, the enemy jolts")
    void laserHitsEnemyInFront() {
        PlayerState player = new PlayerState(100, 200, 24);
        player.attemptMove(1, 0, openMaze());                  // face right
        GdxEnemyRuntime inFront = enemy(250, 200, 60);
        GdxEnemyRuntime behind = enemy(20, 200, 60);
        List<GdxEnemyRuntime> enemies = List.of(inFront, behind);

        GdxWeaponSupport.fireLaser(world, enemies, openMaze(), player,
                catalog.spec(PickupKind.LASER_RIFLE), PlayerIds.LOCAL_PLAYER);

        assertFalse(inFront.isAlive(), "60 damage kills a 60 HP enemy");
        assertEquals(DeathCause.SHOT, inFront.deathCause());
        assertEquals(PlayerIds.LOCAL_PLAYER, inFront.killerId());
        assertEquals(60, behind.getHitPoints(), "the beam goes where the player faces");
        assertTrue(world.effects().stream().anyMatch(e -> e.visual().effect() instanceof LaserBeamEffect));
        assertTrue(world.effects().stream().anyMatch(e -> e.visual().effect() instanceof HitReactionEffect r
                && r.kind() == HitReactionEffect.Kind.SHOT));
        assertNotEquals(0f, inFront.reactionOffset()[0], "the shot enemy jolts");
    }

    @Test
    @DisplayName("the sledgehammer strikes enemies within reach with a crushing blow and leaves far ones alone")
    void sledgehammerStrikesNearby() {
        PlayerState player = new PlayerState(100, 200, 24);
        GdxEnemyRuntime near = enemy(140, 200, 100);
        GdxEnemyRuntime far = enemy(300, 200, 100);
        List<GdxEnemyRuntime> enemies = List.of(near, far);

        var struck = GdxWeaponSupport.swingSledgehammer(world, enemies, openMaze(), player,
                catalog.spec(PickupKind.SLEDGEHAMMER), PlayerIds.LOCAL_PLAYER);

        assertEquals(List.of(near), struck);
        assertFalse(near.isAlive());
        assertEquals(DeathCause.CRUSHED, near.deathCause());
        assertEquals(PlayerIds.LOCAL_PLAYER, near.killerId());
        assertEquals(100, far.getHitPoints());
        assertTrue(world.effects().stream().anyMatch(e -> e.visual().effect() instanceof SledgeSwingEffect));
        assertTrue(world.effects().stream().anyMatch(e -> e.visual().effect() instanceof HitReactionEffect r
                && r.kind() == HitReactionEffect.Kind.BLOW));
    }

    @Test
    @DisplayName("a kill raises the killer's points by the enemy's danger; nobody else gets them")
    void killsAreCreditedWithPoints() {
        GdxEnemyRuntime enemy = enemy(140, 200, 100);
        List<GdxEnemyRuntime> enemies = new ArrayList<>(List.of(enemy));
        enemy.subtractHitPoints(100, DeathCause.CRUSHED, PlayerIds.LOCAL_PLAYER);

        GdxGameCombatAndEnemyFlowSupport.processKilledEnemies(enemies, world.deadEnemies(), world.dyingEnemies(),
                world.remains(), world.retiredShooters(), world.killLedger());

        assertEquals(enemy.originalSpawn().killPoints(), world.killLedger().pointsFor(PlayerIds.LOCAL_PLAYER));
        assertTrue(enemy.originalSpawn().killPoints() > 0);
        assertEquals(0, world.killLedger().pointsFor("player-2"));
    }

    @Test
    @DisplayName("items spawn on corridor points and walking over one applies it")
    void pickupsSpawnAndApply() {
        MazeArena maze = openMaze();
        PlayerState player = new PlayerState(30, HEIGHT - 30, 24);   // world (30, 30)
        PlayerCombatStateService combat = new PlayerCombatStateService();
        combat.reset(100);
        List<Point2D> spots = List.of(new Point2D(300, 300));

        GdxWeaponSupport.updatePickups(world, maze, player, combat, spots, (float) catalog.spawnIntervalSeconds());
        assertEquals(1, world.pickupField().items().size(), "an item spawns after the interval");
        SpriteVisual sprite = GdxWeaponSupport.pickupSprites(world, maze, 0f).get(0);
        assertEquals(300f, sprite.x());
        assertEquals(HEIGHT - 300f, sprite.y(), 2.01f, "drawn in screen space (bobbing up to 2 px)");

        PickupKind kind = world.pickupField().items().get(0).kind();
        PlayerState onTop = new PlayerState(300, HEIGHT - 300, 24);
        var taken = GdxWeaponSupport.updatePickups(world, maze, onTop, combat, spots, 0.01f);
        assertEquals(1, taken.size());
        assertTrue(world.pickupField().items().isEmpty());
        assertPicked(kind, combat);
        assertFalse(world.effects().isEmpty(), "a sparkle shows the pickup");
    }

    private void assertPicked(PickupKind kind, PlayerCombatStateService combat) {
        switch (kind) {
            case BOMB -> assertEquals(1, world.arsenal().count(Weapon.BOMB));
            case LASER_RIFLE -> assertEquals(5, world.arsenal().count(Weapon.LASER_RIFLE));
            case SLEDGEHAMMER -> assertEquals(5, world.arsenal().count(Weapon.SLEDGEHAMMER));
            case SHIELD -> assertTrue(world.shield().isActive());
            case HEART -> assertEquals(100, combat.currentHitPointsAsInt());
        }
    }

    @Test
    @DisplayName("the laser rifle is drawn in the player's hands, mirrored when facing left; bombs are not held")
    void heldWeapon() {
        PlayerState player = new PlayerState(100, 200, 24);
        assertNull(GdxWeaponSupport.heldWeapon(world, player), "nothing held with only bombs");
        world.arsenal().add(Weapon.LASER_RIFLE, 5);
        world.arsenal().cycle();
        player.attemptMove(-1, 0, openMaze());
        SpriteVisual held = GdxWeaponSupport.heldWeapon(world, player);
        assertNotNull(held);
        assertTrue(held.flipX());
        assertTrue(held.x() < player.x(), "held towards the facing");
        assertEquals(catalog.spec(PickupKind.LASER_RIFLE).sprite(), held.spritePath());
    }

    @Test
    @DisplayName("crushing uses the enemy centre (regression: it used to be offset by half a sprite)")
    void crushUsesEnemyCentre() {
        GdxEnemyRuntime centredOnWall = enemy(100, 100, 100);
        // wall 20 px below the centre: within reach (0.4 * 40 + 6 = 22); the old +size/2 offset put it 40 px away
        GdxWallCollapseSupport.crush(List.of(centredOnWall), 0, 80, 300, 80, PlayerIds.LOCAL_PLAYER);
        assertFalse(centredOnWall.isAlive());
        assertEquals(PlayerIds.LOCAL_PLAYER, centredOnWall.killerId(), "crush credited to whoever brought the wall down");
    }

    private static GdxEnemyRuntime enemy(float x, float y, int maxHp) {
        EnemySpawn spawn = new EnemySpawn("zombie_weak_1", "/main/game/maze/zombie1-down.png", x, y, 40f,
                1.2f, 5, 0, "", BehaviorType.WANDER, 1f, 0d, EnemySpawn.DEFAULT_VISIBILITY_LEVEL,
                EnemySpawn.DEFAULT_PROJECTILE_TYPE, 0f, 0f, 0f, 0, 0f, 0, maxHp);
        return GdxEnemyRuntime.fromSpawn(spawn, 0, openWorld(), 30f, 8);
    }

    private static MazeArena openMaze() {
        return new MazeArena() {
            @Override public List<WallSegment> walls() { return List.of(); }
            @Override public float widthPx() { return 400; }
            @Override public float heightPx() { return HEIGHT; }
            @Override public float startX() { return 8; }
            @Override public float startY() { return 8; }
            @Override public float goalX() { return 380; }
            @Override public float goalY() { return 380; }
        };
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
}
