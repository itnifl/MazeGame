package main.game.maze.libgdx.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import main.game.maze.libgdx.controller.GdxHudInteractionStateController;
import main.game.maze.libgdx.controller.GdxTerminalController;
import main.game.maze.libgdx.input.InputFrame;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.libgdx.view.layout.HudLayout;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;

/**
 * Small interaction boundary for HUD clicks and terminal prompt toggling.
 */
public final class GdxGameInteractionSupport {

    private static final float BUTTON_PRESS_SECONDS = 0.14f;
    private static final float MOUSE_STEP_DISTANCE = 20f;

    private GdxGameInteractionSupport() {
    }

    public static void handleGameMouseInput(
            InputFrame inputFrame,
            HudLayout hudLayout,
            float hudViewportHeight,
            GdxHudInteractionStateController hudInteractionState,
            GdxTerminalController terminalController,
            Viewport viewport,
            OrthographicCamera camera,
            MazeArena maze,
            PlayerState player,
            GameWorldModel worldModel,
            Consumer<String> statusMessage,
            BiConsumer<MazeArena, PlayerState> updateCameraFollow,
            Runnable openTerminalPrompt,
            Runnable closeTerminalPrompt) {
        if (!inputFrame.leftMouseClicked()) {
            return;
        }
        float mx = inputFrame.mouseX();
        float my = hudViewportHeight - inputFrame.mouseY();

        if (contains(mx, my, hudLayout.commandButtonX(), hudLayout.commandButtonY(), hudLayout.commandButtonW(), hudLayout.commandButtonH())) {
            hudInteractionState.pressCommandsButton(BUTTON_PRESS_SECONDS);
            return;
        }

        if (contains(mx, my, hudLayout.terminalButtonX(), hudLayout.terminalButtonY(), hudLayout.terminalButtonW(), hudLayout.terminalButtonH())) {
            hudInteractionState.pressTerminalButton(BUTTON_PRESS_SECONDS);
            if (terminalController.isActive()) {
                closeTerminalPrompt.run();
            } else {
                openTerminalPrompt.run();
            }
            return;
        }

        if (hudInteractionState.commandsOverlayVisible()) {
            hudInteractionState.hideCommandsOverlay();
            return;
        }

        if (player != null && viewport != null) {
            Vector3 worldClick = new Vector3(inputFrame.mouseX(), inputFrame.mouseY(), 0f);
            viewport.unproject(worldClick);
            float vx = worldClick.x - player.x();
            float vy = worldClick.y - player.y();
            float len = (float) Math.sqrt(vx * vx + vy * vy);
            if (len > 0.001f) {
                float nx = vx / len;
                float ny = vy / len;
                player.attemptMove(nx * MOUSE_STEP_DISTANCE, ny * MOUSE_STEP_DISTANCE, maze);
                worldModel.setMaze(maze);
                worldModel.setPlayer(player);
                updateCameraFollow.accept(maze, player);
            }
        }

        statusMessage.accept(String.format(Locale.ROOT, "Mouse: %.0f, %.0f", mx, my));
    }

    public static void openTerminalPrompt(GdxTerminalController terminalController, Consumer<String> statusMessage) {
        terminalController.open();
        statusMessage.accept("Terminal opened. Type command and press Enter");
    }

    public static void closeTerminalPrompt(GdxTerminalController terminalController, Consumer<String> statusMessage) {
        terminalController.close();
        statusMessage.accept("Terminal closed");
    }

    public static void handleTerminalTypingInput(
            GdxTerminalController terminalController,
            Consumer<String> statusMessage) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            closeTerminalPrompt(terminalController, statusMessage);
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            terminalController.submit();
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            terminalController.backspace();
            return;
        }
        // All other character input is fed by InputAdapter#keyTyped which
        // respects the OS keyboard layout (æ, ø, å, etc.).
    }

    public static void executeTerminalCommand(
            String raw,
            GdxDebugOverlayState debugOverlayState,
            Consumer<String> statusMessage,
            float enemyLabelSeconds,
            float enemyPathOverlaySeconds) {
        GdxTerminalCommandSupport.Outcome outcome = GdxTerminalCommandSupport.evaluate(raw);
        debugOverlayState.applyTerminalAction(outcome.action(), enemyLabelSeconds, enemyPathOverlaySeconds);
        if (outcome.statusSeconds() > 0f) {
            statusMessage.accept(outcome.statusText());
            return;
        }
        statusMessage.accept(outcome.statusText());
    }

    public static boolean contains(float px, float py, float x, float y, float w, float h) {
        return px >= x && px <= x + w && py >= y && py <= y + h;
    }
}
