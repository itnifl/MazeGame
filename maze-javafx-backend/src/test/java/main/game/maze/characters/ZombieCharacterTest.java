package main.game.maze.characters;

import javafx.scene.shape.Rectangle;
import main.game.maze.App;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.IAudioEngine;
import main.game.maze.characters.interfaces.PositionBounds;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.Zombie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ZombieCharacterTest {

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
        Rectangle gfx = new Rectangle(16, 16);
        assertDoesNotThrow(() -> new ZombieCharacter(gfx, 100, 100, basicZombie()));
    }

    // getDamage() returns a positive value based on the model's attackDamage.
    @Test
    void getDamage_returnsPositiveValue() {
        Rectangle gfx = new Rectangle(16, 16);
        ZombieCharacter zombie = new ZombieCharacter(gfx, 0, 0, basicZombie());
        assertTrue(zombie.getDamage() > 0, "getDamage() must return a positive value");
    }

    // subtractHitPoints reduces HP without crashing when HP remains above zero.
    @Test
    void subtractHitPoints_partialDamage_reducesHpWithoutCrash() {
        Rectangle gfx = new Rectangle(16, 16);
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

        Rectangle gfx = new Rectangle(16, 16);
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
        Rectangle gfx = new Rectangle(16, 16);
        ZombieCharacter zombie = new ZombieCharacter(gfx, 0, 0, basicZombie());

        assertDoesNotThrow(() -> zombie.addDeathNotificationSubscriber(entity -> {}));
    }

    // Model accessor returns the same model passed at construction.
    @Test
    void getModel_returnsModelPassedAtConstruction() {
        Rectangle gfx = new Rectangle(16, 16);
        Zombie model = basicZombie();
        ZombieCharacter zombie = new ZombieCharacter(gfx, 0, 0, model);

        assertSame(model, zombie.getModel());
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
