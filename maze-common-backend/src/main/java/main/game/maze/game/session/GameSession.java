package main.game.maze.game.session;

/**
 * Small mutable session aggregate for backend-neutral game state.
 */
public final class GameSession {

    private GameMode mode = GameMode.START_MENU;
    private boolean pausedFromGame;
    private boolean loadingPending;
    private boolean highScoresReturnToStartMenu;
    private boolean winScoreSaved;
    private boolean highScoresReturnToWin;
    private int baseScore;
    private int moveCount;

    public GameMode mode() {
        return mode;
    }

    public void setMode(GameMode mode) {
        this.mode = mode == null ? GameMode.START_MENU : mode;
    }

    public boolean pausedFromGame() {
        return pausedFromGame;
    }

    public void setPausedFromGame(boolean pausedFromGame) {
        this.pausedFromGame = pausedFromGame;
    }

    public boolean loadingPending() {
        return loadingPending;
    }

    public void setLoadingPending(boolean loadingPending) {
        this.loadingPending = loadingPending;
    }

    public boolean highScoresReturnToStartMenu() {
        return highScoresReturnToStartMenu;
    }

    public void setHighScoresReturnToStartMenu(boolean highScoresReturnToStartMenu) {
        this.highScoresReturnToStartMenu = highScoresReturnToStartMenu;
    }

    /** True when the high scores were opened from the win screen of a level that has a next level. */
    public boolean highScoresReturnToWin() {
        return highScoresReturnToWin;
    }

    public void setHighScoresReturnToWin(boolean highScoresReturnToWin) {
        this.highScoresReturnToWin = highScoresReturnToWin;
    }

    public boolean winScoreSaved() {
        return winScoreSaved;
    }

    public void setWinScoreSaved(boolean winScoreSaved) {
        this.winScoreSaved = winScoreSaved;
    }

    public int baseScore() {
        return baseScore;
    }

    public void setBaseScore(int baseScore) {
        this.baseScore = Math.max(0, baseScore);
    }

    public int moveCount() {
        return moveCount;
    }

    public void incrementMoveCount() {
        moveCount++;
    }

    public void resetMoveCount() {
        moveCount = 0;
    }
}
