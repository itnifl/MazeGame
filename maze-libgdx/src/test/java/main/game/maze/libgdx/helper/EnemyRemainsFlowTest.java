package main.game.maze.libgdx.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.common.graphics.fire.RubbleMaterial;
import main.game.maze.common.movement.WorldView;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.common.remains.RemainsSpriteResolver;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.model.DeadEnemy;
import main.game.maze.libgdx.model.EnemyDeathAnimation;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.libgdx.model.RemainsVisual;
import main.game.maze.libgdx.model.WallRubbleVisual;
import main.game.maze.mazeworld.BreakableWall;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.WallMaterialSpec;
import main.game.maze.opponents.BehaviorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("libGDX enemy remains and wall collapse")
class EnemyRemainsFlowTest {

    private static final String DIR = RemainsSpriteResolver.REMAINS_DIR;
    private static final float HEIGHT = 400f;

    private final GameWorldModel world = new GameWorldModel();
    private final List<GdxEnemyRuntime> enemies = new ArrayList<>();
    private final List<DeadEnemy> dead = new ArrayList<>();
    private final List<EnemyDeathAnimation> dying = new ArrayList<>();

    @Test
    @DisplayName("an enemy burned by the bomb leaves a coal pile where it died")
    void fireLeavesCoal() {
        GdxEnemyRuntime zombie = enemy(50, 50, 0);
        enemies.add(zombie);

        zombie.takeDamage(1_000, DeathCause.FIRE);
        int killed = GdxGameCombatAndEnemyFlowSupport.processKilledEnemies(enemies, dead, dying, world.remains());

        assertEquals(1, killed);
        assertTrue(enemies.isEmpty());
        RemainsVisual remains = world.remains().values().get(0);
        assertTrue(remains.spritePath().startsWith(DIR + "burned-"), remains.spritePath());
        assertEquals(zombie.x(), remains.x());
        assertEquals(zombie.y(), remains.y());
        assertEquals(zombie.size() * zombie.spriteScale(), remains.size());
    }

    @Test
    @DisplayName("shot and crushed kills leave the enemy's own bloody remains; plain /kill leaves none")
    void killCauses() {
        enemies.add(enemy(50, 50, 0));
        GdxGameCombatAndEnemyFlowSupport.killEnemies(enemies, dead, dying, DeathCause.SHOT, world.remains());
        assertEquals(DIR + "zombie1-shot.png", world.remains().values().get(0).spritePath());

        world.remains().clear();
        enemies.add(enemy(50, 50, 0));
        GdxGameCombatAndEnemyFlowSupport.killEnemies(enemies, dead, dying, DeathCause.CRUSHED, world.remains());
        assertEquals(DIR + "zombie1-crushed.png", world.remains().values().get(0).spritePath());

        world.remains().clear();
        enemies.add(enemy(50, 50, 0));
        int killed = GdxGameCombatAndEnemyFlowSupport.killEnemies(enemies, dead, dying, DeathCause.OTHER, world.remains());
        assertEquals(1, killed);
        assertTrue(world.remains().values().isEmpty(), "unknown causes leave nothing, as before");
    }

    @Test
    @DisplayName("remains stay until that enemy respawns")
    void remainsClearedOnRespawn() {
        enemies.add(enemy(50, 50, 100));   // resurrects after 0.1 s
        enemies.add(enemy(200, 200, 0));   // never resurrects
        GdxGameCombatAndEnemyFlowSupport.killEnemies(enemies, dead, dying, DeathCause.FIRE, world.remains());
        assertEquals(2, world.remains().values().size());

        GdxGameCombatAndEnemyFlowSupport.tickResurrections(dead, enemies, openWorld(), 30f, 8, 0.2f, world.remains());

        assertEquals(1, enemies.size(), "the resurrecting enemy is back");
        assertEquals(1, world.remains().values().size(), "only the other enemy's remains are left");
        assertEquals(200f, world.remains().values().get(0).x());
    }

    @Test
    @DisplayName("a collapsing wall falls as rubble and crushes the enemy beside it, not one further away")
    void collapseCrushesNearbyEnemy() {
        GdxEnemyRuntime beside = enemy(50, 50, 0);    // centre (66, 66) on screen
        GdxEnemyRuntime farAway = enemy(300, 300, 0);
        enemies.add(beside);
        enemies.add(farAway);
        // horizontal wall at world y = 400 - 66 = 334, i.e. screen y = 66, right through the first enemy
        BreakableWall wall = new BreakableWall(new Vector2D(0, 334, 200, 334),
                new WallMaterialSpec("WOOD_BASIC", "Wood", 20));

        GdxWallCollapseSupport.onWallsCollapsed(world, enemies, List.of(wall), HEIGHT);

        assertFalse(beside.isAlive());
        assertEquals(DeathCause.CRUSHED, beside.deathCause());
        assertTrue(farAway.isAlive());
        GdxGameCombatAndEnemyFlowSupport.processKilledEnemies(enemies, dead, dying, world.remains());
        assertEquals(DIR + "zombie1-crushed.png", world.remains().values().get(0).spritePath());

        assertEquals(1, world.wallRubble().size());
        WallRubbleVisual rubble = world.wallRubble().get(0).visual();
        assertEquals(RubbleMaterial.WOOD, rubble.rubble().material());
        assertEquals(66.0, rubble.rubble().ay(), 1e-6, "world y-down converted to screen y-up");
        assertTrue(world.explosions().contains(rubble), "rubble is drawn with the other explosions");
    }

    @Test
    @DisplayName("rubble animations end after their lifetime")
    void rubbleExpires() {
        GdxWallCollapseSupport.onWallsCollapsed(world, enemies,
                List.of(new BreakableWall(new Vector2D(0, 10, 50, 10), new WallMaterialSpec("STONE_BASIC", "Stone", 40))),
                HEIGHT);
        GdxWallCollapseSupport.tick(world, 0.5f);
        assertEquals(1, world.wallRubble().size());
        assertTrue(world.wallRubble().get(0).visual().progress() > 0.4f);
        GdxWallCollapseSupport.tick(world, 0.6f);
        assertTrue(world.wallRubble().isEmpty());
    }

    @Test
    @DisplayName("damage of unknown kind and a cause-less kill leave no remains")
    void unknownCause() {
        GdxEnemyRuntime zombie = enemy(50, 50, 0);
        zombie.takeDamage(1_000);
        assertEquals(DeathCause.OTHER, zombie.deathCause());
        GdxEnemyRuntime other = enemy(50, 50, 0);
        other.takeDamage(1, DeathCause.FIRE);
        assertEquals(DeathCause.OTHER, other.deathCause(), "a non-lethal hit does not set the death cause");
    }

    private static GdxEnemyRuntime enemy(float x, float y, int resurrectionTimeMs) {
        EnemySpawn spawn = new EnemySpawn(
                "zombie-test", "/main/game/maze/zombie1-down.png", x, y, 32f,
                1f, 5, 0, "",
                BehaviorType.WANDER, 1f,
                0d, EnemySpawn.DEFAULT_VISIBILITY_LEVEL,
                EnemySpawn.DEFAULT_PROJECTILE_TYPE,
                EnemySpawn.DEFAULT_SPLASH_RADIUS,
                EnemySpawn.DEFAULT_ARC_HEIGHT,
                EnemySpawn.DEFAULT_ATTACK_RANGE,
                EnemySpawn.DEFAULT_ATTACK_COOLDOWN_MS,
                EnemySpawn.DEFAULT_PROJECTILE_SPEED,
                resurrectionTimeMs,
                50);
        return GdxEnemyRuntime.fromSpawn(spawn, 0, openWorld(), 30f, 8);
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
