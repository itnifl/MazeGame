package main.game.maze;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.game.session.PlayerLives;
import main.game.maze.interfaces.IDeathSubscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** {@link FxPlayerLifeController} against a recording host: respawn while lives are left, then game over. */
@DisplayName("JavaFX player lives and respawn")
class FxPlayerLifeControllerTest {

    private static final class RecordingHost implements FxPlayerLifeController.Host {
        final PlayerCharacter player = new PlayerCharacter(null, 40, 60, null);
        final List<ICanDie> gameOvers = new ArrayList<>();
        final List<String> messages = new ArrayList<>();
        Runnable pending;
        double pendingDelay;
        double protectedFor;
        int stops;
        int resumes;
        int livesChanges;

        @Override public PlayerCharacter player() { return player; }
        @Override public void stopPlayerMovement() { stops++; }
        @Override public void resumePlayerMovement() { resumes++; }
        @Override public void protect(double seconds) { protectedFor = seconds; }
        @Override public void showMessage(String text) { messages.add(text); }
        @Override public void livesChanged() { livesChanges++; }
        @Override public void after(double seconds, Runnable action) { pendingDelay = seconds; pending = action; }
        @Override public IDeathSubscriber gameOver() { return gameOvers::add; }

        void runPending() {
            Runnable action = pending;
            pending = null;
            action.run();
        }
    }

    private RecordingHost host;
    private FxPlayerLifeController lives;

    @BeforeEach
    void setUp() {
        host = new RecordingHost();
        lives = new FxPlayerLifeController(new PlayerLives(1), host);
        lives.startGame(host.player, 2);
    }

    private void kill() {
        host.player.subtractHitPoints(host.player.getHitPoints() + 10);
        lives.AddDeathNotification(host.player);
    }

    @Test
    @DisplayName("with a life left: shown dead, then back at the start with full health and spawn protection")
    void respawnsAtStart() {
        host.player.teleportTo(300, 200);
        kill();

        assertEquals(1, lives.lives().remaining());
        assertTrue(host.gameOvers.isEmpty(), "no game over while lives are left");
        assertEquals(1, host.stops, "the dead player stops moving");
        assertEquals(PlayerLives.RESPAWN_DELAY_SECONDS, host.pendingDelay, 1e-9);

        lives.AddDeathNotification(host.player);   // hits on the corpse while waiting
        assertEquals(1, lives.lives().remaining(), "a dead player loses only one life");

        host.runPending();
        assertEquals(40, host.player.getCharacterPosition().getX(), 1e-9, "back at the start");
        assertEquals(60, host.player.getCharacterPosition().getY(), 1e-9);
        assertEquals(host.player.getMaxHitPoints(), host.player.getHitPoints(), "full health");
        assertFalse(host.player.isShowingDeath());
        assertEquals(PlayerLives.RESPAWN_PROTECTION_SECONDS, host.protectedFor, 1e-9);
        assertEquals(1, host.resumes);
        assertEquals(List.of("Last life!"), host.messages);
    }

    @Test
    @DisplayName("on the last life the death goes to the game-over action")
    void gameOverOnTheLastLife() {
        kill();
        host.runPending();
        kill();

        assertEquals(0, lives.lives().remaining());
        assertEquals(List.of(host.player), host.gameOvers);
        assertNull(host.pending, "no respawn after the last life");
    }

    @Test
    @DisplayName("an extra life means one more respawn; a new game resets the lives")
    void extraLifeAndNewGame() {
        lives.lives().gain();
        kill();
        host.runPending();
        kill();
        host.runPending();
        assertTrue(host.gameOvers.isEmpty(), "three lives: two respawns");
        assertEquals(1, lives.lives().remaining());

        lives.startGame(host.player, 3);
        assertEquals(3, lives.lives().remaining());
        assertTrue(host.livesChanges > 0, "the HUD follows the lives");
    }
}
