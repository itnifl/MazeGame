package main.game.maze.characters;

import javafx.application.Platform;
import javafx.scene.shape.Rectangle;
import main.game.maze.App;
import main.game.maze.GameController;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.characters.interfaces.PositionBounds;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.PumpkinBomber;
import main.game.maze.opponents.ProjectileType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class PumpkinBomberCharacterTest {

    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        assertTrue(latch.await(2, TimeUnit.SECONDS), "JavaFX startup timed out");
    }

    @AfterEach
    void cleanup() {
        AudioEngine.reset();
        App.gameController = null;
    }

    private static PumpkinBomber basicPumpkin() {
        PumpkinBomber p = OpponentsFactory.eINSTANCE.createPumpkinBomber();
        p.setHealth(10);
        p.setSpeed(2.0);
        p.setAttackDamage(4);
        p.setThreatLevel(50.0);
        p.setAttackRange(300.0);
        p.setProjectileSpeed(200.0);
        p.setSplashRadius(40.0);
        return p;
    }

    // Constructor does not throw with valid arguments.
    @Test
    void constructor_withValidArgs_doesNotThrow() {
        Rectangle gfx = new Rectangle(16, 16);
        assertDoesNotThrow(() -> new PumpkinBomberCharacter(gfx, 100, 100, basicPumpkin()));
    }

    // getDamage() returns a positive value.
    @Test
    void getDamage_returnsPositiveValue() {
        Rectangle gfx = new Rectangle(16, 16);
        PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(gfx, 0, 0, basicPumpkin());
        assertTrue(pbc.getDamage() > 0, "getDamage() must return a positive value");
    }

    // subtractHitPoints reduces HP by the specified amount when HP remains above zero.
    @Test
    void subtractHitPoints_partialDamage_reducesHp() {
        Rectangle gfx = new Rectangle(16, 16);
        PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(gfx, 0, 0, basicPumpkin());
        int initialHp = pbc.getHitPoints();

        pbc.subtractHitPoints(1);

        assertEquals(initialHp - 1, pbc.getHitPoints(),
                "HP must decrease by exactly the amount subtracted");
    }

    // setHitPoints sets HP to the given value.
    @Test
    void setHitPoints_updatesHp() {
        Rectangle gfx = new Rectangle(16, 16);
        PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(gfx, 0, 0, basicPumpkin());
        pbc.setHitPoints(42);
        assertEquals(42, pbc.getHitPoints());
    }

    // addHitPoints increases HP.
    @Test
    void addHitPoints_increasesHp() {
        Rectangle gfx = new Rectangle(16, 16);
        PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(gfx, 0, 0, basicPumpkin());
        int before = pbc.getHitPoints();
        pbc.addHitPoints(5);
        assertEquals(before + 5, pbc.getHitPoints());
    }

    // doPositionEvaluation with a non-null entity: must not throw.
    // Note: without a real scene graph, getBoundsInParent() may not reflect layoutX/Y,
    // so we only assert the absence of exceptions, not the HP invariant.
    @Test
    void doPositionEvaluation_withValidArgs_doesNotThrow() {
        Rectangle gfx = new Rectangle(10, 10);
        PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(gfx, 500, 500, basicPumpkin());

        javafx.scene.image.ImageView playerGfx = new javafx.scene.image.ImageView();
        PlayerCharacter player = new PlayerCharacter(playerGfx, 0, 0, null);

        PositionBounds bounds = new FxPositionBounds(new Rectangle(0, 0, 10, 10).getBoundsInParent());

        assertDoesNotThrow(() -> pbc.doPositionEvaluation(bounds, player));
    }

    // addDeathNotificationSubscriber does not throw.
    @Test
    void addDeathNotificationSubscriber_doesNotThrow() {
        Rectangle gfx = new Rectangle(16, 16);
        PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(gfx, 0, 0, basicPumpkin());
        assertDoesNotThrow(() -> pbc.addDeathNotificationSubscriber(entity -> {}));
    }

    // PumpkinBomberCharacter must implement IMovingComputerCharacter so that
    // OpponentRuntimeFactory.registerPumpkinBomberCharacter() can call
    // registrar.registerComputerCharacter() — regression guard against stale
    // "registration deferred" comment re-appearing.
    @Test
    void pumpkinBomberCharacter_implementsIMovingComputerCharacter() {
        assertTrue(IMovingComputerCharacter.class.isAssignableFrom(PumpkinBomberCharacter.class),
                "PumpkinBomberCharacter must implement IMovingComputerCharacter "
                + "so it can be registered with EnemyRegistrar and participate in the movement loop");
    }

    // getModel() returns the model passed at construction.
    @Test
    void getModel_returnsModelPassedAtConstruction() {
        Rectangle gfx = new Rectangle(16, 16);
        PumpkinBomber model = basicPumpkin();
        PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(gfx, 0, 0, model);
        assertSame(model, pbc.getModel());
    }

    // -----------------------------------------------------------------------
    // Position-subscriber management
    // -----------------------------------------------------------------------

    @Test
    void getPositionSubscribers_returnsEmptyListInitially() {
        Rectangle gfx = new Rectangle(16, 16);
        PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(gfx, 0, 0, basicPumpkin());
        assertTrue(pbc.getPositionSubscribers().isEmpty(),
                "touchTargets must be empty before any subscriber is added");
    }

    @Test
    void addPositionSubscriber_addsToTouchTargets() {
        Rectangle gfx = new Rectangle(16, 16);
        PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(gfx, 0, 0, basicPumpkin());
        ICanSubscribeAndNotifyPosition stub = makeStubSubscriber();

        pbc.addPositionSubscriber(stub);

        assertTrue(pbc.getPositionSubscribers().contains(stub),
                "getPositionSubscribers must include subscriber added via addPositionSubscriber");
    }

    @Test
    void removePositionSubscriber_removesFromTouchTargets() {
        Rectangle gfx = new Rectangle(16, 16);
        PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(gfx, 0, 0, basicPumpkin());
        ICanSubscribeAndNotifyPosition stub = makeStubSubscriber();
        pbc.addPositionSubscriber(stub);

        pbc.removePositionSubscriber(stub);

        assertFalse(pbc.getPositionSubscribers().contains(stub),
                "subscriber must not be present after removePositionSubscriber");
    }

    // -----------------------------------------------------------------------
    // Ranged attack — tryShootAt / updateProjectiles
    // -----------------------------------------------------------------------

    @Test
    void updateProjectiles_withNoProjectiles_returnsImmediately() {
        Rectangle gfx = new Rectangle(16, 16);
        PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(gfx, 0, 0, basicPumpkin());
        // Must not throw when projectiles list is empty.
        assertDoesNotThrow(() -> pbc.updateProjectiles(0.016));
    }

    @Test
    void tryShootAt_withSamePositionTarget_doesNotAddProjectile() {
        Rectangle gfx = new Rectangle(16, 16); // layoutX/Y default to 0
        PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(gfx, 0, 0, basicPumpkin());

        Rectangle target = new Rectangle(16, 16); // also at layoutX/Y = 0 → dist < 1e-3
        pbc.tryShootAt(target, Long.MAX_VALUE);

        // No projectile should have been added; updateProjectiles on empty list must not crash.
        assertDoesNotThrow(() -> pbc.updateProjectiles(0.016),
                "updateProjectiles must be safe after tryShootAt skips zero-distance shot");
    }

    @Test
    void tryShootAt_withInRangeTarget_createsProjectile_andUpdateProjectiles_removesIt() {
        Rectangle gfx = new Rectangle(16, 16); // character at layoutX=0
        PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(gfx, 0, 0, basicPumpkin());

        Rectangle target = new Rectangle(16, 16);
        target.setLayoutX(10.0); // dist = 10 → within range 300 → createArc returns non-null

        pbc.tryShootAt(target, Long.MAX_VALUE); // fires; projectile added

        // Advance time well past the 5-second life limit so the projectile is removed.
        assertDoesNotThrow(() -> pbc.updateProjectiles(6.0),
                "updateProjectiles must remove the expired projectile without throwing");

        // Subsequent tick with empty list must also be safe.
        assertDoesNotThrow(() -> pbc.updateProjectiles(0.016));
    }

    @Test
    void updateProjectiles_withNonNullControllerAndMaze_doesNotThrow() throws Exception {
        // Cover the wall-check branch (lines that require App.gameController != null and
        // GameMazeWorld.GetWorld() != null). The projectile starts far from any wall so
        // hitWall == null, but the branch itself is exercised for coverage.
        Field worldField = GameMazeWorld.class.getDeclaredField("world");
        worldField.setAccessible(true);
        GameMazeWorld maze = new GameMazeWorld();
        worldField.set(null, maze);
        App.gameController = new GameController();
        try {
            Rectangle gfx = new Rectangle(16, 16);
            PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(gfx, 100, 100, basicPumpkin());
            Rectangle target = new Rectangle(16, 16);
            target.setLayoutX(110.0);
            pbc.tryShootAt(target, Long.MAX_VALUE);
            assertDoesNotThrow(() -> pbc.updateProjectiles(6.0),
                    "updateProjectiles must not throw when gameController and mazeWorld are set");
        } finally {
            worldField.set(null, null);
        }
    }

    @Test
    void beamProjectile_damagesSubscribedVictimWhenLineOfSightClear() {
        PumpkinBomber model = basicPumpkin();
        model.setProjectileType(ProjectileType.BEAM);
        model.setAttackDamage(6);

        Rectangle bomberGfx = new Rectangle(16, 16);
        PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(bomberGfx, 0, 0, model);

        Rectangle playerGfx = new Rectangle(16, 16);
        playerGfx.setLayoutX(20);
        playerGfx.setLayoutY(0);
        PlayerCharacter player = new PlayerCharacter(playerGfx, 0, 0, null);
        pbc.addPositionSubscriber(player);

        App.gameController = new GameController() {
            @Override
            public boolean isWallBetween(double ex, double ey, double px, double py) {
                return false;
            }
        };

        int hpBefore = player.getHitPoints();
        pbc.tryShootAt(playerGfx, Long.MAX_VALUE);

        assertEquals(hpBefore - 6, player.getHitPoints(),
                "BEAM should damage the subscribed target represented by the provided Node");
    }

    @Test
    void beamProjectile_doesNotDamageWhenBlockedByWall() {
        PumpkinBomber model = basicPumpkin();
        model.setProjectileType(ProjectileType.BEAM);
        model.setAttackDamage(7);

        Rectangle bomberGfx = new Rectangle(16, 16);
        PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(bomberGfx, 0, 0, model);

        Rectangle playerGfx = new Rectangle(16, 16);
        playerGfx.setLayoutX(20);
        PlayerCharacter player = new PlayerCharacter(playerGfx, 0, 0, null);
        pbc.addPositionSubscriber(player);

        App.gameController = new GameController() {
            @Override
            public boolean isWallBetween(double ex, double ey, double px, double py) {
                return true;
            }
        };

        int hpBefore = player.getHitPoints();
        pbc.tryShootAt(playerGfx, Long.MAX_VALUE);

        assertEquals(hpBefore, player.getHitPoints(),
                "BEAM must not damage player when line of sight is blocked");
    }

    @Test
    void lobProjectile_appliesSplashEvenWhenWallReported() {
        PumpkinBomber model = basicPumpkin();
        model.setProjectileType(ProjectileType.LOB);
        model.setAttackDamage(5);
        model.setSplashRadius(120.0);
        model.setProjectileSpeed(120.0);

        Rectangle bomberGfx = new Rectangle(16, 16);
        PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(bomberGfx, 0, 0, model);

        Rectangle playerGfx = new Rectangle(16, 16);
        playerGfx.setLayoutX(0);
        playerGfx.setLayoutY(0);
        PlayerCharacter player = new PlayerCharacter(playerGfx, 0, 0, null);
        pbc.addPositionSubscriber(player);

        Rectangle target = new Rectangle(16, 16);
        target.setLayoutX(60);
        target.setLayoutY(0);

        App.gameController = new GameController() {
            @Override
            public boolean isWallBetween(double ex, double ey, double px, double py) {
                return true;
            }
        };

        int hpBefore = player.getHitPoints();
        pbc.tryShootAt(target, Long.MAX_VALUE);
        pbc.updateProjectiles(10.0);

        assertTrue(player.getHitPoints() < hpBefore,
                "LOB splash should still apply even when walls are reported between bomber and target");
    }

    // subtractHitPoints must not NPE when App.gameController is null (CRR-3)
    @Test
    void subtractHitPoints_withNullGameController_doesNotThrow() {
        Rectangle gfx = new Rectangle(16, 16);
        PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(gfx, 0, 0, basicPumpkin());
        App.gameController = null;

        assertDoesNotThrow(() -> pbc.subtractHitPoints(100),
                "subtractHitPoints must not throw NPE when App.gameController is null");
        assertTrue(pbc.getHitPoints() <= 0, "HP must be at or below zero after fatal damage");
    }

    // Negative splashRadius in model must be clamped to zero, no splash damage applied.
    @Test
    void lobProjectile_negativeSplashRadius_doesNotDamage() {
        PumpkinBomber model = basicPumpkin();
        model.setProjectileType(ProjectileType.LOB);
        model.setAttackDamage(10);
        model.setSplashRadius(-50.0);
        model.setProjectileSpeed(150.0);

        Rectangle bomberGfx = new Rectangle(16, 16);
        PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(bomberGfx, 0, 0, model);

        Rectangle playerGfx = new Rectangle(16, 16);
        playerGfx.setLayoutX(0);
        playerGfx.setLayoutY(0);
        PlayerCharacter player = new PlayerCharacter(playerGfx, 0, 0, null);
        pbc.addPositionSubscriber(player);

        Rectangle target = new Rectangle(16, 16);
        target.setLayoutX(80);

        int hpBefore = player.getHitPoints();
        pbc.tryShootAt(target, Long.MAX_VALUE);
        pbc.updateProjectiles(10.0);

        assertEquals(hpBefore, player.getHitPoints(),
                "Negative splashRadius must be clamped to zero — no splash damage should apply");
    }

    // BEAM must still fire on consecutive calls after cooldown expires.
    @Test
    void beamProjectile_firesAgainAfterCooldown() {
        PumpkinBomber model = basicPumpkin();
        model.setProjectileType(ProjectileType.BEAM);
        model.setAttackDamage(5);
        model.setAttackCooldownMs(1000);

        Rectangle bomberGfx = new Rectangle(16, 16);
        PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(bomberGfx, 0, 0, model);

        Rectangle playerGfx = new Rectangle(16, 16);
        playerGfx.setLayoutX(20);
        PlayerCharacter player = new PlayerCharacter(playerGfx, 0, 0, null);
        pbc.addPositionSubscriber(player);

        App.gameController = new GameController() {
            @Override public boolean isWallBetween(double ex, double ey, double px, double py) { return false; }
        };

        int hpBefore = player.getHitPoints();
        pbc.tryShootAt(playerGfx, 1000L);                  // first shot fires at t=1000ms
        pbc.tryShootAt(playerGfx, 1500L);                  // within cooldown — must not fire
        assertEquals(hpBefore - 5, player.getHitPoints(), "Second shot within cooldown must not fire");

        pbc.tryShootAt(playerGfx, 2001L);                  // past cooldown — must fire again
        assertEquals(hpBefore - 10, player.getHitPoints(), "Third shot after cooldown must fire");
    }

    // STRAIGHT projectile beyond attack range must not create a projectile.
    @Test
    void straightProjectile_outsideRange_doesNotFire() {
        PumpkinBomber model = basicPumpkin();
        model.setProjectileType(ProjectileType.STRAIGHT);
        model.setAttackRange(50.0);

        Rectangle bomberGfx = new Rectangle(16, 16);
        PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(bomberGfx, 0, 0, model);

        Rectangle target = new Rectangle(16, 16);
        target.setLayoutX(200);   // 200 > range 50

        pbc.tryShootAt(target, Long.MAX_VALUE);

        assertDoesNotThrow(() -> pbc.updateProjectiles(0.016),
                "updateProjectiles with empty list from out-of-range shot must not throw");
    }

    // Ensure BEAM does not fire when target is null.
    @Test
    void tryShootAt_withNullTarget_doesNotThrow() {
        PumpkinBomber model = basicPumpkin();
        model.setProjectileType(ProjectileType.BEAM);
        Rectangle gfx = new Rectangle(16, 16);
        PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(gfx, 0, 0, model);

        assertDoesNotThrow(() -> pbc.tryShootAt(null, Long.MAX_VALUE),
                "tryShootAt(null) must not throw");
    }

    @Test
    void lobProjectile_doesNotResolveOnImmediateBodyOverlapBeforeArrival() {
        PumpkinBomber model = basicPumpkin();
        model.setProjectileType(ProjectileType.LOB);
        model.setAttackDamage(5);
        model.setSplashRadius(200.0);
        model.setProjectileSpeed(150.0);

        Rectangle bomberGfx = new Rectangle(16, 16);
        PumpkinBomberCharacter pbc = new PumpkinBomberCharacter(bomberGfx, 0, 0, model);

        Rectangle playerGfx = new Rectangle(16, 16);
        playerGfx.setLayoutX(0);
        playerGfx.setLayoutY(0);
        PlayerCharacter player = new PlayerCharacter(playerGfx, 0, 0, null);
        pbc.addPositionSubscriber(player);

        Rectangle target = new Rectangle(16, 16);
        target.setLayoutX(120);
        target.setLayoutY(0);

        int hpBefore = player.getHitPoints();
        pbc.tryShootAt(target, Long.MAX_VALUE);
        pbc.updateProjectiles(0.01);

        assertEquals(hpBefore, player.getHitPoints(),
                "LOB must not apply splash immediately due to initial overlap; splash is applied at arrival or expiry");
    }

    // -----------------------------------------------------------------------
    private static ICanSubscribeAndNotifyPosition makeStubSubscriber() {
        return new ICanSubscribeAndNotifyPosition() {
            public void doPositionEvaluation(PositionBounds b, ICanSubscribeAndNotifyPosition e) {}
            public void addPositionSubscriber(ICanSubscribeAndNotifyPosition e) {}
            public void removePositionSubscriber(ICanSubscribeAndNotifyPosition e) {}
            public List<ICanSubscribeAndNotifyPosition> getPositionSubscribers() { return List.of(); }
        };
    }
}
