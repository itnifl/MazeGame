package main.game.maze.libgdx.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.List;
import java.util.function.Function;
import main.game.maze.game.theme.ControlsGuide;
import main.game.maze.game.theme.MenuButton;
import main.game.maze.game.theme.MenuTheme;
import main.game.maze.game.theme.MenuThemeLoader;

/**
 * The help screen of the main menu ("Help & Controls") in libGDX, like the JavaFX {@code helpScreen.fxml}: the shared
 * {@link ControlsGuide} as keyboard keys (the arrow keys as a cross) with a picture of what each key does, on the menu
 * theme ({@link GdxMenuSkin}). Returns where the Back button is. HUD space (y up).
 */
public final class GdxHelpView {

    static final float ROW_H = 40f;
    static final float ROW_GAP = 3f;
    static final float KEY = 22f;
    private static final float INSET = 24f;
    private static final float KEYS_W = 140f;
    private static final float ICON = 22f;
    private static final float TIP_H = 20f;
    private static final float BUTTON_W = 120f;
    private static final float BUTTON_H = 36f;

    /** What the view draws with. */
    public record RenderContext(SpriteBatch batch, BitmapFont font, GlyphLayout glyphLayout, OrthographicCamera hudCamera,
                                Function<String, Texture> textures) {
    }

    private GdxMenuSkin skin;

    /** The panel height the guide needs. */
    static float panelHeight() {
        return INSET + 18f + GdxMenuSkin.BANNER_H + 6f + 18f + ControlsGuide.entries().size() * (ROW_H + ROW_GAP)
                + ControlsGuide.tips().size() * TIP_H + 16f + BUTTON_H + INSET;
    }

    public GdxShopOverlayView.Rect render(RenderContext context) {
        if (skin == null) {
            skin = new GdxMenuSkin(MenuThemeLoader.loadDefault(), context.textures());
        }
        SpriteBatch batch = context.batch();
        BitmapFont font = context.font();
        GlyphLayout glyphs = context.glyphLayout();
        float w = context.hudCamera().viewportWidth;
        float h = context.hudCamera().viewportHeight;
        float panelW = Math.min(700f, w - 20f);
        float panelH = Math.min(h - 8f, panelHeight());
        float panelX = (w - panelW) / 2f;
        float panelY = (h - panelH) / 2f;
        float left = panelX + INSET + 4f;
        float contentW = panelW - 2f * (INSET + 4f);
        float centreX = w / 2f;

        batch.setProjectionMatrix(context.hudCamera().combined);
        batch.begin();
        Runnable restoreFont = GdxMenuSkin.smoothText(font);
        skin.plate(batch, MenuTheme.Art.BACKDROP, 0f, 0f, w, h);
        skin.plate(batch, MenuTheme.Art.VIGNETTE, 0f, 0f, w, h);
        skin.plate(batch, MenuTheme.Art.PANEL, panelX, panelY, panelW, panelH);

        float top = panelY + panelH - INSET;
        skin.hazard(batch, left, top - skin.hazardHeight(), contentW);
        top -= 18f;
        top = skin.banner(batch, font, glyphs, ControlsGuide.TITLE, centreX, top) - 6f;
        font.getData().setScale(0.8f);
        skin.centred(batch, font, glyphs, ControlsGuide.SUBTITLE, skin.colour(MenuTheme.Swatch.MUTED), centreX, top);
        top -= 18f;

        for (ControlsGuide.Entry entry : ControlsGuide.entries()) {
            row(skin, batch, font, glyphs, entry, left, top - ROW_H, contentW);
            top -= ROW_H + ROW_GAP;
        }
        font.getData().setScale(0.85f);
        for (ControlsGuide.Tip tip : ControlsGuide.tips()) {
            skin.icon(batch, tip.icon(), left + 4f, top - 17f, 16f);
            skin.text(batch, font, tip.text(), skin.colour(MenuTheme.Swatch.ACCENT), left + 26f, top - 4f);
            top -= TIP_H;
        }

        float buttonY = panelY + INSET;
        skin.hazard(batch, left, buttonY + BUTTON_H + 8f, contentW);
        GdxShopOverlayView.Rect back = new GdxShopOverlayView.Rect(centreX - BUTTON_W / 2f, buttonY, BUTTON_W, BUTTON_H);
        font.getData().setScale(1f);
        skin.button(batch, font, glyphs, MenuButton.BACK, back.x(), back.y(), back.w(), back.h(), true);
        font.getData().setScale(1f);
        batch.end();
        restoreFont.run();
        return back;
    }

    /** One key and what it does: the keys, the picture in its well, the title and the sentence, on a crate card. */
    private static void row(GdxMenuSkin skin, SpriteBatch batch, BitmapFont font, GlyphLayout glyphs,
                            ControlsGuide.Entry entry, float x, float y, float w) {
        skin.plate(batch, MenuTheme.Art.CARD, x, y, w, ROW_H);
        float keysRight = x + KEYS_W;
        if (entry.arrows()) {
            // the arrow keys as on a keyboard: up above, left, down and right below
            float cx = keysRight - 1.5f * KEY - 2f;
            float lower = y + (ROW_H - 2f * KEY - 2f) / 2f;
            skin.plate(batch, MenuTheme.Art.KEY_UP, cx - KEY / 2f, lower + KEY + 2f, KEY, KEY);
            skin.plate(batch, MenuTheme.Art.KEY_LEFT, cx - 1.5f * KEY - 2f, lower, KEY, KEY);
            skin.plate(batch, MenuTheme.Art.KEY_DOWN, cx - KEY / 2f, lower, KEY, KEY);
            skin.plate(batch, MenuTheme.Art.KEY_RIGHT, cx + KEY / 2f + 2f, lower, KEY, KEY);
        } else {
            keycaps(skin, batch, font, glyphs, entry.keys(), keysRight, y + (ROW_H - KEY - 6f) / 2f);
        }
        float slotX = keysRight + 10f;
        if (entry.icon() != null) {
            skin.plate(batch, MenuTheme.Art.SLOT, slotX, y + (ROW_H - ICON - 8f) / 2f, ICON + 8f, ICON + 8f);
            skin.icon(batch, entry.icon(), slotX + 4f, y + (ROW_H - ICON) / 2f, ICON);
        }
        float textX = slotX + ICON + 18f;
        font.getData().setScale(0.9f);
        skin.text(batch, font, entry.title(), skin.colour(MenuTheme.Swatch.TEXT), textX, y + ROW_H - 6f);
        font.getData().setScale(0.78f);
        skin.text(batch, font, entry.text(), skin.colour(MenuTheme.Swatch.MUTED), textX, y + ROW_H - 22f);
    }

    /** The keys as keycaps with their names, ending at {@code right}. */
    private static void keycaps(GdxMenuSkin skin, SpriteBatch batch, BitmapFont font, GlyphLayout glyphs,
                                List<String> keys, float right, float y) {
        font.getData().setScale(0.8f);
        float x = right;
        for (int i = keys.size() - 1; i >= 0; i--) {
            glyphs.setText(font, keys.get(i));
            float capW = Math.max(KEY + 6f, glyphs.width + 18f);
            x -= capW;
            skin.plate(batch, MenuTheme.Art.KEY, x, y, capW, KEY + 6f);
            font.setColor(skin.colour(MenuTheme.Swatch.KEY_TEXT));
            font.draw(batch, keys.get(i), x + (capW - glyphs.width) / 2f, y + KEY + 6f - (KEY + 6f - glyphs.height) / 2f + 2f);
            x -= 4f;
        }
    }
}
