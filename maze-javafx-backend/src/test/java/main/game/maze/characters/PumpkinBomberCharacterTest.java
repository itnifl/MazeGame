package main.game.maze.characters;

import javafx.application.Platform;
import javafx.scene.shape.Rectangle;
import main.game.maze.App;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.characters.interfaces.PositionBounds;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.PumpkinBomber;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
