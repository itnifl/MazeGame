package main.game.maze.javafx.controller.state;

import javafx.scene.input.KeyCode;
import main.game.maze.App;
import main.game.maze.FxGameWorldModel;
import main.game.maze.JavaFxInputCommandContext;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.common.controller.state.GameModeRouter;
import main.game.maze.common.input.InputFrame;
import main.game.maze.common.input.InputRouter;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.javafx.render.FxGameRenderCoordinator;

/**
 * The PLAYING update path: input routing, movement application,
 * route-hint penalty, camera follow.
 */
public final class FxPlayingModeController implements GameModeRouter.ModeHandler {

    private final FxGameWorldModel model;
    private final InputRouter<KeyCode> inputRouter;
    private final PlayerCharacter playerCharacter;
    private final FxGameRenderCoordinator renderCoordinator;
    private final JavaFxInputCommandContext context;

    private long lastMoveTime = 0;
    private static final long MOVE_INTERVAL_NANOS = 33_000_000L; // ~30 moves per second

    public FxPlayingModeController(FxGameWorldModel model, InputRouter<KeyCode> inputRouter, 
            PlayerCharacter playerCharacter, FxGameRenderCoordinator renderCoordinator, JavaFxInputCommandContext context) {
        this.model = model;
        this.inputRouter = inputRouter;
        this.playerCharacter = playerCharacter;
        this.renderCoordinator = renderCoordinator;
        this.context = context;
    }

    @Override
    public boolean update() {
        return false; // update is called from GameController, not here
    }

    public void update(InputFrame<KeyCode> frame, long now) {
        if (playerCharacter == null || playerCharacter.getCharacterGraphics() == null) {
            return;
        }

        applyRouteHintPenalty(now);
        inputRouter.route(frame, context);

        if (now - lastMoveTime < MOVE_INTERVAL_NANOS) {
            return;
        }
        lastMoveTime = now;
        
        boolean moved = false;
        if (frame.isHeld(KeyCode.UP) || frame.isHeld(KeyCode.NUMPAD8) || frame.isHeld(KeyCode.W)) {
            stepPlayer(speed -> playerCharacter.moveUp(speed, false));
            moved = true;
        }
        if (frame.isHeld(KeyCode.DOWN) || frame.isHeld(KeyCode.NUMPAD5) || frame.isHeld(KeyCode.S)) {
            stepPlayer(speed -> playerCharacter.moveDown(speed, false));
            moved = true;
        }
        if (frame.isHeld(KeyCode.LEFT) || frame.isHeld(KeyCode.NUMPAD4) || frame.isHeld(KeyCode.A)) {
            stepPlayer(speed -> playerCharacter.moveLeft(speed, false));
            moved = true;
        }
        if (frame.isHeld(KeyCode.RIGHT) || frame.isHeld(KeyCode.NUMPAD6) || frame.isHeld(KeyCode.D)) {
            stepPlayer(speed -> playerCharacter.moveRight(speed, false));
            moved = true;
        }
        
        if (moved) {
            renderCoordinator.updateCameraFollow(playerCharacter);
            context.updateDebugLabels();
        }
    }

    private void stepPlayer(java.util.function.IntPredicate step) {
        int iterations = Math.max(1, StageConstants.PlayerCharacterSpeed / StageConstants.SpeedReducer);
        for (int x = 0; x < iterations; x++) {
            if (step.test(StageConstants.PlayerCharacterSpeed - (x * StageConstants.SpeedReducer))) {
                return;
            }
        }
    }

    private void applyRouteHintPenalty(long now) {
        if (!model.isRouteHintVisible()) {
            model.setLastRouteHintPenaltyNanos(now);
            return;
        }

        if (model.lastRouteHintPenaltyNanos() == 0L) {
            model.setLastRouteHintPenaltyNanos(now);
            return;
        }

        long elapsedNanos = now - model.lastRouteHintPenaltyNanos();
        if (elapsedNanos <= 0) {
            return;
        }
        model.setLastRouteHintPenaltyNanos(now);

        double elapsedMs = elapsedNanos / 1_000_000.0;
        model.setRouteHintPenaltyAccumulator(
                model.routeHintPenaltyAccumulator() + elapsedMs * 0.05);

        if (model.routeHintPenaltyAccumulator() >= 1.0) {
            int penaltyToApply = (int) model.routeHintPenaltyAccumulator();
            model.setRouteHintPenaltyPoints(model.routeHintPenaltyPoints() + penaltyToApply);
            model.setRouteHintPenaltyAccumulator(model.routeHintPenaltyAccumulator() - penaltyToApply);
            context.updateScoreHud();
        }
    }
}
