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
        if (gameBoard == null) {
            return;
        }
        if (gameBoardClip == null) {
            gameBoardClip = new Rectangle();
            gameBoard.setClip(gameBoardClip);
        }
    }

    public void updateCameraFollow(PlayerCharacter playerCharacter) {
        if (gameBoard == null || gameBoard.getScene() == null || gameBoard.getScene().getRoot() == null || playerCharacter == null) {
            return;
        }
        if (gameBoardClip == null) {
            installGameBoardClip();
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

        double[] translation = computeCameraTranslation(
                viewportWidth, viewportHeight,
                worldWidth, worldHeight,
                playerX, playerY);

        gameBoard.setTranslateX(translation[0]);
        gameBoard.setTranslateY(translation[1]);

        // Shift the clip in the board's local space by the inverse of the translation.
        // Without this the clip (at local [0,0]) travels with the board and exposes
        // unrendered regions on the opposite side — producing a white strip.
        if (gameBoardClip != null) {
            double[] cr = computeClipRect(translation, viewportWidth, viewportHeight);
            gameBoardClip.setX(cr[0]);
            gameBoardClip.setY(cr[1]);
            gameBoardClip.setWidth(cr[2]);
            gameBoardClip.setHeight(cr[3]);
        }
    }

    /**
     * Returns {@code [clipX, clipY, clipWidth, clipHeight]} in the board's local
     * coordinate space. The clip must offset by the inverse of the board translation
     * so the visible window always frames the correct region of the world content.
     */
    static double[] computeClipRect(double[] translation, double viewportWidth, double viewportHeight) {
        return new double[]{-translation[0], -translation[1], viewportWidth, viewportHeight};
    }

    /**
     * Computes the board translation required to centre-follow the player.
     * The window is sized to min(screen, board), so when the board exceeds the
     * viewport on any axis the camera scrolls to keep the player in view, clamped
     * so the world never scrolls past its own edges.
     */
    public static double[] computeCameraTranslation(double viewportWidth, double viewportHeight,
            double worldWidth, double worldHeight, double playerX, double playerY) {

        boolean worldWider  = worldWidth  > viewportWidth;
        boolean worldTaller = worldHeight > viewportHeight;

        if (!worldWider && !worldTaller) {
            return new double[]{0, 0};
        }

        double translateX = 0;
        double translateY = 0;

        if (worldWider) {
            translateX = viewportWidth / 2 - playerX;
            translateX = Math.min(translateX, 0);
            translateX = Math.max(translateX, viewportWidth - worldWidth);
        }

        if (worldTaller) {
            translateY = viewportHeight / 2 - playerY;
            translateY = Math.min(translateY, 0);
            translateY = Math.max(translateY, viewportHeight - worldHeight);
        }

        return new double[]{translateX, translateY};
    }

    public void showSpanningTree(Canvas treeCanvas, PlayerCharacter playerCharacter, GameMazeWorld maze) {
        if (maze == null || treeCanvas == null || playerCharacter == null) return;

        var navGraph = maze.getNavigationGraph();
        if (navGraph == null) return;

        var characterPosition = playerCharacter.getCharacterPosition();
        if (characterPosition == null) return;
        Point2D playerPos = new Point2D(characterPosition.getX(), characterPosition.getY());
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
