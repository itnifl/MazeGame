package main.game.maze;

import javafx.scene.input.KeyCode;
import main.game.maze.common.input.InputFrame;
import main.game.maze.common.input.command.GameCommand;

/**
 * JavaFX-specific contract for input commands.
 * Extends the shared {@link GameCommand<KeyCode>} interface with a convenience method
 * for executing against a JavaFX input context.
 */
interface JavaFxGameCommand extends GameCommand<KeyCode> {

    /**
     * Execute this command in the context of a JavaFX game session.
     * Adapts from the shared GameCommand signature to the JavaFX-specific context.
     *
     * @param context the JavaFX input command context
     */
    void execute(JavaFxInputCommandContext context);

    /**
     * Default implementation of the shared GameCommand contract.
     * Routes through the JavaFX-specific execute() method.
     */
    @Override
    default void execute(main.game.maze.common.input.command.GameCommandContext context, InputFrame<KeyCode> frame) {
        if (context instanceof JavaFxInputCommandContext javaFxContext) {
            execute(javaFxContext);
        }
    }
}
