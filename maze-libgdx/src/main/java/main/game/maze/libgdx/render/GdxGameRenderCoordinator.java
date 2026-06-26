package main.game.maze.libgdx.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.List;
import java.util.function.Function;
import main.game.maze.common.movement.ActivePathPoint;
import main.game.maze.dto.Score;
import main.game.maze.game.score.ScoringEngine.ScoreBreakdown;
import main.game.maze.game.session.GameSession;
import main.game.maze.game.status.StatusMessageBus;
import main.game.maze.libgdx.controller.GdxHudInteractionStateController;
import main.game.maze.libgdx.controller.GdxTerminalController;
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
 * Coordinates render-state assembly so the gameplay controller stays focused on lifecycle flow.
 */
public final class GdxGameRenderCoordinator {

    private final GdxGameRenderPipeline renderPipeline = new GdxGameRenderPipeline();
    private final GdxGameRenderStateAssembler renderStateAssembler = new GdxGameRenderStateAssembler();
    private final GameWorldModel worldModel;
    private final List<GdxEnemyRuntime> animatedEnemies;
    private final List<Point2D> activePathPoints;
    private final List<Score> highScoreRows;
    private final GameSession session;
    private final GdxDebugOverlayState debugOverlayState;
    private final StatusMessageBus statusMessageBus;
    private final GdxHudInteractionStateController hudInteractionState;
    private final GdxTerminalController terminalController;
    private final GdxGameWorldView gameWorldView;
    private final GdxHudView hudView;
    private final GdxHighScoresOverlayView highScoresOverlayView;
    private final GdxWinOverlayView winOverlayView;
    private final GdxGameOverOverlayView gameOverOverlayView;
    private final GdxInfectionOverlayView infectionOverlayView;
    private final GdxGameOverOverlayController gameOverOverlayController;
    private final GdxWinOverlayController winOverlayController;
    private final Function<String, Texture> enemyTextureLoader;
    private final Function<GdxEnemyRuntime, List<ActivePathPoint>> enemyPathProvider;
    private final RenderConstants constants;

    public GdxGameRenderCoordinator(
            GameWorldModel worldModel,
            List<GdxEnemyRuntime> animatedEnemies,
            List<Point2D> activePathPoints,
            List<Score> highScoreRows,
            GameSession session,
            GdxDebugOverlayState debugOverlayState,
            StatusMessageBus statusMessageBus,
            GdxHudInteractionStateController hudInteractionState,
            GdxTerminalController terminalController,
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
            RenderConstants constants) {
        this.worldModel = worldModel;
        this.animatedEnemies = animatedEnemies;
        this.activePathPoints = activePathPoints;
        this.highScoreRows = highScoreRows;
        this.session = session;
        this.debugOverlayState = debugOverlayState;
        this.statusMessageBus = statusMessageBus;
        this.hudInteractionState = hudInteractionState;
        this.terminalController = terminalController;
        this.gameWorldView = gameWorldView;
        this.hudView = hudView;
        this.highScoresOverlayView = highScoresOverlayView;
        this.winOverlayView = winOverlayView;
        this.gameOverOverlayView = gameOverOverlayView;
        this.infectionOverlayView = infectionOverlayView;
        this.gameOverOverlayController = gameOverOverlayController;
        this.winOverlayController = winOverlayController;
        this.enemyTextureLoader = enemyTextureLoader;
        this.enemyPathProvider = enemyPathProvider;
        this.constants = constants;
    }

    public HudLayout render(FrameInput input) {
        return renderPipeline.render(renderStateAssembler.assemble(new GdxGameRenderStateAssembler.AssemblyInput(
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
                worldModel.activeGoalX(),
                worldModel.activeGoalY(),
                worldModel.activeGoalSize(),
                constants.wallThickness(),
                worldModel.playerTintRed(),
                worldModel.playerTintGreen(),
                worldModel.playerTintBlue(),
                input.combatDead(),
                constants.playerAliveScale(),
                constants.playerDeadScale(),
                constants.halfRatio(),
                input.enemyAnimationClock(),
                constants.infectionEdgeLayers(),
                constants.infectionPulseSpeed(),
                constants.infectionTriangleWidth(),
                constants.infectionTriangleHeight(),
                constants.infectionGlowLayers(),
                constants.infectionWarningText(),
                animatedEnemies,
                worldModel.dyingEnemies(),
                activePathPoints,
                worldModel.enemyProjectiles(),
                worldModel.enemyBeams(),
                worldModel.enemyImpacts(),
                shakeOffsetX(input.enemyAnimationClock()),
                shakeOffsetY(input.enemyAnimationClock()),
                session,
                debugOverlayState,
                statusMessageBus,
                constants.topMargin(),
                constants.scorePanelWidth(),
                constants.scorePanelHeight(),
                constants.bottomBarHeight(),
                constants.hpBarHeight(),
                input.hpBarBottomY(),
                input.bottomRowY(),
                input.bottomRowHeight(),
                worldModel.currentHpRatio(),
                hudInteractionState.commandPressOffsetY(),
                hudInteractionState.terminalPressOffsetY(),
                hudInteractionState.commandButtonPressedSeconds(),
                hudInteractionState.terminalButtonPressedSeconds(),
                input.showHintInfo(),
                worldModel.pathHintBudget().exhausted(),
                input.pathHintRemainingSeconds(),
                input.showSpanningTreeInfo(),
                input.currentScore(),
                input.playerBombsRemaining(),
                input.scoreBreakdown(),
                terminalController.isActive(),
                terminalController.bufferText(),
                hudInteractionState.commandsOverlayVisible(),
                worldModel.infectionWarningVisible(),
                highScoreRows,
                gameWorldView,
                hudView,
                highScoresOverlayView,
                winOverlayView,
                gameOverOverlayView,
                infectionOverlayView,
                gameOverOverlayController,
                winOverlayController,
                enemyTextureLoader,
                enemyPathProvider,
                input.hudLayout())));
    }

        private float shakeOffsetX(float animationClock) {
                float remaining = worldModel.explosionShakeRemainingSeconds();
                float intensity = worldModel.explosionShakeIntensity();
                if (remaining <= 0f || intensity <= 0f) {
                        return 0f;
                }
                float envelope = Math.min(1f, remaining / 0.20f);
                return (float) Math.sin(animationClock * 90f) * intensity * envelope;
        }

        private float shakeOffsetY(float animationClock) {
                float remaining = worldModel.explosionShakeRemainingSeconds();
                float intensity = worldModel.explosionShakeIntensity();
                if (remaining <= 0f || intensity <= 0f) {
                        return 0f;
                }
                float envelope = Math.min(1f, remaining / 0.20f);
                return (float) Math.cos(animationClock * 73f) * intensity * 0.65f * envelope;
        }

    public record FrameInput(
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
            boolean combatDead,
            float enemyAnimationClock,
            float hpBarBottomY,
            float bottomRowY,
            float bottomRowHeight,
            boolean showHintInfo,
            float pathHintRemainingSeconds,
            boolean showSpanningTreeInfo,
            int currentScore,
            int playerBombsRemaining,
            ScoreBreakdown scoreBreakdown,
            HudLayout hudLayout) {
    }

    public record RenderConstants(
            float wallThickness,
            float playerAliveScale,
            float playerDeadScale,
            float halfRatio,
            int infectionEdgeLayers,
            float infectionPulseSpeed,
            float infectionTriangleWidth,
            float infectionTriangleHeight,
            int infectionGlowLayers,
            String infectionWarningText,
            float topMargin,
            float scorePanelWidth,
            float scorePanelHeight,
            float bottomBarHeight,
            float hpBarHeight) {
    }
}
