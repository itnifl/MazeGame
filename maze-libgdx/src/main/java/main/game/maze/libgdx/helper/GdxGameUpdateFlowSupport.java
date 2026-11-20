package main.game.maze.libgdx.helper;

import main.game.maze.libgdx.input.GdxMovementKeys;
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
                GdxMovementKeys.KEYS.isLeft(currentInputFrame),
                GdxMovementKeys.KEYS.isRight(currentInputFrame),
                GdxMovementKeys.KEYS.isDown(currentInputFrame),
                GdxMovementKeys.KEYS.isUp(currentInputFrame));

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
            java.util.function.BooleanSupplier respawn,
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

        // lives left: the player comes back at the start instead of the game ending
        if (respawn.getAsBoolean()) {
            worldModel.setDeathSequenceStarted(false);
            worldModel.setDeathDisplayRemainingSeconds(0f);
            return;
        }
        enterGameOverMode.run();
        if (!worldModel.playedGameOverSound()) {
            worldModel.setPlayedGameOverSound(true);
            switchToGameOverMusic.run();
        }
    }
}
