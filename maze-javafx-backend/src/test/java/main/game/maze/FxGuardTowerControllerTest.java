package main.game.maze;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.common.graphics.fire.RocketEffect;
import main.game.maze.game.level.GuardTowerSpec;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.mazeworld.constants.StageConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("JavaFX guard towers: rpgs, gatling, sounds, destruction")
class FxGuardTowerControllerTest {

    private static final GuardTowerSpec SPEC = LevelCatalogLoader.loadDefault().level(4).guardTower();
    private static final double HALF = StageConstants.PlayerCharacterXYSize * 0.5d;
    private static final long TICK = 20_000_000L;   // 20 ms

    private static final class Recording implements FxGuardTowerController.Host {
        final PlayerCharacter player = new PlayerCharacter(null, 0, 0, null);
        int walls;
        final List<String> sounds = new ArrayList<>();
        final List<Object[]> blasts = new ArrayList<>();
        final List<Integer> hurts = new ArrayList<>();
        final List<double[]> tracers = new ArrayList<>();
        final List<RocketEffect> effects = new ArrayList<>();

        @Override public PlayerCharacter player() { return player; }
        @Override public int wallsBetween(double a, double b, double c, double d) { return walls; }
        @Override public void blast(double x, double y, int damage, double range, String sound, boolean shake) {
            blasts.add(new Object[] {x, y, damage, range, sound, shake});
        }
        @Override public void hurtPlayer(int damage) { hurts.add(damage); }
        @Override public void tracer(double a, double b, double c, double d) { tracers.add(new double[] {a, b, c, d}); }
        @Override public void drawRockets(List<RocketEffect> rockets, double clock) { effects.addAll(rockets); }
        @Override public void play(String path, String key, long coolDownMillis) { sounds.add(path); }
    }

    private static final class FakeTower implements FxGuardTowerController.Body {
        int health = SPEC.health();
        @Override public double centerX() { return 300; }
        @Override public double centerY() { return 200; }
        @Override public int health() { return health; }
    }

    private Recording host;
    private FxGuardTowerController towers;
    private FakeTower tower;
    private long now;

    @BeforeEach
    void setUp() {
        host = new Recording();
        // the player stands 250 px to the right of the tower
        host.player.teleportTo(300 + 250 - HALF, 200 - HALF);
        towers = new FxGuardTowerController(host);
        towers.startGame(SPEC);
        tower = new FakeTower();
        towers.register("tower_0", tower);
        now = 1_000_000_000L;
    }

    private void runSeconds(double seconds) {
        for (int i = 0; i < Math.round(seconds / 0.02); i++) {
            now += TICK;
            towers.tick(now);
        }
    }

    @Test
    @DisplayName("the tower fires an rpg with its launch sound, then the rocket flies and explodes with the rpg explosion sound")
    void rpgFlightAndExplosion() {
        runSeconds(0.1);
        assertTrue(host.sounds.contains(SPEC.rpgLaunchSound()), "rpg launch sound");
        assertTrue(!host.effects.isEmpty(), "the rocket is drawn in flight");
        assertTrue(host.blasts.isEmpty(), "still in the air");
        runSeconds(1.0);   // 250 px at 300 px/s: under a second
        assertEquals(1, host.blasts.stream().filter(b -> SPEC.rpgExplosionSound().equals(b[4])).count());
        Object[] blast = host.blasts.get(0);
        assertEquals(SPEC.rpgWallDamage(), blast[2]);
        assertEquals(SPEC.rpgBlastRadius(), (double) blast[3], 1e-9);
        assertTrue(host.hurts.stream().anyMatch(damage -> damage >= SPEC.rpgPlayerDamage()), "the player stood in the blast");
    }

    @Test
    @DisplayName("the gatling fires between the rpgs: tracers and the gatling sound")
    void gatlingBetweenRpgs() {
        runSeconds(1.4);
        assertTrue(host.tracers.isEmpty(), "nothing before halfway between two rpgs");
        runSeconds(0.9);
        assertEquals(SPEC.gatlingRounds(), host.tracers.size(), "a burst of rounds");
        assertTrue(host.sounds.contains(SPEC.gatlingSound()));
    }

    @Test
    @DisplayName("more than three walls between: the tower holds its fire")
    void tooManyWalls() {
        host.walls = 4;
        runSeconds(5);
        assertTrue(host.tracers.isEmpty());
        assertTrue(host.effects.isEmpty());
        assertTrue(host.sounds.isEmpty());
    }

    @Test
    @DisplayName("damage plays the hit sound; destruction plays the destroyed sound and blows up with a screen shake")
    void hitAndDestroyed() {
        host.walls = 4;   // keep it quiet
        runSeconds(0.1);
        tower.health -= 100;
        runSeconds(0.1);
        assertEquals(List.of(SPEC.hitSound()), host.sounds);
        tower.health = 0;
        runSeconds(0.1);
        assertTrue(host.sounds.contains(SPEC.destroyedSound()));
        Object[] blast = host.blasts.get(host.blasts.size() - 1);
        assertEquals(SPEC.deathBlastDamage(), blast[2]);
        assertEquals(true, blast[5], "the screen shakes");
        runSeconds(4);
        assertEquals(1, host.blasts.size(), "once");
    }

    @Test
    @DisplayName("a level without towers does nothing")
    void noTowers() {
        towers.startGame(null);
        runSeconds(4);
        assertTrue(host.effects.isEmpty() && host.hurts.isEmpty());
    }
}
