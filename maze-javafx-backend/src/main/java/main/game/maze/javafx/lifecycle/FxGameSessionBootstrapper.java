package main.game.maze.javafx.lifecycle;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import main.game.maze.App;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.config.model.PlayerConfig;
import main.game.maze.config.service.XmiRulesLoader;
import main.game.maze.constants.PlayerConstants;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.javafx.render.FxMazeCanvasRenderer;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.runtime.opponents.EnemyRegistrar;
import main.game.maze.runtime.opponents.OpponentRuntimeFactory;

import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Encapsulates the arena-build / player-create / canvas-setup phase that was
 * previously embedded in {@code GameController.setupGame()}.
 *
 * <p>Responsibilities: board sizing, background loading, world generation, player
 * construction, canvas creation, and opponent instantiation.  Produces a
 * {@link SessionResult} record that the caller unwraps to obtain the fully-built
 * objects.  All scene-graph mutations (canvas add) are performed here so the
 * controller's {@code setupGame()} only needs to wire the resulting objects together.</p>
 *
 * <p>Old canvases must be removed from {@code gameBoard} by the caller before
 * invoking {@link #setup}.</p>
 */
public final class FxGameSessionBootstrapper {

    private static final Logger LOGGER = Logger.getLogger(FxGameSessionBootstrapper.class.getName());

    /**
     * All objects produced by a single session setup.
     *
     * @param playerCharacter the freshly constructed player
     * @param maze            the generated maze world
     * @param mazeCanvas      canvas holding the static maze walls (index 0 in gameBoard)
     * @param pathCanvas      transparent canvas for path-hint overlays
     * @param treeCanvas      transparent canvas for spanning-tree overlay
     * @param playerConfig    resolved player configuration (speed, health, etc.)
     */
    public record SessionResult(
            PlayerCharacter playerCharacter,
            GameMazeWorld   maze,
            Canvas          mazeCanvas,
            Canvas          pathCanvas,
            Canvas          treeCanvas,
            PlayerConfig    playerConfig) {}

    private static final BiConsumer<EnemyRegistrar, Difficulty> DEFAULT_SPAWNER =
            (registrar, difficulty) -> {
                if (difficulty != null) OpponentRuntimeFactory.instantiateFromModel(registrar, difficulty);
                else                    OpponentRuntimeFactory.instantiateFromModel(registrar);
            };

    private final MazeVisualStyleConfig            visualStyle;
    private final FxMazeCanvasRenderer             mazeCanvasRenderer;
    private final EnemyRegistrar                   enemyRegistrar;
    private final BiConsumer<EnemyRegistrar, Difficulty> opponentSpawner;

    /** Production constructor — uses the real {@link OpponentRuntimeFactory}. */
    public FxGameSessionBootstrapper(
            MazeVisualStyleConfig visualStyle,
            FxMazeCanvasRenderer  mazeCanvasRenderer,
            EnemyRegistrar        enemyRegistrar) {
        this(visualStyle, mazeCanvasRenderer, enemyRegistrar, DEFAULT_SPAWNER);
    }

    /**
     * Injection constructor — allows tests (or other callers) to supply a custom
     * spawner, e.g., a no-op that avoids loading the EMF/XMI opponent model.
     */
    public FxGameSessionBootstrapper(
            MazeVisualStyleConfig            visualStyle,
            FxMazeCanvasRenderer             mazeCanvasRenderer,
            EnemyRegistrar                   enemyRegistrar,
            BiConsumer<EnemyRegistrar, Difficulty> opponentSpawner) {
        this.visualStyle        = visualStyle;
        this.mazeCanvasRenderer = mazeCanvasRenderer;
        this.enemyRegistrar     = enemyRegistrar;
        this.opponentSpawner    = opponentSpawner;
    }

    /**
     * Builds a complete game session on the supplied {@code gameBoard}.
     * The caller is responsible for removing stale canvases from {@code gameBoard}
     * before calling this method.
     */
    public SessionResult setup(
            Pane        gameBoard,
            ImageView   playerImageView,
            ProgressBar hpBar,
            Difficulty  startDifficulty) {

        gameBoard.setPrefSize(App.getBoardMaxX(), App.getBoardMaxY());
        gameBoard.setMinSize(App.getBoardMaxX(), App.getBoardMaxY());
        gameBoard.setMaxSize(App.getBoardMaxX(), App.getBoardMaxY());

        updateBoardBackground(gameBoard, startDifficulty);

        GameMazeWorld maze      = GameMazeWorld.GetWorld(App.getBoardMaxX(), App.getBoardMaxY());
        PlayerConfig  config    = loadPlayerConfig();

        setPlayerBaseImage(playerImageView, config.imageBase());

        PlayerCharacter player = new PlayerCharacter(
                playerImageView,
                playerImageView.getLayoutX(),
                playerImageView.getLayoutY(),
                hpBar,
                config);
        player.setHitPoints(config.health());

        // Canvases — order matters for rendering (maze at index 0, overlays above)
        Canvas mazeCanvas;
        try {
            mazeCanvas = mazeCanvasRenderer.drawCanvas(maze.getMazeVectors());
        } catch (ExceptionInInitializerError | NoClassDefFoundError e) {
            LOGGER.log(Level.SEVERE,
                    "WallRegistry static initializer failed — main.game.maze.walls may be missing from classpath. "
                    + "Falling back to blank wall canvas.", e);
            mazeCanvas = new Canvas(App.getBoardMaxX(), App.getBoardMaxY());
        }
        gameBoard.getChildren().add(0, mazeCanvas);

        Canvas pathCanvas = new Canvas(App.getBoardMaxX(), App.getBoardMaxY());
        pathCanvas.setMouseTransparent(true);
        gameBoard.getChildren().add(pathCanvas);

        Canvas treeCanvas = new Canvas(App.getBoardMaxX(), App.getBoardMaxY());
        treeCanvas.setMouseTransparent(true);
        gameBoard.getChildren().add(treeCanvas);

        opponentSpawner.accept(enemyRegistrar, startDifficulty);

        return new SessionResult(player, maze, mazeCanvas, pathCanvas, treeCanvas, config);
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    private void updateBoardBackground(Pane gameBoard, Difficulty startDifficulty) {
        String bgPath = visualStyle.backgroundImageForDifficultyName(difficultyName(startDifficulty));
        try {
            var url = FxGameSessionBootstrapper.class.getResource(bgPath);
            if (url != null) {
                Image bgImage = new Image(url.toExternalForm());
                BackgroundImage bi = new BackgroundImage(bgImage,
                        BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
                gameBoard.setBackground(new Background(bi));
            } else {
                LOGGER.warning("Could not find background image: " + bgPath);
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error loading background", e);
        }
    }

    private static PlayerConfig loadPlayerConfig() {
        var loader = new XmiRulesLoader();
        try {
            return loader.loadPlayerConfigFromClasspath(
                    PlayerConstants.PlayerModelPath,
                    PlayerConstants.PlayerModelEcorePath);
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Failed to load player model, falling back to defaults", ex);
            return PlayerConfig.defaults();
        }
    }

    private static void setPlayerBaseImage(ImageView playerImageView, String imagePath) {
        if (imagePath == null || imagePath.isBlank()) return;
        try {
            var url = FxGameSessionBootstrapper.class.getResource(imagePath);
            if (url != null) {
                playerImageView.setImage(new Image(url.toExternalForm()));
            } else {
                LOGGER.warning("Player base image not found: " + imagePath);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Failed to load player base image from " + imagePath, ex);
        }
    }

    private static String difficultyName(Difficulty d) {
        return d == null ? "" : d.eClass().getName();
    }
}
