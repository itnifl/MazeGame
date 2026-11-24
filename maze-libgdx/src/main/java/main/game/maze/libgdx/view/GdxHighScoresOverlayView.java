package main.game.maze.libgdx.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import main.game.maze.dto.Score;
import main.game.maze.game.theme.MenuTheme;
import main.game.maze.game.theme.MenuThemeLoader;

/**
 * View renderer for the high scores overlay, in the shared menu theme ({@link GdxMenuSkin}) like the JavaFX
 * {@code highscoreScreen.fxml}: the riveted plate with hazard stripes, "HALL OF FAME" on the title plate and the top
 * scores in skull coins. HUD space (y up).
 */
public final class GdxHighScoresOverlayView {

    private static final float INSET = 24f;
    private static final float ROW_H = 26f;

    private GdxMenuSkin skin;

    public void render(RenderContext context, List<Score> highScoreRows) {
        if (skin == null) {
            skin = new GdxMenuSkin(MenuThemeLoader.loadDefault(), context.textures());
        }
        SpriteBatch batch = context.batch();
        BitmapFont font = context.font();
        GlyphLayout gl = context.glyphLayout();
        OrthographicCamera hudCamera = context.hudCamera();
        float w = hudCamera.viewportWidth;
        float h = hudCamera.viewportHeight;

        int rows = Math.max(1, Math.min(10, highScoreRows.size()));
        float panelW = Math.min(520f, w - 40f);
        float panelH = Math.min(h - 40f, 190f + rows * ROW_H);
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
        top = skin.banner(batch, font, gl, "HALL OF FAME", centreX, top) - 8f;
        font.getData().setScale(0.8f);
        skin.centred(batch, font, gl, "TOP SCORERS", skin.colour(MenuTheme.Swatch.MUTED), centreX, top);
        font.getData().setScale(1f);
        top -= 26f;

        if (highScoreRows.isEmpty()) {
            skin.centred(batch, font, gl, "No saved scores yet", skin.colour(MenuTheme.Swatch.MUTED), centreX, top);
        } else {
            for (int i = 0; i < rows; i++) {
                Score row = highScoreRows.get(i);
                skin.centred(batch, font, gl,
                        String.format(Locale.ROOT, "%d. %s: %d", i + 1, row.getName(), row.getTheScore()),
                        skin.colour(i == 0 ? MenuTheme.Swatch.PRICE : MenuTheme.Swatch.TEXT), centreX, top);
                top -= ROW_H;
            }
        }

        skin.hazard(batch, left, panelY + INSET + 18f, contentW);
        font.getData().setScale(0.85f);
        skin.centred(batch, font, gl, "Press ESC to continue", skin.colour(MenuTheme.Swatch.ACCENT), centreX,
                panelY + INSET + 8f);
        font.getData().setScale(1f);
        batch.end();
        restoreFont.run();
    }

    public record RenderContext(
            SpriteBatch batch,
            BitmapFont font,
            GlyphLayout glyphLayout,
            OrthographicCamera hudCamera,
            Function<String, Texture> textures) {
    }
}
