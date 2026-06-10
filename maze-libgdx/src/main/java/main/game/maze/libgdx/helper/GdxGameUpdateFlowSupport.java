package main.game.maze.libgdx.helper;

import main.game.maze.libgdx.controller.GdxPlayerInputController;
import main.game.maze.common.input.InputFrame;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;

/**
 * Helper for update-loop flow that should stay out of the screen controller.
 */
public final class GdxGameUpdateFlowSupport {

    private GdxGameUpdateFlowSupport() {
    }

    public static void applyMovementFromFrame(
            InputFrame<Integer> currentInputFrame,
            GdxPlayerInputController playerInputController,
            PlayerState player,
            MazeArena maze,
            float activePlayerSpeed,
            float currentFrameDt,
            Runnable onMoved) {
        GdxPlayerInputController.MovementIntent movementIntent = playerInputController.resolveMovement(
                currentInputFrame.isHeld(com.badlogic.gdx.Input.Keys.LEFT) || currentInputFrame.isHeld(com.badlogic.gdx.Input.Keys.A),
                currentInputFrame.isHeld(com.badlogic.gdx.Input.Keys.RIGHT) || currentInputFrame.isHeld(com.badlogic.gdx.Input.Keys.D),
                currentInputFrame.isHeld(com.badlogic.gdx.Input.Keys.DOWN) || currentInputFrame.isHeld(com.badlogic.gdx.Input.Keys.S),
                currentInputFrame.isHeld(com.badlogic.gdx.Input.Keys.UP) || currentInputFrame.isHeld(com.badlogic.gdx.Input.Keys.W));

        if (!movementIntent.hasMovement() || player == null || maze == null) {
            return;
        }

        player.attemptMove(
                movementIntent.dx() * activePlayerSpeed * currentFrameDt,
                movementIntent.dy() * activePlayerSpeed * currentFrameDt,
                maze);
        onMoved.run();
    }

    public static void applyDeathSequence(
            boolean combatFrameDead,
            GameWorldModel worldModel,
            float dt,
            float deathDisplayDelaySeconds,
            Runnable enterGameOverMode,
            Runnable switchToGameOverMusic) {
        if (!combatFrameDead) {
            return;
        }

        if (!worldModel.deathSequenceStarted()) {
            worldModel.setDeathSequenceStarted(true);
            worldModel.setDeathDisplayRemainingSeconds(deathDisplayDelaySeconds);
        }

        worldModel.setDeathDisplayRemainingSeconds(worldModel.deathDisplayRemainingSeconds() - dt);
        if (worldModel.deathDisplayRemainingSeconds() > 0f) {
            return;
        }

        enterGameOverMode.run();
        if (!worldModel.playedGameOverSound()) {
            worldModel.setPlayedGameOverSound(true);
            switchToGameOverMusic.run();
        }
    }
}
