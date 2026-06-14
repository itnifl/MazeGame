package main.game.maze.javafx.render;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.game.maze.App;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.service.MazeNavigationGraphService;

/**
 * Owns canvas/HUD/overlay redraw orchestration over an immutable render snapshot
 * from FxGameWorldModel.
 */
public final class FxGameRenderCoordinator {

    private final Pane gameBoard;
    private Rectangle gameBoardClip;

    public FxGameRenderCoordinator(Pane gameBoard) {
        this.gameBoard = gameBoard;
        installGameBoardClip();
    }

    private void installGameBoardClip() {
        if (gameBoard == null || gameBoard.getScene() == null || gameBoard.getScene().getRoot() == null) {
            return;
        }
        if (gameBoardClip == null) {
            gameBoardClip = new Rectangle();
            gameBoard.setClip(gameBoardClip);
        }

        gameBoardClip.widthProperty().bind(gameBoard.getScene().widthProperty());
        gameBoardClip.heightProperty().bind(gameBoard.getScene().heightProperty());
    }

    public void updateCameraFollow(PlayerCharacter playerCharacter) {
        if (gameBoard == null || gameBoard.getScene() == null || gameBoard.getScene().getRoot() == null || playerCharacter == null) {
            return;
        }

        double viewportWidth = gameBoard.getScene().getRoot().getBoundsInLocal().getWidth();
        double viewportHeight = gameBoard.getScene().getRoot().getBoundsInLocal().getHeight();
        if (viewportWidth <= 0 || viewportHeight <= 0) {
            return;
        }

        double worldWidth = App.getBoardMaxX();
        double worldHeight = App.getBoardMaxY();

        double playerX = playerCharacter.getCharacterPosition().getX();
        double playerY = playerCharacter.getCharacterPosition().getY();

        boolean fullscreen = isStageFullscreen();
        double[] translation = computeCameraTranslation(
                viewportWidth, viewportHeight,
                worldWidth, worldHeight,
                playerX, playerY,
                fullscreen);

        gameBoard.setTranslateX(translation[0]);
        gameBoard.setTranslateY(translation[1]);
    }
    
    private boolean isStageFullscreen() {
        if (gameBoard == null || gameBoard.getScene() == null) {
            return false;
        }
        var window = gameBoard.getScene().getWindow();
        return window instanceof javafx.stage.Stage stage && stage.isFullScreen();
    }
    
    public static double[] computeCameraTranslation(double viewportWidth, double viewportHeight,
            double worldWidth, double worldHeight, double playerX, double playerY, boolean fullscreen) {

        boolean worldWider  = worldWidth  > viewportWidth;
        boolean worldTaller = worldHeight > viewportHeight;

        // World fits entirely in viewport — no translation needed.
        if (!worldWider && !worldTaller) {
            return new double[]{0, 0};
        }

        // Both axes overflow in windowed mode — keep the board at origin so the
        // player always sees the top-left starting area.
        if (worldWider && worldTaller && !fullscreen) {
            return new double[]{0, 0};
        }

        double translateX = 0;
        double translateY = 0;

        if (worldWider) {
            if (fullscreen) {
                // Centre-follow the player, clamped so the world never scrolls past its edges.
                translateX = viewportWidth / 2 - playerX;
                translateX = Math.min(translateX, 0);
                translateX = Math.max(translateX, viewportWidth - worldWidth);
            } else {
                // Only width overflows in windowed mode — anchor to the far-right edge.
                translateX = viewportWidth - worldWidth;
            }
        }

        if (worldTaller) {
            if (fullscreen) {
                translateY = viewportHeight / 2 - playerY;
                translateY = Math.min(translateY, 0);
                translateY = Math.max(translateY, viewportHeight - worldHeight);
            } else {
                // Only height overflows in windowed mode — anchor to the far-bottom edge.
                translateY = viewportHeight - worldHeight;
            }
        }

        return new double[]{translateX, translateY};
    }

    public void showSpanningTree(Canvas treeCanvas, PlayerCharacter playerCharacter, GameMazeWorld maze) {
        if (maze == null || treeCanvas == null || playerCharacter == null) return;

        var navGraph = maze.getNavigationGraph();
        if (navGraph == null) return;

        Point2D playerPos = new Point2D(
                playerCharacter.getCharacterPosition().getX(),
                playerCharacter.getCharacterPosition().getY());
        MazeNavigationGraphService.rebuildSpanningTreeFrom(navGraph, playerPos);

        GraphicsContext gc = treeCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, treeCanvas.getWidth(), treeCanvas.getHeight());
        gc.setStroke(Color.RED);
        gc.setLineWidth(4.0);
        gc.setGlobalAlpha(0.6);

        var grid = navGraph.getGrid();
        int cols = navGraph.getCols();
        int rows = navGraph.getRows();

        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                var node = grid[c][r];
                if (node == null) continue;
                var parent = node.getTreeParent();
                if (parent != null) {
                    gc.strokeLine(node.getX(), node.getY(), parent.getX(), parent.getY());
                }
            }
        }
        gc.setGlobalAlpha(1.0);
    }

    public void clearSpanningTree(Canvas treeCanvas) {
        if (treeCanvas == null) return;
        GraphicsContext gc = treeCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, treeCanvas.getWidth(), treeCanvas.getHeight());
    }
}
