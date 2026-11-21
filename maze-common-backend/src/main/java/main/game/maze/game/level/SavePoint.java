package main.game.maze.game.level;

import main.game.maze.game.items.ArsenalSnapshot;

/**
 * The state a level is entered with, saved when the level before it is completed: which level, and the weapons
 * the player carries into it (null when that level starts from scratch), and the score carried forward: the score the
 * level before it ended with, which the next level adds its own starting score to. Restarting the level restores exactly this.
 */
public record SavePoint(int level, ArsenalSnapshot arsenal, int score) {

    /** A savepoint with no score carried. */
    public SavePoint(int level, ArsenalSnapshot arsenal) {
        this(level, arsenal, 0);
    }

    public SavePoint {
        if (level < 2) {
            throw new IllegalArgumentException("a savepoint is made when a level is completed, so it is for level 2 or later: " + level);
        }
    }
}
