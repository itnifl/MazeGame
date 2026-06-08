package main.game.maze.libgdx.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import main.game.maze.common.movement.ActivePathPoint;
import main.game.maze.dto.Score;
import main.game.maze.game.session.GameMode;
import main.game.maze.game.session.GameSession;
import main.game.maze.game.status.StatusMessageBus;
import main.game.maze.libgdx.controller.GdxWinOverlayController;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.helper.GdxDebugOverlayState;
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
 * Dedicated render orchestration for gameplay and overlays.
 */
public final class GdxGameRenderPipeline {

    public HudLayout render(RenderState state) {
        ScreenUtils.clear(0.07f, 0.07f, 0.12f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Overlays that need no active world are rendered before the guard.
        if (state.session().mode() == GameMode.HIGH_SCORES) {
            applyFullWindowGlViewport();
            drawHighScoresOverlayIfNeeded(state);
            return state.hudLayout();
        }

        if (state.maze() == null || state.player() == null || state.viewport() == null) {
            return state.hudLayout();
        }

        List<GdxGameWorldView.EnemyViewModel> enemyViewModels = new ArrayList<>(state.animatedEnemies().size());
        for (GdxEnemyRuntime enemy : state.animatedEnemies()) {
            Texture enemyTexture = state.enemyTextureLoader().apply(enemy.imagePath());
            String label = enemy.debugLabel(state.debugOverlayState().showBehaviourType(), state.debugOverlayState().showMovementType());
            enemyViewModels.add(new GdxGameWorldView.EnemyViewModel(
                    enemyTexture,
                    enemy.x(),
                    enemy.y(),
                    enemy.size(),
                    enemy.renderOpacity(),
                    enemy.infectious(),
                    enemy.infectionStrength(),
                    enemy.phase(),
                    label,
                    state.enemyPathProvider().apply(enemy)));
        }

        state.gameWorldView().render(new GdxGameWorldView.RenderContext(
                state.batch(),
                state.shapes(),
                state.font(),
                state.camera(),
                state.viewport(),
                state.maze(),
                state.player(),
                state.backgroundTexture(),
                state.goalTexture(),
                state.wallTexture(),
                state.playerTexture(),
                state.playerDeathTexture(),
                state.activeGoalX(),
                state.activeGoalY(),
                state.activeGoalSize(),
                state.wallThickness(),
                state.playerTintRed(),
                state.playerTintGreen(),
                state.playerTintBlue(),
                state.combatDead(),
                state.playerAliveScale(),
                state.playerDeadScale(),
                state.halfRatio(),
                state.enemyAnimationClock(),
                state.infectionEdgeLayers(),
                enemyViewModels,
                state.activePathPoints(),
                state.debugOverlayState().enemyPathSecondsRemaining(),
                state.showSpanningTreeInfo()));

        applyFullWindowGlViewport();
        HudLayout nextHudLayout = drawHud(state);
        drawHighScoresOverlayIfNeeded(state);
        drawWinOverlayIfNeeded(state);
        drawGameOverOverlayIfNeeded(state);
        drawInfectionOverlayIfNeeded(state);
        return nextHudLayout;
    }

    private HudLayout drawHud(RenderState state) {
        GdxHudView.HudMessageMode messageMode = switch (state.session().mode()) {
            case WON -> GdxHudView.HudMessageMode.WON;
            case GAME_OVER -> GdxHudView.HudMessageMode.GAME_OVER;
            default -> GdxHudView.HudMessageMode.STATUS;
        };
        String statusMessage = state.statusMessageBus().hasMessage() ? state.statusMessageBus().currentMessage() : "";

        return state.hudView().render(new GdxHudView.RenderContext(
                state.batch(),
                state.shapes(),
                state.font(),
                state.hudCamera(),
                state.topMargin(),
                state.scorePanelWidth(),
                state.scorePanelHeight(),
                state.bottomBarHeight(),
                state.hpBarHeight(),
                state.hpBarBottomY(),
                state.bottomRowY(),
                state.bottomRowHeight(),
                state.currentHpRatio(),
                state.commandPressOffsetY(),
                state.terminalPressOffsetY(),
                state.commandButtonPressedSeconds(),
                state.terminalButtonPressedSeconds(),
                state.showHintInfo(),
                state.pathHintExhausted(),
                state.pathHintRemainingSeconds(),
                state.showSpanningTreeInfo(),
                state.currentScore(),
                messageMode,
                statusMessage,
                state.terminalActive(),
                state.terminalBufferText(),
                state.commandsOverlayVisible()));
    }

    private void drawHighScoresOverlayIfNeeded(RenderState state) {
        if (state.session().mode() != GameMode.HIGH_SCORES) {
            return;
        }
        state.highScoresOverlayView().render(
                new GdxHighScoresOverlayView.RenderContext(state.batch(), state.shapes(), state.font(), state.hudCamera()),
                state.highScoreRows());
    }

    private void drawWinOverlayIfNeeded(RenderState state) {
        if (state.session().mode() != GameMode.WON) {
            return;
        }
        GdxWinOverlayView.WinButtons winButtons = state.winOverlayView().render(
                new GdxWinOverlayView.WinOverlayContext(
                        state.batch(),
                        state.shapes(),
                        state.font(),
                        state.glyphLayout(),
                        state.hudCamera(),
                        state.winBackgroundTexture(),
                        state.session().winScoreSaved(),
                        state.winOverlayController().winScoreStatusText(),
                        state.winOverlayController().winNameInputText(),
                        state.currentScore()));
        state.winOverlayController().onOverlayRendered(winButtons);
    }

    private void drawGameOverOverlayIfNeeded(RenderState state) {
        if (state.session().mode() != GameMode.GAME_OVER) {
            return;
        }
        state.gameOverOverlayView().render(
                new GdxGameOverOverlayView.RenderContext(
                        state.batch(),
                        state.shapes(),
                        state.font(),
                        state.glyphLayout(),
                        state.hudCamera(),
                        state.gameOverBackgroundTexture(),
                        state.currentScore()));
    }

    private void drawInfectionOverlayIfNeeded(RenderState state) {
        if (state.session().mode() != GameMode.PLAYING || !state.infectionWarningVisible()) {
            return;
        }
        state.infectionOverlayView().render(
                new GdxInfectionOverlayView.InfectionWarningContext(
                        state.batch(),
                        state.shapes(),
                        state.font(),
                        state.glyphLayout(),
                        state.hudCamera(),
                        state.enemyAnimationClock(),
                        state.infectionPulseSpeed(),
                        state.infectionTriangleWidth(),
                        state.infectionTriangleHeight(),
                        state.infectionGlowLayers(),
                        state.infectionWarningText()));
    }

    private static void applyFullWindowGlViewport() {
        Gdx.gl.glViewport(0, 0,
                Math.max(1, Gdx.graphics.getBackBufferWidth()),
                Math.max(1, Gdx.graphics.getBackBufferHeight()));
    }

    public record RenderState(
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
            GdxWinOverlayController winOverlayController,
            Function<String, Texture> enemyTextureLoader,
            Function<GdxEnemyRuntime, List<ActivePathPoint>> enemyPathProvider,
            HudLayout hudLayout) {
    }
}
