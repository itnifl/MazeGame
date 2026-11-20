package main.game.maze.characters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import main.game.maze.FxEnemyCoordinator;
import main.game.maze.FxGameWorldModel;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.common.graphics.fire.HealFeedback;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.game.items.Pickup;
import main.game.maze.game.items.PickupCatalog;
import main.game.maze.game.items.PickupCatalogLoader;
import main.game.maze.game.items.PickupKind;
import main.game.maze.game.items.Weapon;
import main.game.maze.game.score.KillLedger;
import main.game.maze.game.score.KillScoring;
import main.game.maze.game.score.PlayerIds;
import main.game.maze.javafx.render.FxPickupLayerProbe;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.Vector2D.VectorFacing;
import main.game.maze.mazeworld.WallMaterialSpec;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.mazeworld.flame.LaserBeamEngine;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.Zombie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** JavaFX weapons, pickups and kill credit, using the same shared rules as libGDX. */
@DisplayName("JavaFX weapons parity with libGDX")
class FxWeaponsParityTest {

    private static final PickupCatalog CATALOG = PickupCatalogLoader.loadDefault();
    private static final double SIZE = StageConstants.ZombieCharacterXYSize;

    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    private static void onFx(Runnable action) throws Exception {
        AtomicReference<Throwable> failure = new AtomicReference<>();
        CountDownLatch done = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } catch (Throwable t) {
                failure.set(t);
            } finally {
                done.countDown();
            }
        });
        assertTrue(done.await(5, TimeUnit.SECONDS));
        if (failure.get() instanceof AssertionError ae) {
            throw ae;
        }
        if (failure.get() != null) {
            throw new RuntimeException(failure.get());
        }
    }

    private static Zombie model(int health) {
        Zombie zombie = OpponentsFactory.eINSTANCE.createZombie();
        zombie.setId("zombie_weak_1");
        zombie.setHealth(health);
        zombie.setSpeed(2.0);
        zombie.setAttackDamage(1);
        zombie.setThreatLevel(2.0);
        zombie.setBehavior(BehaviorType.WANDER);
        return zombie;
    }

    /** A zombie whose sprite centre is at (cx, cy). */
    private static ZombieCharacter zombieAt(double cx, double cy, int health) {
        return new ZombieCharacter(new Rectangle(16, 16), cx - SIZE / 2, cy - SIZE / 2, model(health));
    }

    @SuppressWarnings("unchecked")
    private static FxEnemyCoordinator coordinator(GameMazeWorld world, Object... characters) throws Exception {
        FxEnemyCoordinator coordinator = new FxEnemyCoordinator(
                () -> null, () -> null, new FxGameWorldModel(), () -> world, () -> null, () -> {});
        Field field = FxEnemyCoordinator.class.getDeclaredField("allComputerCharacters");
        field.setAccessible(true);
        ((List<Object>) field.get(coordinator)).addAll(List.of(characters));
        return coordinator;
    }

    @Test
    @DisplayName("the laser hits the enemy in line: a shot credited to the shooter")
    void laserHitsEnemyInLine() throws Exception {
        ZombieCharacter target = zombieAt(300, 100, 60);
        ZombieCharacter offLine = zombieAt(300, 300, 60);

        FxEnemyCoordinator.LaserHit hit = coordinator(null, target, offLine).fireLaser(100, 100, 1, 0,
                CATALOG.spec(PickupKind.LASER_RIFLE), 14, PlayerIds.LOCAL_PLAYER);

        assertEquals(LaserBeamEngine.Outcome.ENEMY, hit.shot().outcome());
        assertEquals(target, hit.enemy());
        assertFalse(target.isAlive());
        assertEquals(DeathCause.SHOT, target.deathCause());
        assertEquals(PlayerIds.LOCAL_PLAYER, target.killerId());
        assertEquals(60, offLine.getHitPoints());
    }

    @Test
    @DisplayName("the laser cannot harm steel: the enemy behind a steel wall is safe")
    void laserStoppedBySteel() throws Exception {
        Vector2D wall = new Vector2D(200, 0, 200, 200);
        GameMazeWorld world = new GameMazeWorld(() -> List.of(wall));
        world.assignBreakableWalls(0L, List.of(new WallMaterialSpec("STEEL_SOLID", "Steel", 80)));
        ZombieCharacter behind = zombieAt(300, 100, 60);

        FxEnemyCoordinator.LaserHit hit = coordinator(world, behind).fireLaser(100, 100, 1, 0,
                CATALOG.spec(PickupKind.LASER_RIFLE), 14, PlayerIds.LOCAL_PLAYER);

        assertEquals(LaserBeamEngine.Outcome.BLOCKED, hit.shot().outcome());
        assertNull(hit.enemy());
        assertEquals(60, behind.getHitPoints());
        assertEquals(80, world.findBreakableWall(wall).getRemainingHp(), "steel takes no laser damage");
    }

    @Test
    @DisplayName("the sledgehammer crushes enemies within reach, credited to the swinger")
    void sledgehammerStrikesNearby() throws Exception {
        ZombieCharacter near = zombieAt(140, 100, 100);
        ZombieCharacter far = zombieAt(400, 100, 100);

        List<ICanDie> struck = coordinator(null, near, far).swingSledgehammer(100, 100,
                CATALOG.spec(PickupKind.SLEDGEHAMMER), PlayerIds.LOCAL_PLAYER);

        assertEquals(List.of(near), struck);
        assertEquals(DeathCause.CRUSHED, near.deathCause());
        assertEquals(PlayerIds.LOCAL_PLAYER, near.killerId());
        assertEquals(100, far.getHitPoints());
    }

    @Test
    @DisplayName("kills go into the ledger with points by the enemy's danger (model effective threat)")
    void killPoints() {
        ZombieCharacter zombie = zombieAt(100, 100, 100);
        zombie.subtractHitPoints(100, DeathCause.FIRE, PlayerIds.LOCAL_PLAYER);
        KillLedger ledger = new KillLedger();
        // effective threat = threat level x health share (capped at 100 %): 2 x 1.0 = 2
        ledger.record(zombie, "zombie_weak_1", KillScoring.pointsFor(model(100)));
        assertEquals(2 * KillScoring.POINTS_PER_THREAT, ledger.pointsFor(PlayerIds.LOCAL_PLAYER));
    }

    @Test
    @DisplayName("the player: the power shield blocks damage, hearts heal up to the maximum, facing follows moves")
    void playerShieldHealFacing() {
        PlayerCharacter player = new PlayerCharacter(null, 0, 0, null);
        int max = player.getMaxHitPoints();
        player.setHitPoints(max);
        boolean[] shieldUp = {true};
        player.setShield(() -> shieldUp[0]);
        player.subtractHitPoints(30);
        assertEquals(max, player.getHitPoints());
        shieldUp[0] = false;
        player.subtractHitPoints(30);
        assertEquals(max - 30, player.getHitPoints());
        player.heal(1_000);
        assertEquals(max, player.getHitPoints());
        assertEquals(VectorFacing.RIGHT, player.getFacing());
    }

    @Test
    @DisplayName("pickup sprites follow the field; the held laser is mirrored facing left; bombs are not held")
    void pickupLayer() throws Exception {
        onFx(() -> {
            Pane board = new Pane();
            FxPickupLayerProbe layer = new FxPickupLayerProbe(board);
            Pickup heart = new Pickup(1, PickupKind.HEART, 50, 60);
            Pickup rifle = new Pickup(2, PickupKind.LASER_RIFLE, 150, 60);
            layer.showItems(List.of(heart, rifle), CATALOG, 0);
            assertEquals(2, layer.itemCount());
            for (javafx.scene.Node node : board.getChildren()) {
                assertEquals(CATALOG.itemSize(), ((javafx.scene.image.ImageView) node).getFitWidth(), 1e-9,
                        "items are drawn the model size (as big as the player)");
            }
            layer.showItems(List.of(rifle), CATALOG, 0);
            assertEquals(1, layer.itemCount(), "picked-up items disappear");

            layer.showHeldWeapon(Weapon.BOMB, 3, CATALOG, 100, 100, 12, VectorFacing.LEFT);
            assertNull(layer.heldView() == null ? null : layer.heldView().getParent(), "bombs are not held");
            layer.showHeldWeapon(Weapon.LASER_RIFLE, 5, CATALOG, 100, 100, 12, VectorFacing.LEFT);
            assertNotNull(layer.heldView().getParent());
            assertEquals(-1, layer.heldView().getScaleX(), "mirrored when facing left");
            assertTrue(layer.heldView().getLayoutX() + layer.heldView().getFitWidth() / 2 < 100, "held towards the facing");

            layer.showShield(100, 100, 20, 3.0, 0);

            HealFeedback heal = new HealFeedback();
            layer.showHeal(heal, 100, 100, 30, 0);
            assertNull(layer.healArrowView(), "no arrow without a heal");
            heal.trigger();
            heal.tick(HealFeedback.LIFETIME_SECONDS / 2);
            layer.showHeal(heal, 100, 100, 30, 0);
            assertNotNull(layer.healArrowView().getParent());
            assertNotNull(layer.healCanvas().getParent(), "the flash is painted over the player");
            assertTrue(layer.healArrowView().getLayoutY() + layer.healArrowView().getFitHeight() <= 100 - 15 + 0.01,
                    "the arrow is over the head (y-down)");
            heal.tick(HealFeedback.LIFETIME_SECONDS);
            layer.showHeal(heal, 100, 100, 30, 0);
            assertNull(layer.healArrowView().getParent(), "gone when over");

            layer.clear();
            assertEquals(0, layer.itemCount());
            assertTrue(board.getChildren().isEmpty());
        });
    }
}
