package main.game.maze.libgdx.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.List;
import java.util.function.Function;
import main.game.maze.common.movement.ActivePathPoint;
import main.game.maze.dto.Score;
import main.game.maze.game.score.ScoringEngine.ScoreBreakdown;
import main.game.maze.game.session.GameSession;
import main.game.maze.game.status.StatusMessageBus;
import main.game.maze.libgdx.controller.GdxGameOverOverlayController;
import main.game.maze.libgdx.controller.GdxWinOverlayController;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.helper.GdxDebugOverlayState;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.libgdx.view.GdxGameOverOverlayView;
import main.game.maze.libgdx.view.GdxGameWorldView;
import main.game.maze.libgdx.view.GdxHighScoresOverlayView;
import main.game.maze.libgdx.view.GdxHudView;
import main.game.maze.libgdx.view.GdxInfectionOverlayView;
import main.game.maze.libgdx.view.GdxWinOverlayView;
import main.game.maze.libgdx.view.layout.HudLayout;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;

/**
 * Builds the render pipeline input in one place so the controller stays thin.
 */
public final class GdxGameRenderStateAssembler {

    public GdxGameRenderPipeline.RenderState assemble(AssemblyInput input) {
        return new GdxGameRenderPipeline.RenderState(
                input.batch(),
                input.shapes(),
                input.font(),
                input.glyphLayout(),
                input.camera(),
                input.hudCamera(),
                input.viewport(),
                input.maze(),
                input.player(),
                input.backgroundTexture(),
                input.goalTexture(),
                input.wallTexture(),
                input.playerTexture(),
                input.playerDeathTexture(),
                input.winBackgroundTexture(),
                input.gameOverBackgroundTexture(),
                input.activeGoalX(),
                input.activeGoalY(),
                input.activeGoalSize(),
                input.wallThickness(),
                input.playerTintRed(),
                input.playerTintGreen(),
                input.playerTintBlue(),
                input.combatDead(),
                input.playerAliveScale(),
                input.playerDeadScale(),
                input.halfRatio(),
                input.enemyAnimationClock(),
                input.infectionEdgeLayers(),
                input.infectionPulseSpeed(),
                input.infectionTriangleWidth(),
                input.infectionTriangleHeight(),
                input.infectionGlowLayers(),
                input.infectionWarningText(),
                input.animatedEnemies(),
                input.activePathPoints(),
                input.session(),
                input.debugOverlayState(),
                input.statusMessageBus(),
                input.topMargin(),
                input.scorePanelWidth(),
                input.scorePanelHeight(),
                input.bottomBarHeight(),
                input.hpBarHeight(),
                input.hpBarBottomY(),
                input.bottomRowY(),
                input.bottomRowHeight(),
                input.currentHpRatio(),
                input.commandPressOffsetY(),
                input.terminalPressOffsetY(),
                input.commandButtonPressedSeconds(),
                input.terminalButtonPressedSeconds(),
                input.showHintInfo(),
                input.pathHintExhausted(),
                input.pathHintRemainingSeconds(),
                input.showSpanningTreeInfo(),
                input.currentScore(),
                input.scoreBreakdown(),
                input.terminalActive(),
                input.terminalBufferText(),
                input.commandsOverlayVisible(),
                input.infectionWarningVisible(),
                input.highScoreRows(),
                input.gameWorldView(),
                input.hudView(),
                input.highScoresOverlayView(),
                input.winOverlayView(),
                input.gameOverOverlayView(),
                input.infectionOverlayView(),
                input.gameOverOverlayController(),
                input.winOverlayController(),
                input.enemyTextureLoader(),
                input.enemyPathProvider(),
                input.hudLayout());
    }

    public record AssemblyInput(
            SpriteBatch batch,
            ShapeRenderer shapes,
            BitmapFont font,
            GlyphLayout glyphLayout,
            OrthographicCamera camera,
            OrthographicCamera hudCamera,
            Viewport viewport,
            MazeArena maze,
            PlayerState player,
            Texture backgroundTexture,
            Texture goalTexture,
            Texture wallTexture,
            Texture playerTexture,
            Texture playerDeathTexture,
            Texture winBackgroundTexture,
            Texture gameOverBackgroundTexture,
            float activeGoalX,
            float activeGoalY,
            float activeGoalSize,
            float wallThickness,
            float playerTintRed,
            float playerTintGreen,
            float playerTintBlue,
            boolean combatDead,
            float playerAliveScale,
            float playerDeadScale,
            float halfRatio,
            float enemyAnimationClock,
            int infectionEdgeLayers,
            float infectionPulseSpeed,
            float infectionTriangleWidth,
            float infectionTriangleHeight,
            int infectionGlowLayers,
            String infectionWarningText,
            List<GdxEnemyRuntime> animatedEnemies,
            List<Point2D> activePathPoints,
            GameSession session,
            GdxDebugOverlayState debugOverlayState,
            StatusMessageBus statusMessageBus,
            float topMargin,
            float scorePanelWidth,
            float scorePanelHeight,
            float bottomBarHeight,
            float hpBarHeight,
            float hpBarBottomY,
            float bottomRowY,
            float bottomRowHeight,
            float currentHpRatio,
            float commandPressOffsetY,
            float terminalPressOffsetY,
            float commandButtonPressedSeconds,
            float terminalButtonPressedSeconds,
            boolean showHintInfo,
            boolean pathHintExhausted,
            float pathHintRemainingSeconds,
            boolean showSpanningTreeInfo,
            int currentScore,
            ScoreBreakdown scoreBreakdown,
            boolean terminalActive,
            String terminalBufferText,
            boolean commandsOverlayVisible,
            boolean infectionWarningVisible,
            List<Score> highScoreRows,
            GdxGameWorldView gameWorldView,
            GdxHudView hudView,
            GdxHighScoresOverlayView highScoresOverlayView,
            GdxWinOverlayView winOverlayView,
            GdxGameOverOverlayView gameOverOverlayView,
            GdxInfectionOverlayView infectionOverlayView,
            GdxGameOverOverlayController gameOverOverlayController,
            GdxWinOverlayController winOverlayController,
            Function<String, Texture> enemyTextureLoader,
            Function<GdxEnemyRuntime, List<ActivePathPoint>> enemyPathProvider,
            HudLayout hudLayout) {
    }
}
