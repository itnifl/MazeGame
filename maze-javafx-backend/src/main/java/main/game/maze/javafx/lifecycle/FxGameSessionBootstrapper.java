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
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.config.model.PlayerConfig;
import main.game.maze.config.service.XmiRulesLoader;
import main.game.maze.constants.PlayerConstants;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.game.level.LevelSpec;
import main.game.maze.javafx.render.FxMazeCanvasRenderer;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.runtime.opponents.EnemyRegistrar;
import main.game.maze.runtime.opponents.OpponentRuntimeFactory;
import main.game.maze.runtime.opponents.PortalGhostSpawner;
import main.game.maze.runtime.opponents.GuardTowerSpawner;
import main.game.maze.runtime.opponents.WallBuilderSpawner;
import main.game.maze.runtime.opponents.SergeantSpawner;

import java.util.function.BiConsumer;
import java.util.function.Supplier;
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
                LevelSpec level = App.levelProgress.current();
                if (difficulty != null) OpponentRuntimeFactory.instantiateFromModel(registrar, difficulty, level);
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
        // same generated maze; a level with zones only changes which walls are strong and which weak
        LevelSpec     level     = App.levelProgress.current();
        level.applyWalls(maze, App.getBoardMaxX(), App.getBoardMaxY());
        level.applyLava(maze, App.getBoardMaxX(), App.getBoardMaxY());   // before anything is placed: nothing stands in lava
        // with lava under the maze the maze canvas has no floor of its own: the lava layer beneath it paints the floor,
        // so the lava shows wherever there is no wall
        mazeCanvasRenderer.setTransparentBackground(main.game.maze.game.lava.LavaEffects.visible(maze.getLava()));
        PlayerConfig  config    = loadPlayerConfig();

        setPlayerBaseImage(playerImageView, config.imageBase());
        // start close to the original start, on free ground and not under the HUD
        double half = StageConstants.PlayerCharacterXYSize / 2.0;
        main.game.maze.mazeworld.Point2D spawn = main.game.maze.game.spawn.PlayerSpawn.find(maze,
                playerImageView.getLayoutX() + half, playerImageView.getLayoutY() + half,
                StageConstants.PlayerCharacterXYSize, App.getBoardMaxX(), App.getBoardMaxY());
        playerImageView.setLayoutX(spawn.getX() - half);
        playerImageView.setLayoutY(spawn.getY() - half);

        PlayerCharacter player = new PlayerCharacter(
                playerImageView,
                playerImageView.getLayoutX(),
                playerImageView.getLayoutY(),
                hpBar,
                config);
        player.setHitPoints(config.health());

        // levels that show their wall materials draw each wall with its material's sprite
        mazeCanvasRenderer.setWallMaterialLook(level.showWallMaterials() ? maze::wallMaterialIds : null);

        // Canvases — order matters for rendering (maze at index 0, overlays above)
        Canvas mazeCanvas = drawCanvasOrFallback(
                () -> mazeCanvasRenderer.drawCanvas(maze.getMazeVectors()),
                App.getBoardMaxX(), App.getBoardMaxY());
        gameBoard.getChildren().add(0, mazeCanvas);

        Canvas pathCanvas = new Canvas(App.getBoardMaxX(), App.getBoardMaxY());
        pathCanvas.setMouseTransparent(true);
        gameBoard.getChildren().add(pathCanvas);

        Canvas treeCanvas = new Canvas(App.getBoardMaxX(), App.getBoardMaxY());
        treeCanvas.setMouseTransparent(true);
        gameBoard.getChildren().add(treeCanvas);

        opponentSpawner.accept(enemyRegistrar, startDifficulty);
        SergeantSpawner.spawn(enemyRegistrar, level, startDifficulty, maze);
        PortalGhostSpawner.spawn(enemyRegistrar, level, startDifficulty, maze,
                playerImageView.getLayoutX(), playerImageView.getLayoutY());
        WallBuilderSpawner.spawn(enemyRegistrar, level, startDifficulty, maze);
        GuardTowerSpawner.spawn(enemyRegistrar, level, startDifficulty, maze,
                playerImageView.getLayoutX(), playerImageView.getLayoutY());

        return new SessionResult(player, maze, mazeCanvas, pathCanvas, treeCanvas, config);
    }

    // -----------------------------------------------------------------------
    // Package-private helpers (accessible from tests in same package)
    // -----------------------------------------------------------------------

    /**
     * Draws the maze canvas via the supplied factory, catching the JVM errors
     * that arise when the {@code main.game.maze.walls} bundle is absent from
     * the runtime classpath.  Returns a blank canvas with the given dimensions
     * as a fallback, ensuring enemy spawning and the rest of session setup
     * still proceed normally.
     */
    static Canvas drawCanvasOrFallback(Supplier<Canvas> canvasSupplier, int fallbackWidth, int fallbackHeight) {
        try {
            return canvasSupplier.get();
        } catch (ExceptionInInitializerError | NoClassDefFoundError e) {
            LOGGER.log(Level.SEVERE,
                    "WallRegistry static initializer failed — main.game.maze.walls may be missing from classpath. "
                    + "Falling back to blank wall canvas.", e);
            return new Canvas(fallbackWidth, fallbackHeight);
        }
    }

    // -----------------------------------------------------------------------
    // Private helpers
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
