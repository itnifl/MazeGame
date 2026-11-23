package main.game.maze.libgdx.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import main.game.maze.common.graphics.fire.RocketFlightEffect;
import main.game.maze.common.movement.WorldView;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.game.items.PickupKind;
import main.game.maze.game.items.PickupSpec;
import main.game.maze.game.items.Weapon;
import main.game.maze.game.score.PlayerIds;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.game.PlayerCombatStateService;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.mazeworld.flame.FlameTarget;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.WallSegment;
import main.game.maze.opponents.BehaviorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("libGDX shop weapons (#164): the same rules as JavaFX, in screen space")
class GdxStrikeSupportTest {

    private static final float HEIGHT = 400f;

    private final GameWorldModel world = new GameWorldModel();

    private static PlayerState facingRight() {
        PlayerState player = new PlayerState(100, 200, 24);
        player.attemptMove(1, 0, openMaze());
        return player;
    }

    @Test
    @DisplayName("a thrown grenade lands ahead of the player and goes off after its fuse, in screen space")
    void grenade() {
        PlayerState player = facingRight();
        String message = GdxStrikeSupport.use(world, openMaze(), player, Weapon.GRENADE, List.of());
        assertEquals("Grenade thrown", message);
        assertTrue(GdxStrikeSupport.tick(world, List.of(), openMaze(), 0.5f, PlayerIds.LOCAL_PLAYER).isEmpty());
        world.bombFuseGlows().clear();
        GdxStrikeSupport.addFuseGlows(world, openMaze(), 0.0);
        assertEquals(1, world.bombFuseGlows().size(), "the grenade glows where it lies");
        List<GdxStrikeSupport.Blast> due = GdxStrikeSupport.tick(world, List.of(), openMaze(), 1.0f, PlayerIds.LOCAL_PLAYER);
        assertEquals(1, due.size());
        PickupSpec grenade = world.pickupField().catalog().spec(PickupKind.GRENADE);
        assertEquals(player.x() + grenade.range(), due.get(0).x(), 1e-3, "thrown its full range");
        assertEquals(player.y(), due.get(0).y(), 1e-3, "back in screen space");
        assertTrue(due.get(0).hurtsPlayer());
    }

    @Test
    @DisplayName("the bunker buster rocket goes through a wall and wrecks a tower behind it, then blows up")
    void bunkerBuster() {
        PlayerState player = facingRight();
        int towerHealth = main.game.maze.game.level.LevelCatalogLoader.loadDefault().level(4).guardTower().health();
        GdxEnemyRuntime tower = enemy(300, 200, towerHealth);
        GdxStrikeSupport.Blast impact = GdxStrikeSupport.fireBunkerBuster(world, List.of(tower), openMaze(), player,
                world.pickupField().catalog().spec(PickupKind.BUNKER_BUSTER), PlayerIds.LOCAL_PLAYER);
        assertFalse(tower.isAlive(), "a rocket wrecks a tower");
        assertEquals(PlayerIds.LOCAL_PLAYER, tower.killerId());
        assertTrue(impact.x() > player.x() && impact.x() <= 300, "the blast is where the rocket stopped");
        assertEquals(player.y(), impact.y(), 1e-3);
        assertTrue(world.effects().stream().anyMatch(e -> e.visual().effect() instanceof RocketFlightEffect));
    }

    @Test
    @DisplayName("the extra life fills the health bar, and is not used at full health")
    void lifeRefill() {
        PlayerCombatStateService combat = new PlayerCombatStateService();
        combat.reset(100);
        assertFalse(GdxStrikeSupport.canRefillLife(combat), "full health");
        combat.applyDirectDamage(70);
        assertTrue(GdxStrikeSupport.canRefillLife(combat));
        GdxStrikeSupport.refillLife(world, combat);
        assertEquals(100, combat.currentHitPointsAsInt());
        assertTrue(world.healFeedback().active());
    }

    @Test
    @DisplayName("the soldiers see the enemies in world space and their rounds are credited to the player")
    void soldierTargets() {
        GdxEnemyRuntime enemy = enemy(150, 300, 60);
        List<FlameTarget> targets = GdxStrikeSupport.livingTargets(List.of(enemy), HEIGHT, PlayerIds.LOCAL_PLAYER);
        assertEquals(1, targets.size());
        assertEquals(150, targets.get(0).centerX(), 1e-6);
        assertEquals(HEIGHT - 300, targets.get(0).centerY(), 1e-6, "world y is down");
        targets.get(0).applyDamage(60);
        assertFalse(enemy.isAlive());
        assertEquals(DeathCause.SHOT, enemy.deathCause());
        assertEquals(PlayerIds.LOCAL_PLAYER, enemy.killerId());
        assertTrue(GdxStrikeSupport.livingTargets(List.of(enemy), HEIGHT, PlayerIds.LOCAL_PLAYER).isEmpty());
        assertTrue(GdxStrikeSupport.soldierSprites(world, openMaze(), 24f).isEmpty(), "no squad, no soldiers");
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
            @Override public float widthPx() { return 600; }
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
            @Override public double maxX() { return 600; }
            @Override public double maxY() { return 400; }
            @Override public boolean wouldCollide(double cx, double cy, double size) { return false; }
        };
    }

    @Test
    @DisplayName("the nuclear grenade lies on the floor as itself with a green halo, and goes off as a nuclear blast")
    void nuke() {
        PlayerState player = facingRight();
        GdxStrikeSupport.use(world, openMaze(), player, Weapon.NUKE_GRENADE, List.of());
        List<main.game.maze.libgdx.model.SpriteVisual> lying = GdxStrikeSupport.lyingSprites(world, openMaze());
        assertEquals(1, lying.size());
        assertEquals(world.pickupField().catalog().spec(PickupKind.NUKE_GRENADE).sprite(), lying.get(0).spritePath());
        world.bombFuseGlows().clear();
        GdxStrikeSupport.addFuseGlows(world, openMaze(), 0.0);
        var halo = world.bombFuseGlows().get(0);
        assertTrue(halo.haloOnly() && halo.green() > halo.red(), "a green halo round the sprite");
        List<GdxStrikeSupport.Blast> due = GdxStrikeSupport.tick(world, List.of(), openMaze(), 3.0f, PlayerIds.LOCAL_PLAYER);
        assertEquals(1, due.size());
        assertTrue(due.get(0).nuclear());
    }

    @Test
    @DisplayName("the soldiers are handed to the enemy AI in screen space (y up)")
    void soldiersOnScreen() {
        List<main.game.maze.mazeworld.Point2D> ground = new java.util.ArrayList<>();
        for (int i = 0; i < 12; i++) {
            ground.add(new main.game.maze.mazeworld.Point2D(50 + i * 30, 100));
        }
        GdxStrikeSupport.use(world, openMaze(), facingRight(), Weapon.INFANTRY_STRIKE, ground);
        var onScreen = GdxStrikeSupport.soldierPositionsOnScreen(world, openMaze());
        assertEquals(10, onScreen.size());
        onScreen.forEach(p -> assertEquals(HEIGHT - 100, p.getY(), 1e-6));
        var sprites = GdxStrikeSupport.soldierSprites(world, openMaze(), 24f);
        assertTrue(sprites.get(0).spritePath().startsWith(main.game.maze.game.strike.StrikeVisuals.SOLDIER_DIR));
    }

    @Test
    @DisplayName("what the player drops looks like what it is: the bomb as a bomb, airstrike bombs falling onto their shadow")
    void droppedThingsLookLikeThemselves() {
        var maze = openMaze();
        world.strikes().plantBomb(100, maze.heightPx() - 200, 50, null);
        List<main.game.maze.libgdx.model.SpriteVisual> sprites = GdxStrikeSupport.lyingSprites(world, maze);
        assertEquals(1, sprites.size());
        assertEquals(world.pickupField().catalog().spec(PickupKind.BOMB).sprite(), sprites.get(0).spritePath());
        assertEquals(200, sprites.get(0).y(), 1e-3, "screen space");

        GdxStrikeSupport.use(world, maze, facingRight(), Weapon.AIRSTRIKE, List.of());
        double fuse = world.pickupField().catalog().spec(PickupKind.AIRSTRIKE).fuseSeconds();
        // the first bomb comes into view a moment before it hits; the planted bomb goes off on the way (3 s fuse)
        GdxStrikeSupport.tick(world, List.of(), maze,
                (float) (fuse - main.game.maze.game.strike.StrikeVisuals.FALL_SECONDS / 2), PlayerIds.LOCAL_PLAYER);
        List<main.game.maze.libgdx.model.SpriteVisual> falling = GdxStrikeSupport.lyingSprites(world, maze);
        assertTrue(falling.stream().anyMatch(v -> v.spritePath().equals(main.game.maze.game.strike.StrikeVisuals.AERIAL_BOMB_SPRITE)));
        var shadow = falling.stream().filter(v -> v.spritePath().equals(main.game.maze.game.strike.StrikeVisuals.BOMB_SHADOW_SPRITE))
                .findFirst().orElseThrow();
        var bomb = falling.stream().filter(v -> v.spritePath().equals(main.game.maze.game.strike.StrikeVisuals.AERIAL_BOMB_SPRITE))
                .findFirst().orElseThrow();
        assertEquals(shadow.x(), bomb.x(), 1e-3);
        assertTrue(bomb.y() > shadow.y(), "the bomb is still above its shadow (up is +y)");
    }
}
