package main.game.maze.libgdx.helper;

import main.game.maze.game.score.HighScoreRepository;

/**
 * Shared helper for win screen name input and save handling.
 */
public final class GdxWinScoreSupport {

    private GdxWinScoreSupport() {
    }

    public static boolean appendCharacter(StringBuilder input, char character, int maxChars) {
        if (input.length() >= maxChars) {
            return true;
        }
        if (Character.isLetterOrDigit(character) || character == ' ' || character == '_' || character == '-') {
            input.append(character);
            return true;
        }
        return false;
    }

    public static void backspace(StringBuilder input) {
        if (input.length() > 0) {
            input.deleteCharAt(input.length() - 1);
        }
    }

    public static String playerName(StringBuilder input) {
        return input.toString().trim();
    }

    public static boolean hasPlayerName(StringBuilder input) {
        return !playerName(input).isEmpty();
    }

    public static boolean saveScore(HighScoreRepository repository, StringBuilder input, int score) {
        return GdxScoreSupport.saveHighScore(repository, playerName(input), score);
    }
}
