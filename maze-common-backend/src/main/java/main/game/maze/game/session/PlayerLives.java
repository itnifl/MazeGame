package main.game.maze.game.session;

import main.game.maze.difficulties.Difficulty;

/**
 * The player's lives in one game, the same in both frontends. The player starts with the
 * difficulty's {@code startingLives} (easy 3, normal 2, hard 1 in the shipped model); an extra-life
 * pickup adds one. When the player dies, {@link #loseLife()} says whether they respawn at the start
 * position (lives left) or the game is over (that was the last life). Thread-safe.
 */
public final class PlayerLives {

    /** After a respawn the player cannot be hurt for this long, so they are not killed again at once. */
    public static final double RESPAWN_PROTECTION_SECONDS = 5.0;
    /** How long the dead player is shown before respawning (the same as before the game-over screen). */
    public static final double RESPAWN_DELAY_SECONDS = 3.0;

    private int remaining;

    /** @param starting lives at the start (at least one) */
    public PlayerLives(int starting) {
        reset(starting);
    }

    /** Lives for a game at this difficulty: the model's {@code startingLives}; one when there is no difficulty. */
    public static int startingFor(Difficulty difficulty) {
        return difficulty == null ? 1 : Math.max(1, difficulty.getStartingLives());
    }

    /** New game. */
    public synchronized void reset(int starting) {
        remaining = Math.max(1, starting);
    }

    public synchronized int remaining() {
        return remaining;
    }

    /** Extra-life pickup. */
    public synchronized void gain() {
        remaining++;
    }

    /**
     * The player died: one life is gone.
     *
     * @return true when lives are left, so the player respawns; false when it was the last life
     */
    public synchronized boolean loseLife() {
        remaining = Math.max(0, remaining - 1);
        return remaining > 0;
    }
}
