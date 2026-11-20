package main.game.maze.game.score;

/**
 * Identifies players, so every damaging action can say who did it (kill credit, scores).
 * Single-player games use {@link #LOCAL_PLAYER}; a multiplayer game gives each player its own id.
 */
public final class PlayerIds {

    /** The player at this keyboard. */
    public static final String LOCAL_PLAYER = "player-1";

    private PlayerIds() {
    }
}
