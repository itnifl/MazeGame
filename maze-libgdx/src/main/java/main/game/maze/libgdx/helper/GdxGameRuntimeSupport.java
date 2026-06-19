package main.game.maze.libgdx.helper;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.game.score.PathHintBudget;
import main.game.maze.game.score.ScoringEngine;
import main.game.maze.game.session.GameMode;
import main.game.maze.game.session.GameSession;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.RealMaze;
import main.game.maze.mazeworld.service.MazeNavigationGraphService;

/**
 * Shared runtime calculations that keep the controller focused on orchestration.
 */
public final class GdxGameRuntimeSupport {

    private GdxGameRuntimeSupport() {
    }

    public record PathPenaltyOutcome(boolean showHintInfo, PathHintBudget pathHintBudget,
                                     float pathPenaltyPoints, boolean exhaustedNotified) {
    }

    public static void updatePathHint(boolean showHintInfo, MazeArena maze, PlayerState player,
                                      GameWorldModel worldModel, List<Point2D> activePathPoints) {
        activePathPoints.clear();
        if (!showHintInfo || !(maze instanceof RealMaze realMaze) || player == null) {
            return;
        }
        if (realMaze.navigationGraph() == null) {
            return;
        }

        Point2D start = new Point2D(player.x(), maze.heightPx() - player.y());
        Point2D goal = new Point2D(worldModel.activeGoalX(), maze.heightPx() - worldModel.activeGoalY());
        var path = MazeNavigationGraphService.findPath(realMaze.navigationGraph(), start, goal);
        if (path != null && path.size() > 1) {
            activePathPoints.addAll(path);
        }
    }

    public static PathPenaltyOutcome applyPathPenalty(GameMode mode, boolean showHintInfo,
                                                      PathHintBudget pathHintBudget, float pathPenaltyPoints,
                                                      float dt, float routeHintPenaltyPerSecond) {
        if (mode != GameMode.PLAYING) {
            return new PathPenaltyOutcome(showHintInfo, pathHintBudget, pathPenaltyPoints, false);
        }
        if (pathHintBudget.exhausted()) {
            return new PathPenaltyOutcome(false, pathHintBudget, pathPenaltyPoints, showHintInfo);
        }
        if (!showHintInfo) {
            return new PathPenaltyOutcome(false, pathHintBudget, pathPenaltyPoints, false);
        }

        float consumed = pathHintBudget.consume(dt);
        if (consumed <= 0f) {
            return new PathPenaltyOutcome(false, pathHintBudget, pathPenaltyPoints, true);
        }

        float nextPenalty = pathPenaltyPoints + consumed * routeHintPenaltyPerSecond;
        boolean exhaustedNow = pathHintBudget.exhausted();
        return new PathPenaltyOutcome(!exhaustedNow, pathHintBudget, nextPenalty, exhaustedNow);
    }

    public static List<EnemySpawn> currentEnemyContacts(List<GdxEnemyRuntime> animatedEnemies) {
        if (animatedEnemies.isEmpty()) {
            return List.of();
        }
        List<EnemySpawn> contacts = new ArrayList<>(animatedEnemies.size());
        for (GdxEnemyRuntime enemy : animatedEnemies) {
            contacts.add(enemy.contactSnapshot());
        }
        return contacts;
    }

    public static int currentScore(ScoringEngine scoringEngine, GameSession session, GameWorldModel worldModel) {
        return GdxScoreSupport.currentScore(scoringEngine, session, worldModel.pathPenaltyPoints());
    }

    /**
     * End-screen score for the GAME_OVER or WON overlay.
     * Delegates to {@link GdxScoreSupport#endScreenScore} which applies
     * the 5 000-point death penalty (when dead) and damage * 10 penalty.
     */
    public static int endScreenScore(
            ScoringEngine scoringEngine,
            GameSession session,
            GameWorldModel worldModel,
            int maxHp,
            int currentHp,
            boolean won) {
        return GdxScoreSupport.endScreenScore(
                scoringEngine, session, worldModel.pathPenaltyPoints(), maxHp, currentHp, won);
    }
}
