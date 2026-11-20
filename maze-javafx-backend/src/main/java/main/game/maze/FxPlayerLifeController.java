package main.game.maze;

import main.game.maze.characters.PlayerCharacter;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.game.session.PlayerLives;
import main.game.maze.interfaces.IDeathSubscriber;

/**
 * JavaFX player lives (the shared {@link PlayerLives} rules, the same as libGDX): when the player
 * dies with lives left they are shown dead for {@link PlayerLives#RESPAWN_DELAY_SECONDS}, then come
 * back at the level start with full health and {@link PlayerLives#RESPAWN_PROTECTION_SECONDS} of
 * protection; on the last life the death goes to the game-over action as before.
 */
public final class FxPlayerLifeController implements IDeathSubscriber {

    /** What the controller needs from the game screen. */
    public interface Host {
        PlayerCharacter player();

        void stopPlayerMovement();

        void resumePlayerMovement();

        /** Damage protection after a respawn. */
        void protect(double seconds);

        void showMessage(String text);

        /** The lives count changed: refresh the HUD. */
        void livesChanged();

        /** Runs {@code action} on the JavaFX Application Thread after {@code seconds}. */
        void after(double seconds, Runnable action);

        /** Receives the final death (game over). */
        IDeathSubscriber gameOver();
    }

    private final PlayerLives lives;
    private final Host host;
    private double startX;
    private double startY;
    private boolean respawning;
    private boolean disposed;

    public FxPlayerLifeController(PlayerLives lives, Host host) {
        this.lives = lives;
        this.host = host;
    }

    /** New game: the difficulty's lives; the player respawns where they start now. */
    public synchronized void startGame(PlayerCharacter player, int startingLives) {
        lives.reset(startingLives);
        startX = player.getCharacterPosition().getX();
        startY = player.getCharacterPosition().getY();
        respawning = false;
        disposed = false;
        host.livesChanged();
    }

    /** Game torn down: a respawn that is still pending must not touch the disposed game. */
    public synchronized void dispose() {
        disposed = true;
        respawning = false;
    }

    public PlayerLives lives() {
        return lives;
    }

    @Override
    public void AddDeathNotification(ICanDie mortalEntity) {
        synchronized (this) {
            if (disposed || respawning) {
                return;   // torn down, or already dead and waiting to respawn (hits on the corpse)
            }
            if (lives.loseLife()) {
                respawning = true;
            }
        }
        host.livesChanged();
        if (!respawning) {
            host.gameOver().AddDeathNotification(mortalEntity);
            return;
        }
        host.stopPlayerMovement();
        host.after(PlayerLives.RESPAWN_DELAY_SECONDS, this::respawn);
    }

    private void respawn() {
        synchronized (this) {
            if (disposed) {
                return;
            }
        }
        PlayerCharacter player = host.player();
        if (player != null) {
            player.revive(startX, startY);
        }
        host.protect(PlayerLives.RESPAWN_PROTECTION_SECONDS);
        synchronized (this) {
            respawning = false;
        }
        host.resumePlayerMovement();
        int left = lives.remaining();
        host.showMessage(left == 1 ? "Last life!" : "Life lost - " + left + " lives left");
    }
}
