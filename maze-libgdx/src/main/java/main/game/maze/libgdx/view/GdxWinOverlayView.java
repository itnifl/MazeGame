package main.game.maze.libgdx.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import main.game.maze.game.score.ScoringEngine.ScoreBreakdown;
import main.game.maze.game.theme.MenuButton;
import main.game.maze.game.theme.MenuTheme;
import main.game.maze.game.theme.MenuThemeLoader;

/**
 * View renderer for the win overlay: the mission report between levels, in the shared menu theme ({@link GdxMenuSkin})
 * like the JavaFX {@code winScreen.fxml}. A riveted plate with hazard stripes, the header on the olive title plate, the
 * score in skull coins with its breakdown, the name being typed, and a row of metal-plate buttons. HUD space (y up).
 */
public final class GdxWinOverlayView {

    private static final float LINE_SPACING = 16f;
    private static final float INSET = 24f;
    private static final float BUTTON_W = 124f;
    private static final float BUTTON_H = 36f;
    private static final float BUTTON_GAP = 10f;

    private GdxMenuSkin skin;

    private GdxMenuSkin skin(WinOverlayContext context) {
        if (skin == null) {
            skin = new GdxMenuSkin(MenuThemeLoader.loadDefault(), context.textures());
        }
        return skin;
    }

    /** The buttons in the row, left to right: Save Score, [Shop, Next Level,] Restart Level, Back to Menu. */
    static List<MenuButton> buttons(boolean nextLevelAvailable) {
        List<MenuButton> buttons = new ArrayList<>(List.of(MenuButton.SAVE_SCORE));
        if (nextLevelAvailable) {
            buttons.add(MenuButton.SHOP);
            buttons.add(MenuButton.NEXT_LEVEL);
        }
        buttons.add(MenuButton.RESTART_LEVEL);
        buttons.add(MenuButton.BACK_TO_MENU);
        return buttons;
    }

    public WinButtons render(WinOverlayContext context) {
        GdxMenuSkin skin = skin(context);
        SpriteBatch batch = context.batch();
        BitmapFont font = context.font();
        GlyphLayout gl = context.glyphLayout();
        OrthographicCamera hudCamera = context.hudCamera();
        float w = hudCamera.viewportWidth;
        float h = hudCamera.viewportHeight;

        int breakdownLines = countBreakdownLines(context.scoreBreakdown());
        List<MenuButton> buttons = buttons(context.nextLevelAvailable());
        float panelW = Math.min(720f, w - 30f);
        float panelH = 318f + breakdownLines * LINE_SPACING;
        float panelX = (w - panelW) * 0.5f;
        float panelY = (h - panelH) * 0.5f;
        float left = panelX + INSET + 4f;
        float contentW = panelW - 2f * (INSET + 4f);
        float centreX = panelX + panelW / 2f;

        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        Runnable restoreFont = GdxMenuSkin.smoothText(font);
        skin.plate(batch, MenuTheme.Art.BACKDROP, 0f, 0f, w, h);
        skin.plate(batch, MenuTheme.Art.VIGNETTE, 0f, 0f, w, h);
        skin.plate(batch, MenuTheme.Art.PANEL, panelX, panelY, panelW, panelH);

        float top = panelY + panelH - INSET;
        skin.hazard(batch, left, top - skin.hazardHeight(), contentW);
        top -= 20f;
        String header = main.game.maze.game.status.FinalLevelIntro.winText(!context.nextLevelAvailable());
        top = skin.banner(batch, font, gl, header, centreX, top) - 6f;

        font.getData().setScale(0.8f);
        skin.centred(batch, font, gl, "Type your name and click Save Score - or stock up in the Shop and move on",
                skin.colour(MenuTheme.Swatch.MUTED), centreX, top);
        top -= 22f;
        font.getData().setScale(1f);
        skin.centred(batch, font, gl, "YOUR SCORE", skin.colour(MenuTheme.Swatch.TITLE), centreX, top);
        top -= 22f;

        // the score in skull coins
        font.getData().setScale(2.2f);
        String scoreText = String.valueOf(context.score());
        gl.setText(font, scoreText);
        float scoreX = centreX - (34f + 10f + gl.width) / 2f;
        skin.icon(batch, skin.theme().image(MenuTheme.Art.TOKEN).path(), scoreX, top - 34f, 34f);
        skin.text(batch, font, scoreText, skin.colour(MenuTheme.Swatch.PRICE), scoreX + 44f, top - (34f - gl.height) / 2f);
        font.getData().setScale(1f);
        top -= 46f;

        top = drawBreakdown(skin, batch, font, gl, context.scoreBreakdown(), centreX, top);

        // name input / status
        top -= 6f;
        if (context.winScoreSaved()) {
            skin.centred(batch, font, gl, context.winScoreStatus(), skin.colour(MenuTheme.Swatch.ACCENT), centreX, top);
        } else {
            skin.centred(batch, font, gl, "Name: " + context.winNameInput() + "_", skin.colour(MenuTheme.Swatch.TEXT),
                    centreX, top);
            if (context.winScoreStatus() != null && !context.winScoreStatus().isBlank()) {
                skin.centred(batch, font, gl, context.winScoreStatus(), skin.colour(MenuTheme.Swatch.BAD), centreX,
                        top - LINE_SPACING);
            }
        }

        // the button row, with the hazard strip over it
        float btnRowY = panelY + INSET;
        skin.hazard(batch, left, btnRowY + BUTTON_H + 10f, contentW);
        float btnW = Math.min(BUTTON_W, (contentW - (buttons.size() - 1) * BUTTON_GAP) / buttons.size());
        float rowW = buttons.size() * btnW + (buttons.size() - 1) * BUTTON_GAP;
        float x = centreX - rowW / 2f;
        float[] xs = new float[MenuButton.values().length];
        font.getData().setScale(0.9f);
        for (MenuButton button : buttons) {
            boolean enabled = button != MenuButton.SAVE_SCORE || !context.winScoreSaved();
            skin.button(batch, font, gl, button, x, btnRowY, btnW, BUTTON_H, enabled);
            xs[button.ordinal()] = x;
            x += btnW + BUTTON_GAP;
        }
        font.getData().setScale(1f);
        batch.end();
        restoreFont.run();

        boolean next = context.nextLevelAvailable();
        float nextW = next ? btnW : 0f;
        return new WinButtons(xs[MenuButton.SAVE_SCORE.ordinal()], btnRowY, btnW, BUTTON_H,
                xs[MenuButton.BACK_TO_MENU.ordinal()], btnRowY, btnW, BUTTON_H,
                xs[MenuButton.NEXT_LEVEL.ordinal()], btnRowY, nextW, BUTTON_H,
                xs[MenuButton.RESTART_LEVEL.ordinal()], btnRowY, btnW, BUTTON_H,
                xs[MenuButton.SHOP.ordinal()], btnRowY, nextW, BUTTON_H);
    }

    private static int countBreakdownLines(ScoreBreakdown bd) {
        if (bd == null) return 0;
        int n = 0;
        if (bd.damagePenalty()  > 0) n++;
        if (bd.dynamicPenalty() > 0) n++;
        if (bd.winBonus()       > 0) n++;
        return n;
    }

    /** Draws the centred breakdown lines (penalties bad news, the win bonus the accent); returns the y under them. */
    private static float drawBreakdown(GdxMenuSkin skin, SpriteBatch batch, BitmapFont font, GlyphLayout gl,
                                       ScoreBreakdown bd, float centreX, float startY) {
        if (bd == null) return startY;
        float y = startY;
        Color bad = skin.colour(MenuTheme.Swatch.BAD);
        if (bd.damagePenalty() > 0) {
            skin.centred(batch, font, gl, "- Damage penalty: -" + bd.damagePenalty(), bad, centreX, y);
            y -= LINE_SPACING;
        }
        if (bd.dynamicPenalty() > 0) {
            skin.centred(batch, font, gl, "- Path hint penalty: -" + bd.dynamicPenalty(), bad, centreX, y);
            y -= LINE_SPACING;
        }
        if (bd.winBonus() > 0) {
            skin.centred(batch, font, gl, "+ Win bonus: +" + bd.winBonus(), skin.colour(MenuTheme.Swatch.ACCENT), centreX, y);
            y -= LINE_SPACING;
        }
        return y;
    }

    public record WinOverlayContext(
            SpriteBatch batch,
            BitmapFont font,
            GlyphLayout glyphLayout,
            OrthographicCamera hudCamera,
            Function<String, Texture> textures,
            boolean winScoreSaved,
            String winScoreStatus,
            String winNameInput,
            int score,
            ScoreBreakdown scoreBreakdown,
            boolean nextLevelAvailable) {
    }

    public record WinButtons(
            float saveX,
            float saveY,
            float saveW,
            float saveH,
            float backX,
            float backY,
            float backW,
            float backH,
            float nextX,
            float nextY,
            float nextW,
            float nextH,
            float restartX,
            float restartY,
            float restartW,
            float restartH,
            float shopX,
            float shopY,
            float shopW,
            float shopH) {
    }
}
