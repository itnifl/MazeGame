package main.game.maze.game.status;

import main.game.maze.game.level.LevelProgress;

/**
 * The introduction of the last level, whichever level that is: before it starts the words FINAL LEVEL slam onto the
 * screen as a {@link TitleCard} and a dark voice says them. Shared by both frontends.
 */
public final class FinalLevelIntro {

    /** What the title says. */
    public static final String TEXT = "FINAL LEVEL";
    /** What the voice says: a recording of the words, made deep and dark by {@code tools/sound-gen}. */
    public static final String VOICE = "/main/game/maze/sounds/final-level.wav";
    /** The win screen of the last level says this instead of "YOU WIN". */
    public static final String WIN_TEXT = "GAME WON";
    /** ...and a dark voice says this. */
    public static final String WIN_VOICE = "/main/game/maze/sounds/ultimate-victory.wav";
    public static final String ORDINARY_WIN_TEXT = "YOU WIN";

    /** What the win screen says: the game is won when the last level is won. */
    public static String winText(boolean lastLevel) {
        return lastLevel ? WIN_TEXT : ORDINARY_WIN_TEXT;
    }

    /** How long the title stays: a little longer than the voice. */
    public static final float SECONDS = 2.8f;

    private FinalLevelIntro() {
    }

    /** Whether the level the player is about to start is the last one (and not the only one). */
    public static boolean applies(LevelProgress progress) {
        return progress != null && progress.number() > 1 && !progress.hasNext();
    }
}
