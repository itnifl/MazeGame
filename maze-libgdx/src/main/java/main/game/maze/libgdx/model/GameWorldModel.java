package main.game.maze.libgdx.model;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.dto.Score;
import main.game.maze.game.score.PathHintBudget;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;

/**
 * Mutable gameplay runtime state boundary for libGDX play sessions.
 */
public final class GameWorldModel {

    private MazeArena maze;
    private PlayerState player;
    private RuntimeVisualModel runtimeModel;
    private float activePlayerSpeed;
    private float activeGoalSize;
    private float activeGoalX;
    private float activeGoalY;
    private float currentHpRatio = 1f;
    private float playerTintRed = 1f;
    private float playerTintGreen = 1f;
    private float playerTintBlue = 1f;
    private boolean infectionWarningVisible;
    private boolean playedWinSound;
    private boolean playedGameOverSound;
    private boolean deathSequenceStarted;
    private float deathDisplayRemainingSeconds;
    private float pathPenaltyPoints;
    private PathHintBudget pathHintBudget = new PathHintBudget(PathHintBudget.EASY_SECONDS);

    private final List<GdxEnemyRuntime> animatedEnemies = new ArrayList<>();
    private final List<Point2D> activePathPoints = new ArrayList<>();
    private final List<GdxEnemyRuntime.ProjectileVisual> enemyProjectiles = new ArrayList<>();
    private final List<GdxEnemyRuntime.BeamVisual> enemyBeams = new ArrayList<>();
    private final List<Score> highScoreRows = new ArrayList<>();

    public MazeArena maze() {
        return maze;
    }

    public void setMaze(MazeArena maze) {
        this.maze = maze;
    }

    public PlayerState player() {
        return player;
    }

    public void setPlayer(PlayerState player) {
        this.player = player;
    }

    public RuntimeVisualModel runtimeModel() {
        return runtimeModel;
    }

    public void setRuntimeModel(RuntimeVisualModel runtimeModel) {
        this.runtimeModel = runtimeModel;
    }

    public float activePlayerSpeed() {
        return activePlayerSpeed;
    }

    public void setActivePlayerSpeed(float activePlayerSpeed) {
        this.activePlayerSpeed = activePlayerSpeed;
    }

    public float activeGoalSize() {
        return activeGoalSize;
    }

    public void setActiveGoalSize(float activeGoalSize) {
        this.activeGoalSize = activeGoalSize;
    }

    public float activeGoalX() {
        return activeGoalX;
    }

    public void setActiveGoalX(float activeGoalX) {
        this.activeGoalX = activeGoalX;
    }

    public float activeGoalY() {
        return activeGoalY;
    }

    public void setActiveGoalY(float activeGoalY) {
        this.activeGoalY = activeGoalY;
    }

    public float currentHpRatio() {
        return currentHpRatio;
    }

    public void setCurrentHpRatio(float currentHpRatio) {
        this.currentHpRatio = currentHpRatio;
    }

    public float playerTintRed() {
        return playerTintRed;
    }

    public void setPlayerTintRed(float playerTintRed) {
        this.playerTintRed = playerTintRed;
    }

    public float playerTintGreen() {
        return playerTintGreen;
    }

    public void setPlayerTintGreen(float playerTintGreen) {
        this.playerTintGreen = playerTintGreen;
    }

    public float playerTintBlue() {
        return playerTintBlue;
    }

    public void setPlayerTintBlue(float playerTintBlue) {
        this.playerTintBlue = playerTintBlue;
    }

    public boolean infectionWarningVisible() {
        return infectionWarningVisible;
    }

    public void setInfectionWarningVisible(boolean infectionWarningVisible) {
        this.infectionWarningVisible = infectionWarningVisible;
    }

    public boolean playedWinSound() {
        return playedWinSound;
    }

    public void setPlayedWinSound(boolean playedWinSound) {
        this.playedWinSound = playedWinSound;
    }

    public boolean playedGameOverSound() {
        return playedGameOverSound;
    }

    public void setPlayedGameOverSound(boolean playedGameOverSound) {
        this.playedGameOverSound = playedGameOverSound;
    }

    public boolean deathSequenceStarted() {
        return deathSequenceStarted;
    }

    public void setDeathSequenceStarted(boolean deathSequenceStarted) {
        this.deathSequenceStarted = deathSequenceStarted;
    }

    public float deathDisplayRemainingSeconds() {
        return deathDisplayRemainingSeconds;
    }

    public void setDeathDisplayRemainingSeconds(float deathDisplayRemainingSeconds) {
        this.deathDisplayRemainingSeconds = deathDisplayRemainingSeconds;
    }

    public float pathPenaltyPoints() {
        return pathPenaltyPoints;
    }

    public void setPathPenaltyPoints(float pathPenaltyPoints) {
        this.pathPenaltyPoints = pathPenaltyPoints;
    }

    public PathHintBudget pathHintBudget() {
        return pathHintBudget;
    }

    public void setPathHintBudget(PathHintBudget pathHintBudget) {
        this.pathHintBudget = pathHintBudget;
    }

    public List<GdxEnemyRuntime> animatedEnemies() {
        return animatedEnemies;
    }

    public List<Point2D> activePathPoints() {
        return activePathPoints;
    }

    public List<GdxEnemyRuntime.ProjectileVisual> enemyProjectiles() {
        return enemyProjectiles;
    }

    public List<GdxEnemyRuntime.BeamVisual> enemyBeams() {
        return enemyBeams;
    }

    public List<Score> highScoreRows() {
        return highScoreRows;
    }
}
