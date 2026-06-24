package main.game.maze.libgdx.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.Locale;
import main.game.maze.libgdx.view.layout.HudLayout;

/**
 * Renders the gameplay HUD and command overlays.
 */
public final class GdxHudView {

    public HudLayout render(RenderContext context) {
        SpriteBatch batch = context.batch();
        ShapeRenderer shapes = context.shapes();
        BitmapFont font = context.font();
        OrthographicCamera hudCamera = context.hudCamera();

        batch.setProjectionMatrix(hudCamera.combined);
        shapes.setProjectionMatrix(hudCamera.combined);

        float w = hudCamera.viewportWidth;
        float h = hudCamera.viewportHeight;

        float scoreX = w - context.scorePanelWidth() - 6f;
        float scoreY = h - context.topMargin() - context.scorePanelHeight();

        float buttonX = 14f;
        float buttonY = 7f;
        float buttonW = 112f;
        float buttonH = 26f;
        float terminalButtonX = buttonX + buttonW + 12f;
        float terminalButtonY = buttonY;
        float terminalButtonW = 112f;
        float terminalButtonH = 26f;
        float commandYDraw = buttonY + context.commandPressOffset();
        float terminalYDraw = terminalButtonY + context.terminalPressOffset();
        float rowPanelX = 8f;
        float rowPanelY = context.bottomRowY();
        float rowPanelW = w - 16f;
        float rowPanelH = context.bottomRowHeight();

        HudLayout hudLayout = new HudLayout(
                buttonX,
                commandYDraw,
                buttonW,
                buttonH,
                terminalButtonX,
                terminalYDraw,
                terminalButtonW,
                terminalButtonH);

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(1f, 1f, 1f, 0.96f);
        shapes.rect(1f, context.hpBarBottomY(), w - 2f, context.hpBarHeight());
        shapes.setColor(0.76f, 0.22f, 0.17f, 0.92f);
        shapes.rect(1f, context.hpBarBottomY(), (w - 2f) * context.currentHpRatio(), context.hpBarHeight());

        shapes.setColor(0f, 0f, 0f, 0.45f);
        shapes.rect(scoreX, scoreY, context.scorePanelWidth(), context.scorePanelHeight());
        shapes.setColor(0f, 0f, 0f, 0.12f);
        shapes.rect(rowPanelX, rowPanelY, rowPanelW, rowPanelH);

        if (context.commandButtonPressedSeconds() > 0f) {
            shapes.setColor(0.42f, 0.86f, 0.74f, 0.92f);
        } else {
            shapes.setColor(0.56f, 1.0f, 0.88f, 0.85f);
        }
        shapes.rect(buttonX, commandYDraw, buttonW, buttonH);

        if (context.terminalButtonPressedSeconds() > 0f) {
            shapes.setColor(0.88f, 0.76f, 0.30f, 0.95f);
        } else {
            shapes.setColor(1f, 0.90f, 0.43f, 0.90f);
        }
        shapes.rect(terminalButtonX, terminalYDraw, terminalButtonW, terminalButtonH);
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0.56f, 1.0f, 0.88f, 0.85f);
        shapes.rect(1f, context.hpBarBottomY(), w - 2f, context.hpBarHeight());
        shapes.rect(scoreX, scoreY, context.scorePanelWidth(), context.scorePanelHeight());
        shapes.rect(rowPanelX, rowPanelY, rowPanelW, rowPanelH);
        shapes.rect(0f, context.bottomBarHeight(), w, h - context.bottomBarHeight() - context.hpBarHeight());
        shapes.rect(buttonX, commandYDraw, buttonW, buttonH);
        shapes.rect(terminalButtonX, terminalYDraw, terminalButtonW, terminalButtonH);
        shapes.end();

        batch.begin();
        font.setColor(new Color(0.07f, 0.22f, 0.19f, 1f));
        font.draw(batch, "Commands", buttonX + 10f, commandYDraw + 18f);

        font.setColor(new Color(0.18f, 0.11f, 0f, 1f));
        font.draw(batch, "Terminal", terminalButtonX + 16f, terminalYDraw + 18f);

        String commandText = "H Highscore  ESC Restart  P Path "
                + (context.showHintInfo() ? "[ON]" : "[OFF]")
                + (context.pathHintExhausted()
                ? " [SPENT]"
                : String.format(Locale.ROOT, " [%.0fs left]", context.pathHintRemainingSeconds()))
                + "  O Tree "
                + (context.showSpanningTreeInfo() ? "[ON]" : "[OFF]");
        font.setColor(new Color(0.84f, 1f, 0.96f, 1f));
        font.draw(batch, commandText, terminalButtonX + terminalButtonW + 14f, buttonY + 18f);

        font.setColor(Color.GOLD);
        font.getData().setScale(1.25f);
        font.draw(batch, "Score: " + context.score(), scoreX + 14f, scoreY + context.scorePanelHeight() - 8f);
        font.getData().setScale(1.0f);

        font.setColor(new Color(1f, 0.95f, 0.72f, 1f));
        font.draw(batch, "Bombs: " + context.playerBombsRemaining(), scoreX + 14f, scoreY + 16f);

        switch (context.messageMode()) {
            case WON -> {
                font.setColor(new Color(0.56f, 1.0f, 0.88f, 1f));
                font.draw(batch, "You reached the heart. Press ESC to return to menu.", 12f, context.bottomBarHeight() + 18f);
            }
            case GAME_OVER -> {
                font.setColor(new Color(1f, 0.35f, 0.30f, 1f));
                font.draw(batch, "You were defeated. Press ESC to return to menu.", 12f, context.bottomBarHeight() + 18f);
            }
            case STATUS -> {
                if (context.statusMessage() != null && !context.statusMessage().isBlank()) {
                    font.setColor(new Color(0.56f, 1.0f, 0.88f, 1f));
                    font.draw(batch, context.statusMessage(), 12f, context.bottomBarHeight() + 18f);
                }
            }
        }

        if (context.terminalActive()) {
            float panelX = 22f;
            float panelY = context.bottomBarHeight() + 26f;
            float panelW = Math.max(360f, w * 0.58f);
            float panelH = 56f;
            batch.end();

            shapes.begin(ShapeRenderer.ShapeType.Filled);
            shapes.setColor(0.02f, 0.04f, 0.09f, 0.93f);
            shapes.rect(panelX, panelY, panelW, panelH);
            shapes.end();

            shapes.begin(ShapeRenderer.ShapeType.Line);
            shapes.setColor(1f, 0.90f, 0.43f, 0.94f);
            shapes.rect(panelX, panelY, panelW, panelH);
            shapes.end();

            batch.begin();
            font.setColor(new Color(1f, 0.95f, 0.72f, 1f));
            font.draw(batch, "Terminal: " + context.terminalBufferText(), panelX + 10f, panelY + 35f);
            font.setColor(new Color(0.78f, 0.90f, 1f, 1f));
            font.draw(batch, "Enter: run  Backspace: delete  Esc: close", panelX + 10f, panelY + 16f);
        }
        batch.end();

        if (context.commandsOverlayVisible()) {
            drawCommandsOverlay(context);
        }

        return hudLayout;
    }

    private void drawCommandsOverlay(RenderContext context) {
        float w = context.hudCamera().viewportWidth;
        float h = context.hudCamera().viewportHeight;
        ShapeRenderer shapes = context.shapes();
        SpriteBatch batch = context.batch();
        BitmapFont font = context.font();

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0f, 0f, 0f, 0.38f);
        shapes.rect(0f, 0f, w, h);
        shapes.setColor(0.08f, 0.06f, 0.17f, 0.92f);
        shapes.rect(20f, 60f, w - 40f, h - 140f);
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0.56f, 1.0f, 0.88f, 0.95f);
        shapes.rect(20f, 60f, w - 40f, h - 140f);
        shapes.end();

        batch.begin();
        font.setColor(new Color(1f, 0.90f, 0.43f, 1f));
        font.getData().setScale(1.4f);
        font.draw(batch, "Commands", 40f, h - 95f);
        font.getData().setScale(1.0f);

        font.setColor(new Color(0.95f, 0.97f, 1f, 1f));
        font.draw(batch, "Arrow Keys: Move player", 40f, h - 130f);
        font.draw(batch, "H: Open high score screen", 40f, h - 156f);
        font.draw(batch, "ESC: Difficulty and restart prompt", 40f, h - 182f);
        font.draw(batch, "P: Show shortest path to heart (hold)", 40f, h - 208f);
        font.draw(batch, "O: Toggle tree info", 40f, h - 234f);
        font.draw(batch, "Warning: Showing shortest path continuously reduces score over time.", 40f, h - 260f);
        font.draw(batch, "Click anywhere to close", 40f, h - 292f);
        batch.end();
    }

    public enum HudMessageMode {
        WON,
        GAME_OVER,
        STATUS
    }

    public record RenderContext(
            SpriteBatch batch,
            ShapeRenderer shapes,
            BitmapFont font,
            OrthographicCamera hudCamera,
            float topMargin,
            float scorePanelWidth,
            float scorePanelHeight,
            float bottomBarHeight,
            float hpBarHeight,
            float hpBarBottomY,
            float bottomRowY,
            float bottomRowHeight,
            float currentHpRatio,
            float commandPressOffset,
            float terminalPressOffset,
            float commandButtonPressedSeconds,
            float terminalButtonPressedSeconds,
            boolean showHintInfo,
            boolean pathHintExhausted,
            float pathHintRemainingSeconds,
            boolean showSpanningTreeInfo,
            int score,
            int playerBombsRemaining,
            HudMessageMode messageMode,
            String statusMessage,
            boolean terminalActive,
            String terminalBufferText,
            boolean commandsOverlayVisible) {
    }
}
