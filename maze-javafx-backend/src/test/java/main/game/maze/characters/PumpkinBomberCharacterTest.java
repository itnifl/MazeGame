package main.game.maze.characters;

import javafx.application.Platform;
import javafx.scene.shape.Rectangle;
import main.game.maze.App;
import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.characters.interfaces.PositionBounds;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.PumpkinBomber;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
}
