package main.game.maze.libgdx.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.function.Function;
import main.game.maze.game.theme.MenuButton;
import main.game.maze.game.theme.MenuTheme;
import main.game.maze.game.theme.MenuThemeLoader;
import main.game.maze.game.score.ScoringEngine.ScoreBreakdown;

/**
 * View renderer for the game over overlay, in the shared menu theme ({@link GdxMenuSkin}) like the JavaFX
 * {@code gameOverScreen.fxml}: the riveted plate with hazard stripes, "GAME OVER" on the title plate, the score in skull
 * coins with its penalties, and the metal-plate buttons. HUD space (y up).
 */
public final class GdxGameOverOverlayView {

    private static final float LINE_SPACING  = 18f;
    private static final float INSET = 24f;
    private static final float BUTTON_W = 170f;
    private static final float BUTTON_H = 36f;
    private static final float BUTTON_GAP = 16f;

    private GdxMenuSkin skin;

    public GameOverButtons render(RenderContext context) {
        if (skin == null) {
            skin = new GdxMenuSkin(MenuThemeLoader.loadDefault(), context.textures());
        }
        SpriteBatch batch = context.batch();
        BitmapFont font = context.font();
        GlyphLayout gl = context.glyphLayout();
        OrthographicCamera hudCamera = context.hudCamera();
        float w = hudCamera.viewportWidth;
        float h = hudCamera.viewportHeight;

        int breakdownLines = countBreakdownLines(context.scoreBreakdown());
        float panelW = Math.min(560f, w - 40f);
        float panelH = 300f + breakdownLines * LINE_SPACING;
        float panelX = (w - panelW) * 0.5f;
        float panelY = (h - panelH) * 0.5f;
        float left = panelX + INSET + 4f;
        float contentW = panelW - 2f * (INSET + 4f);
        float centreX = panelX + panelW / 2f;

        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        Runnable restoreFont = GdxMenuSkin.smoothText(font);
        if (context.backdrop() != null) {
            batch.setColor(Color.WHITE);
            batch.draw(context.backdrop(), 0f, 0f, w, h);
        } else {
            skin.plate(batch, MenuTheme.Art.BACKDROP, 0f, 0f, w, h);
        }
        skin.plate(batch, MenuTheme.Art.VIGNETTE, 0f, 0f, w, h);
        skin.plate(batch, MenuTheme.Art.PANEL, panelX, panelY, panelW, panelH);

        float top = panelY + panelH - INSET;
        skin.hazard(batch, left, top - skin.hazardHeight(), contentW);
        top -= 20f;
        top = skin.banner(batch, font, gl, "GAME OVER", centreX, top) - 6f;

        font.getData().setScale(0.8f);
        skin.centred(batch, font, gl, "Restart the level, or press ESC to return to the start menu",
                skin.colour(MenuTheme.Swatch.MUTED), centreX, top);
        top -= 22f;
        font.getData().setScale(1f);
        skin.centred(batch, font, gl, "YOUR SCORE", skin.colour(MenuTheme.Swatch.TITLE), centreX, top);
        top -= 22f;

        font.getData().setScale(2.2f);
        String scoreText = String.valueOf(context.score());
        gl.setText(font, scoreText);
        float scoreX = centreX - (34f + 10f + gl.width) / 2f;
        skin.icon(batch, skin.theme().image(MenuTheme.Art.TOKEN).path(), scoreX, top - 34f, 34f);
        skin.text(batch, font, scoreText, skin.colour(MenuTheme.Swatch.PRICE), scoreX + 44f, top - (34f - gl.height) / 2f);
        font.getData().setScale(1f);
        top -= 46f;

        drawBreakdown(skin, batch, font, gl, context.scoreBreakdown(), centreX, top);

        float btnRowY = panelY + INSET;
        skin.hazard(batch, left, btnRowY + BUTTON_H + 10f, contentW);
        float btnW = Math.min(BUTTON_W, (contentW - BUTTON_GAP) / 2f);
        float restartBtnX = centreX - btnW - BUTTON_GAP / 2f;
        float backBtnX = centreX + BUTTON_GAP / 2f;
        font.getData().setScale(0.9f);
        skin.button(batch, font, gl, MenuButton.RESTART_LEVEL, restartBtnX, btnRowY, btnW, BUTTON_H, true);
        skin.button(batch, font, gl, MenuButton.BACK_TO_MENU, backBtnX, btnRowY, btnW, BUTTON_H, true);
        font.getData().setScale(1f);
        batch.end();
        restoreFont.run();

        return new GameOverButtons(
            restartBtnX, btnRowY, btnW, BUTTON_H,
            backBtnX, btnRowY, btnW, BUTTON_H);
    }

    private static int countBreakdownLines(ScoreBreakdown bd) {
        if (bd == null) return 0;
        int n = 0;
        if (bd.deathPenalty()   > 0) n++;
        if (bd.damagePenalty()  > 0) n++;
        if (bd.dynamicPenalty() > 0) n++;
        return n;
    }

    private static void drawBreakdown(GdxMenuSkin skin, SpriteBatch batch, BitmapFont font, GlyphLayout gl,
                                       ScoreBreakdown bd, float centreX, float startY) {
        if (bd == null) return;
        float y = startY;
        Color bad = skin.colour(MenuTheme.Swatch.BAD);
        if (bd.deathPenalty() > 0) {
            skin.centred(batch, font, gl, "- Death penalty: -" + bd.deathPenalty(), bad, centreX, y);
            y -= LINE_SPACING;
        }
        if (bd.damagePenalty() > 0) {
            skin.centred(batch, font, gl, "- Damage penalty: -" + bd.damagePenalty(), bad, centreX, y);
            y -= LINE_SPACING;
        }
        if (bd.dynamicPenalty() > 0) {
            skin.centred(batch, font, gl, "- Path hint penalty: -" + bd.dynamicPenalty(), bad, centreX, y);
        }
    }

    public record RenderContext(
            SpriteBatch batch,
            BitmapFont font,
            GlyphLayout glyphLayout,
            OrthographicCamera hudCamera,
            Texture backdrop,
            Function<String, Texture> textures,
            int score,
            ScoreBreakdown scoreBreakdown) {
    }

    public record GameOverButtons(
            float restartX,
            float restartY,
            float restartW,
            float restartH,
            float backX,
            float backY,
            float backW,
            float backH) {
    }
}
