package main.game.maze.javafx.render;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import main.game.maze.App;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.mazeworld.GameMazeWorld;

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

        gameBoardClip.widthProperty().bind(gameBoard.getScene().getRoot().getBoundsInLocal().widthProperty());
        gameBoardClip.heightProperty().bind(gameBoard.getScene().getRoot().getBoundsInLocal().heightProperty());
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

        double translateX = 0;
        double translateY = 0;

        if (worldWidth > viewportWidth) {
            translateX = viewportWidth / 2 - playerX;
            translateX = Math.min(translateX, 0);
            translateX = Math.max(translateX, viewportWidth - worldWidth);
        }

        if (worldHeight > viewportHeight) {
            translateY = viewportHeight / 2 - playerY;
            translateY = Math.min(translateY, 0);
            translateY = Math.max(translateY, viewportHeight - worldHeight);
        }
        
        return new double[]{translateX, translateY};
    }
}
