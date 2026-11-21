package main.game.maze.libgdx.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.common.graphics.fire.PuffEffect;
import main.game.maze.common.graphics.fire.RocketEffect;
import main.game.maze.common.movement.WorldView;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.game.level.GuardTowerSpec;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.SergeantSpec;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.model.EnemyAnimationSpec;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.opponents.BehaviorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("libGDX guard towers: rpgs, gatling, sounds, destruction")
class GdxGuardTowerSupportTest {

    private static final GuardTowerSpec SPEC = LevelCatalogLoader.loadDefault().level(4).guardTower();

    private static final class Recording implements GdxGuardTowerSupport.Host {
        int walls;
        final List<String> sounds = new ArrayList<>();
        final List<Object[]> blasts = new ArrayList<>();
        final List<Integer> hurts = new ArrayList<>();
        final List<PuffEffect> effects = new ArrayList<>();

        @Override public int wallsBetween(float a, float b, float c, float d) { return walls; }
        @Override public void blast(float x, float y, int damage, float range, String sound, boolean shake) {
            blasts.add(new Object[] {x, y, damage, range, sound, shake});
        }
        @Override public void hurtPlayer(int damage) { hurts.add(damage); }
        @Override public void showEffect(PuffEffect effect) { effects.add(effect); }
        @Override public void play(String path, String key, long coolDownMillis) { sounds.add(path); }
    }

    private Recording host;
    private GdxGuardTowerSupport support;
    private GdxEnemyRuntime tower;
    private List<GdxEnemyRuntime> enemies;

    private static GdxEnemyRuntime tower(float x, float y) {
        EnemySpawn spawn = new EnemySpawn(GuardTowerSpec.SPAWN_ID_PREFIX + "-1", SPEC.sprite(), x, y, 56f,
                12f, 0, 0, "", BehaviorType.PASSIVE, 1f, 0d, EnemySpawn.DEFAULT_VISIBILITY_LEVEL,
                EnemySpawn.DEFAULT_PROJECTILE_TYPE, 0f, 0f, 0f, 0, 0f, 0, SPEC.health(),
                new EnemyAnimationSpec(null, null, null, null, 1, 1f, true), 0);
        return GdxEnemyRuntime.fromSpawn(spawn, 0, openWorld(), 30f, 8);
    }

    @BeforeEach
    void setUp() {
        host = new Recording();
        support = new GdxGuardTowerSupport();
        support.reset(SPEC);
        tower = tower(100, 200);
        enemies = new ArrayList<>(List.of(tower));
    }

    /** The player stands 250 px to the right of the tower. */
    private void run(double seconds) {
        for (int i = 0; i < Math.round(seconds / 0.02); i++) {
            support.tick(host, enemies, 350, 200, 0.02f);
        }
    }

    @Test
    @DisplayName("a tower is recognised by its spawn id, never moves and is not a sergeant")
    void recognisedAndStationary() {
        assertTrue(tower.isGuardTower());
        assertTrue(tower.isStationary());
        assertFalse(tower.isSergeant());
        EnemySpawn sergeant = new EnemySpawn(SergeantSpec.SPAWN_ID_PREFIX + "-1", "/x.png", 10, 10, 40f, 5f, 0, 0, "",
                BehaviorType.PASSIVE, 1f, 0d, EnemySpawn.DEFAULT_VISIBILITY_LEVEL, EnemySpawn.DEFAULT_PROJECTILE_TYPE,
                0f, 0f, 0f, 0, 0f, 0, 100);
        assertFalse(GdxEnemyRuntime.fromSpawn(sergeant, 0, openWorld(), 30f, 8).isGuardTower());
        for (int i = 0; i < 60; i++) {
            tower.advance(openWorld(), null, null, null, null, 0.05f);
        }
        assertEquals(100f, tower.x(), 1e-6);
        assertEquals(200f, tower.y(), 1e-6);
    }

    @Test
    @DisplayName("the rpg is launched with its sound, flies, then explodes with the rpg explosion sound and hurts the player")
    void rpgFlightAndExplosion() {
        run(0.1);
        assertTrue(host.sounds.contains(SPEC.rpgLaunchSound()));
        assertTrue(host.effects.stream().anyMatch(effect -> effect instanceof RocketEffect));
        assertTrue(host.blasts.isEmpty());
        run(1.0);
        assertEquals(1, host.blasts.stream().filter(b -> SPEC.rpgExplosionSound().equals(b[4])).count());
        assertTrue(host.hurts.stream().anyMatch(damage -> damage >= SPEC.rpgPlayerDamage()));
    }

    @Test
    @DisplayName("the gatling fires in between: a burst of rounds with the gatling sound halfway between two rpgs")
    void gatlingInBetween() {
        run(1.4);
        long before = host.sounds.stream().filter(SPEC.gatlingSound()::equals).count();
        assertEquals(0, before);
        run(0.9);
        assertTrue(host.sounds.stream().anyMatch(SPEC.gatlingSound()::equals));
    }

    @Test
    @DisplayName("more than three walls between: the tower holds its fire")
    void tooManyWalls() {
        host.walls = 4;
        run(5);
        assertTrue(host.effects.isEmpty());
        assertTrue(host.hurts.isEmpty());
        assertTrue(host.sounds.isEmpty());
    }

    @Test
    @DisplayName("a hit plays the hit sound; a destroyed tower plays the destroyed sound and blows up with a screen shake, once")
    void hitAndDestroyed() {
        host.walls = 4;
        run(0.1);
        tower.subtractHitPoints(100, DeathCause.SHOT, "player");
        run(0.1);
        assertEquals(List.of(SPEC.hitSound()), host.sounds);
        tower.subtractHitPoints(SPEC.health(), DeathCause.SHOT, "player");
        enemies.remove(tower);   // the game removes dead enemies
        run(0.1);
        assertTrue(host.sounds.contains(SPEC.destroyedSound()));
        Object[] blast = host.blasts.get(host.blasts.size() - 1);
        assertEquals(SPEC.deathBlastDamage(), blast[2]);
        assertEquals(true, blast[5]);
        run(4);
        assertEquals(1, host.blasts.size());
    }

    @Test
    @DisplayName("a destroyed tower leaves no corpse and no zombie death animation, and is credited to its killer")
    void noCorpse() {
        var world = new main.game.maze.libgdx.model.GameWorldModel();
        tower.subtractHitPoints(SPEC.health(), DeathCause.FIRE, "player");
        int killed = main.game.maze.libgdx.helper.GdxGameCombatAndEnemyFlowSupport.processKilledEnemies(
                enemies, world.deadEnemies(), world.dyingEnemies(), world.remains(), null, world.killLedger());
        assertEquals(1, killed);
        assertTrue(enemies.isEmpty());
        assertTrue(world.dyingEnemies().isEmpty(), "the blast is the death animation");
        assertTrue(world.deadEnemies().isEmpty(), "it does not come back");
    }

    @Test
    @DisplayName("a level without towers does nothing")
    void noTowers() {
        support.reset(null);
        run(4);
        assertTrue(host.effects.isEmpty());
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
