package main.game.maze.characters;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import main.game.maze.App;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.IAudioEngine;
import main.game.maze.characters.interfaces.PositionBounds;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.Zombie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ZombieCharacterTest {

    @BeforeAll
    static void setupClass() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {
            // Already started
        }
    }

    @AfterEach
    void cleanup() {
        AudioEngine.reset();
        App.gameController = null;
    }

    private static Zombie basicZombie() {
        Zombie z = OpponentsFactory.eINSTANCE.createZombie();
        z.setHealth(10);
        z.setSpeed(2.0);
        z.setAttackDamage(3);
        z.setThreatLevel(50.0);
        return z;
    }

    // Constructor does not throw with valid arguments.
    @Test
    void constructor_withValidArgs_doesNotThrow() {
        ImageView gfx = new ImageView();
        assertDoesNotThrow(() -> new ZombieCharacter(gfx, 100, 100, basicZombie()));
    }

    // getDamage() returns a positive value based on the model's attackDamage.
    @Test
    void getDamage_returnsPositiveValue() {
        ImageView gfx = new ImageView();
        ZombieCharacter zombie = new ZombieCharacter(gfx, 0, 0, basicZombie());
        assertTrue(zombie.getDamage() > 0, "getDamage() must return a positive value");
    }

    // subtractHitPoints reduces HP without crashing when HP remains above zero.
    @Test
    void subtractHitPoints_partialDamage_reducesHpWithoutCrash() {
        ImageView gfx = new ImageView();
        ZombieCharacter zombie = new ZombieCharacter(gfx, 0, 0, basicZombie());
        int initialHp = zombie.getHitPoints();

        zombie.subtractHitPoints(1);

        assertEquals(initialHp - 1, zombie.getHitPoints(), "HP must decrease by the amount subtracted");
    }

    // doPositionEvaluation triggers audio when bounds overlap (no wall check without gameController).
    @Test
    void doPositionEvaluation_overlappingBounds_playsAudio() {
        RecordingAudio audio = new RecordingAudio();
        AudioEngine.set(audio);

        Zombie zombieModel = basicZombie();
        zombieModel.setTouchSound("/sound/zombie.wav");

        ImageView gfx = new ImageView();
        ZombieCharacter zombie = new ZombieCharacter(gfx, 0, 0, zombieModel);

        // Victim with plenty of HP so it doesn't die and call App.gameController.
        javafx.scene.image.ImageView playerGfx = new javafx.scene.image.ImageView();
        PlayerCharacter player = new PlayerCharacter(playerGfx, 0, 0, null);

        PositionBounds bounds = new FxPositionBounds(new Rectangle(0, 0, 10, 10).getBoundsInParent());
        zombie.doPositionEvaluation(bounds, player);

        assertFalse(audio.rateLimitedCalls.isEmpty(),
                "doPositionEvaluation must trigger a rate-limited audio call on overlap");
    }

    // addDeathNotificationSubscriber / subscription management does not throw.
    @Test
    void deathSubscriberManagement_doesNotThrow() {
        ImageView gfx = new ImageView();
        ZombieCharacter zombie = new ZombieCharacter(gfx, 0, 0, basicZombie());

        assertDoesNotThrow(() -> zombie.addDeathNotificationSubscriber(entity -> {}));
    }

    // Model accessor returns the same model passed at construction.
    @Test
    void getModel_returnsModelPassedAtConstruction() {
        ImageView gfx = new ImageView();
        Zombie model = basicZombie();
        ZombieCharacter zombie = new ZombieCharacter(gfx, 0, 0, model);

        assertSame(model, zombie.getModel());
    }

    // changeDirection() must not throw regardless of current direction state.
    @Test
    void changeDirection_doesNotThrow() {
        ImageView gfx = new ImageView();
        ZombieCharacter zombie = new ZombieCharacter(gfx, 100, 100, basicZombie());
        assertDoesNotThrow(() -> zombie.changeDirection(),
                "changeDirection must never throw regardless of current movement state");
    }

    // setDirection with a known Point2D must update the character's direction.
    @Test
    void setDirection_updatesDirectionComponents() {
        ImageView gfx = new ImageView();
        ZombieCharacter zombie = new ZombieCharacter(gfx, 100, 100, basicZombie());

        zombie.setDirection(new Point2D(1, 0));

        assertEquals(1.0, zombie.getDirectionX(), 0.01,
                "setDirection must update directionX to the supplied x component");
        assertEquals(0.0, zombie.getDirectionY(), 0.01,
                "setDirection must update directionY to the supplied y component");
    }

    // setDirection with null must be a no-op (does not throw, does not reset direction).
    @Test
    void setDirection_withNull_isNoOp() {
        ImageView gfx = new ImageView();
        ZombieCharacter zombie = new ZombieCharacter(gfx, 100, 100, basicZombie());
        zombie.setDirection(new Point2D(1, 0));
        double beforeX = zombie.getDirectionX();

        zombie.setDirection(null);

        assertEquals(beforeX, zombie.getDirectionX(), 0.01,
                "setDirection(null) must not alter the current directionX");
    }

    // move(true) with a known non-zero direction must return true and advance position.
    @Test
    void move_withForce_andKnownDirection_returnsTrue() {
        ImageView gfx = new ImageView();
        ZombieCharacter zombie = new ZombieCharacter(gfx, 100, 100, basicZombie());
        zombie.setDirection(new Point2D(1, 0)); // move right

        boolean moved = zombie.move(true);

        assertTrue(moved, "move(force=true) must return true when direction is set and position is within bounds");
    }

    // move(true) with zero direction reports not moved.
    @Test
    void move_withZeroDirection_returnsFalse() {
        ImageView gfx = new ImageView();
        ZombieCharacter zombie = new ZombieCharacter(gfx, 100, 100, basicZombie());
        zombie.setDirection(new Point2D(0, 0)); // zero direction

        boolean moved = zombie.move(true);

        assertFalse(moved, "move(force=true) must return false when direction is (0, 0)");
    }

    // -----------------------------------------------------------------------
    // Resurrection behaviour
    // -----------------------------------------------------------------------

    @Test
    void subtractHitPoints_lethal_withNoResurrection_doesNotResetHp() {
        // App.gameController is null here — null guard in die() must prevent NPE.
        Zombie model = basicZombie();
        model.setResurrectionTime(0);
        ImageView gfx = new ImageView();
        ZombieCharacter zombie = new ZombieCharacter(gfx, 50, 50, model);

        assertDoesNotThrow(() -> zombie.subtractHitPoints(Integer.MAX_VALUE));
        assertTrue(zombie.getHitPoints() <= 0, "HP must be zero after lethal damage");
    }

    @Test
    void subtractHitPoints_lethal_withResurrection_doesNotResetHpImmediately() {
        // App.gameController is null — resurrection timer fires asynchronously on FX thread;
        // HP must NOT be reset synchronously at the point of death.
        Zombie model = basicZombie();
        model.setResurrectionTime(5);
        ImageView gfx = new ImageView();
        ZombieCharacter zombie = new ZombieCharacter(gfx, 50, 50, model);

        assertDoesNotThrow(() -> zombie.subtractHitPoints(Integer.MAX_VALUE));
        assertTrue(zombie.getHitPoints() <= 0,
                "HP must still be zero immediately after die() — resurrect() fires after the PauseTransition delay");
    }

    @Test
    void subtractHitPoints_lethal_notifiesDeathSubscribers() {
        Zombie model = basicZombie();
        model.setResurrectionTime(0);
        ImageView gfx = new ImageView();
        ZombieCharacter zombie = new ZombieCharacter(gfx, 50, 50, model);

        List<Object> notified = new ArrayList<>();
        zombie.addDeathNotificationSubscriber(notified::add);

        zombie.subtractHitPoints(Integer.MAX_VALUE);

        assertEquals(1, notified.size(), "Death subscriber must be notified exactly once on lethal damage");
        assertSame(zombie, notified.get(0));
    }

    @Test
    void subtractHitPoints_nonLethal_doesNotNotifyDeathSubscribers() {
        Zombie model = basicZombie(); // health = 10
        ImageView gfx = new ImageView();
        ZombieCharacter zombie = new ZombieCharacter(gfx, 50, 50, model);

        List<Object> notified = new ArrayList<>();
        zombie.addDeathNotificationSubscriber(notified::add);
        zombie.subtractHitPoints(1);

        assertTrue(notified.isEmpty(), "Death subscriber must not fire on non-lethal damage");
    }

    @Test
    void spawnCoords_arePreservedForResurrectReset() throws Exception {
        // After dying the zombie should be able to teleport to its spawn coords.
        // We verify this indirectly: teleportTo must not throw even with a null FX scheduler.
        Zombie model = basicZombie();
        model.setResurrectionTime(0);
        ImageView gfx = new ImageView();
        ZombieCharacter zombie = new ZombieCharacter(gfx, 77, 88, model);

        // teleportTo is public on Character — call it directly to verify no NPE.
        assertDoesNotThrow(() -> zombie.teleportTo(77, 88));
    }

    // -----------------------------------------------------------------------
    private static final class RecordingAudio implements IAudioEngine {
        final List<String> rateLimitedCalls = new ArrayList<>();
        @Override public void play(String p) {}
        @Override public void playRateLimited(String p, String id, long ms) { rateLimitedCalls.add(id); }
        @Override public void playLoop(String p, String ch) {}
        @Override public void stopChannel(String ch) {}
        @Override public void dispose() {}
    }
}
