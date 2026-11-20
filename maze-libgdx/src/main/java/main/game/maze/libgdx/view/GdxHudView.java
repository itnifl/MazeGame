package main.game.maze.libgdx.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.Locale;
import main.game.maze.game.items.ArsenalHud;
import main.game.maze.game.items.WeaponHud;
import main.game.maze.libgdx.view.layout.HudLayout;

/**
 * Renders the gameplay HUD and command overlays.
 */
public final class GdxHudView {

    /** Measures HUD text (needs no GL context). */
    private final GlyphLayout glyphs = new GlyphLayout();

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
        WeaponHud.Model weaponHud = context.weaponHud();
        float weaponX = 6f;
        float weaponY = h - context.topMargin() - (float) WeaponHud.panelHeight();
        if (weaponHud != null) {
            shapes.rect(weaponX, weaponY, (float) WeaponHud.panelWidth(weaponHud.slots().size()),
                    (float) WeaponHud.panelHeight());
            for (int i = 0; i < weaponHud.slots().size(); i++) {
                if (weaponHud.slots().get(i).selected()) {   // the weapon in hand: gold backing
                    shapes.setColor(1f, 0.82f, 0.25f, 0.55f);
                    shapes.rect(weaponX + (float) WeaponHud.slotX(i) - 2f, weaponY + (float) WeaponHud.PADDING - 2f,
                            (float) WeaponHud.ICON_SIZE + 4f, (float) WeaponHud.ICON_SIZE + 4f);
                    shapes.setColor(0f, 0f, 0f, 0.45f);
                }
            }
        }
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
        if (weaponHud != null) {
            shapes.rect(weaponX, weaponY, (float) WeaponHud.panelWidth(weaponHud.slots().size()),
                    (float) WeaponHud.panelHeight());
        }
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

        if (weaponHud != null) {
            drawWeaponHud(batch, font, context, weaponHud, weaponX, weaponY);
        }

        font.setColor(new Color(1f, 0.95f, 0.72f, 1f));
        // shrink the arsenal line to the score panel so no count is cut off at the window edge
        glyphs.setText(font, context.arsenalHudText());
        font.getData().setScale(ArsenalHud.fitScale(glyphs.width, context.scorePanelWidth() - 28f));
        font.draw(batch, context.arsenalHudText(), scoreX + 14f, scoreY + 16f);
        font.getData().setScale(1.0f);

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

    /** Weapons HUD (top-left): weapon icons with their stock, the one in hand highlighted, then the lives. */
    private static void drawWeaponHud(SpriteBatch batch, BitmapFont font, RenderContext context, WeaponHud.Model hud,
                                      float panelX, float panelY) {
        float icon = (float) WeaponHud.ICON_SIZE;
        float iconY = panelY + (float) WeaponHud.PADDING;
        for (int i = 0; i < hud.slots().size(); i++) {
            WeaponHud.Slot slot = hud.slots().get(i);
            float x = panelX + (float) WeaponHud.slotX(i);
            com.badlogic.gdx.graphics.Texture texture = context.iconLoader() != null ? context.iconLoader().apply(slot.sprite()) : null;
            batch.setColor(1f, 1f, 1f, slot.count() > 0 ? 1f : 0.4f);
            if (texture != null) {
                batch.draw(texture, x, iconY, icon, icon);
            }
            batch.setColor(Color.WHITE);
            font.setColor(slot.selected() ? Color.GOLD : Color.WHITE);
            font.draw(batch, String.valueOf(slot.count()), x + icon - 10f, iconY + 12f);
        }
        float livesX = panelX + (float) WeaponHud.livesX(hud.slots().size());
        com.badlogic.gdx.graphics.Texture life = context.iconLoader() != null ? context.iconLoader().apply(hud.lifeIcon()) : null;
        if (life != null) {
            batch.draw(life, livesX, iconY, icon, icon);
        }
        font.setColor(new Color(1f, 0.92f, 0.5f, 1f));
        font.draw(batch, "x" + hud.lives(), livesX + icon + 4f, iconY + icon * 0.65f);
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
            String arsenalHudText,
            WeaponHud.Model weaponHud,
            java.util.function.Function<String, com.badlogic.gdx.graphics.Texture> iconLoader,
            HudMessageMode messageMode,
            String statusMessage,
            boolean terminalActive,
            String terminalBufferText,
            boolean commandsOverlayVisible) {
    }
}
