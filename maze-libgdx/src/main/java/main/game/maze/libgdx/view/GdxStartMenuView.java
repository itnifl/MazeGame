package main.game.maze.libgdx.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.List;
import java.util.function.Function;
import main.game.maze.game.theme.MenuButton;
import main.game.maze.game.theme.MenuTheme;
import main.game.maze.game.theme.MenuThemeLoader;
import main.game.maze.game.theme.StartMenuText;
import main.game.maze.libgdx.view.layout.MenuLayout;

/**
 * Renders the main menu in the shared menu theme ({@link GdxMenuSkin}), like the JavaFX {@code startScreen.fxml}: the
 * zombie backdrop, the title on the olive plate, a riveted panel with hazard stripes, the difficulty picker as a crate
 * card (its list of crate cards below it when open) and metal-plate buttons. Returns the per-frame layout snapshot for
 * input hit-testing (the options of the open picker are {@code comboH} tall each, under it). HUD space (y up).
 */
public final class GdxStartMenuView {

    private static final float PANEL_W = 600f;
    private static final float PANEL_H = 360f;
    private static final float INSET = 26f;

    private GdxMenuSkin skin;

    private GdxMenuSkin skin(RenderContext context) {
        if (skin == null) {
            skin = new GdxMenuSkin(MenuThemeLoader.loadDefault(), context.textures());
        }
        return skin;
    }

    public MenuLayout render(RenderContext context) {
        GdxMenuSkin skin = skin(context);
        SpriteBatch batch = context.batch();
        BitmapFont font = context.font();
        GlyphLayout glyphs = context.glyphLayout();
        OrthographicCamera hudCamera = context.hudCamera();
        float w = hudCamera.viewportWidth;
        float h = hudCamera.viewportHeight;

        float panelW = Math.min(PANEL_W, w - 40f);
        float panelH = PANEL_H;
        float panelX = (w - panelW) * 0.5f;
        float panelY = (h - panelH) * 0.5f - 34f;
        float centreX = w / 2f;
        float left = panelX + INSET + 4f;
        float contentW = panelW - 2f * (INSET + 4f);

        float comboW = Math.min(360f, contentW);
        float comboH = 44f;
        float comboX = centreX - comboW / 2f;
        float comboY = panelY + panelH - INSET - 18f - 30f - comboH;

        float buttonW = 180f;
        float buttonH = 44f;
        float buttonY = comboY - 18f - buttonH;
        float buttonX = centreX - buttonW - 7f;
        float highScoresX = centreX + 7f;
        float loadW = buttonW;
        float loadH = 40f;
        float loadX = buttonX;
        float loadY = buttonY - 12f - loadH;

        MenuLayout layout = new MenuLayout(comboX, comboY, comboW, comboH, buttonX, buttonY, buttonW, buttonH,
                highScoresX, buttonY, buttonW, buttonH, loadX, loadY, loadW, loadH, highScoresX, loadY, buttonW, loadH);

        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        Runnable restoreFont = GdxMenuSkin.smoothText(font);
        skin.plate(batch, MenuTheme.Art.BACKDROP, 0f, 0f, w, h);
        skin.plate(batch, MenuTheme.Art.VIGNETTE, 0f, 0f, w, h);

        // the title on its plate, the subtitle under it
        float titleTop = Math.min(h - 18f, panelY + panelH + 120f);
        float underTitle = skin.banner(batch, font, glyphs, StartMenuText.TITLE, 2.6f, 62f, centreX, titleTop);
        font.getData().setScale(1.15f);
        skin.centred(batch, font, glyphs, StartMenuText.SUBTITLE, skin.colour(MenuTheme.Swatch.ACCENT), centreX,
                underTitle - 8f);

        skin.plate(batch, MenuTheme.Art.PANEL, panelX, panelY, panelW, panelH);
        float top = panelY + panelH - INSET;
        skin.hazard(batch, left, top - skin.hazardHeight(), contentW);
        font.getData().setScale(1.1f);
        skin.centred(batch, font, glyphs, StartMenuText.SELECT_DIFFICULTY, skin.colour(MenuTheme.Swatch.TITLE), centreX,
                top - 22f);

        // the difficulty picker
        skin.plate(batch, context.startMenuDropdownOpen() ? MenuTheme.Art.CARD_SELECTED : MenuTheme.Art.CARD,
                comboX, comboY, comboW, comboH);
        font.getData().setScale(1.2f);
        String selected = selectedName(context);
        skin.text(batch, font, selected, skin.colour(MenuTheme.Swatch.TEXT), comboX + 16f, comboY + comboH / 2f + 9f);
        skin.text(batch, font, context.startMenuDropdownOpen() ? "^" : "v", skin.colour(MenuTheme.Swatch.PRICE),
                comboX + comboW - 26f, comboY + comboH / 2f + 9f);

        // the buttons
        font.getData().setScale(1.1f);
        skin.button(batch, font, glyphs, context.pausedFromGame() ? MenuButton.RESTART_MISSION : MenuButton.START_MISSION,
                buttonX, buttonY, buttonW, buttonH, true);
        skin.button(batch, font, glyphs, MenuButton.HIGH_SCORES, highScoresX, buttonY, buttonW, buttonH, true);
        font.getData().setScale(1f);
        font.getData().setScale(0.9f);
        skin.button(batch, font, glyphs, MenuButton.LOAD, context.loadLabel(), loadX, loadY, loadW, loadH,
                context.loadAvailable());
        skin.button(batch, font, glyphs, MenuButton.HELP, highScoresX, loadY, buttonW, loadH, true);

        // the keys, the difficulty, what went wrong
        font.getData().setScale(0.85f);
        skin.wrapped(batch, font, StartMenuText.controls(context.pausedFromGame()), skin.colour(MenuTheme.Swatch.MUTED),
                left, loadY - 12f, contentW);
        skin.centred(batch, font, glyphs, context.selectedDifficultySummary(), skin.colour(MenuTheme.Swatch.ACCENT),
                centreX, panelY + INSET + 42f);
        skin.centred(batch, font, glyphs, context.statusMessage(), skin.colour(MenuTheme.Swatch.BAD), centreX,
                panelY + INSET + 26f);
        skin.hazard(batch, left, panelY + INSET, contentW);

        font.getData().setScale(1f);
        skin.centred(batch, font, glyphs, StartMenuText.TAGLINE, skin.colour(MenuTheme.Swatch.PRICE), centreX,
                panelY - 14f);

        if (context.loadingPending()) {
            font.getData().setScale(2.0f);
            skin.centred(batch, font, glyphs, "Loading ...", skin.colour(MenuTheme.Swatch.TITLE), centreX,
                    panelY + panelH / 2f + 16f);
        }

        if (context.startMenuDropdownOpen() && !context.difficultyNames().isEmpty()) {
            drawOptions(skin, batch, font, context, layout);
        }
        font.getData().setScale(1f);
        batch.end();
        restoreFont.run();
        return layout;
    }

    /** The open picker: one crate card per difficulty under it, the chosen one lit. */
    private static void drawOptions(GdxMenuSkin skin, SpriteBatch batch, BitmapFont font, RenderContext context,
                                    MenuLayout layout) {
        font.getData().setScale(1.1f);
        List<String> names = context.difficultyNames();
        // a dark well behind the list, so nothing under it shows between the cards
        float listH = names.size() * layout.comboH();
        skin.plate(batch, MenuTheme.Art.SLOT, layout.comboX() - 3f, layout.comboY() - listH - 3f, layout.comboW() + 6f,
                listH + 3f);
        for (int i = 0; i < names.size(); i++) {
            float y = layout.comboY() - (i + 1) * layout.comboH();
            boolean chosen = i == context.selectedDifficultyIndex();
            skin.plate(batch, chosen ? MenuTheme.Art.CARD_SELECTED : MenuTheme.Art.CARD, layout.comboX(), y,
                    layout.comboW(), layout.comboH());
            skin.text(batch, font, names.get(i), skin.colour(chosen ? MenuTheme.Swatch.TITLE : MenuTheme.Swatch.TEXT),
                    layout.comboX() + 16f, y + layout.comboH() / 2f + 8f);
        }
    }

    private static String selectedName(RenderContext context) {
        int index = context.selectedDifficultyIndex();
        return index >= 0 && index < context.difficultyNames().size() ? context.difficultyNames().get(index) : "Easy";
    }

    public record RenderContext(
            SpriteBatch batch,
            BitmapFont font,
            GlyphLayout glyphLayout,
            OrthographicCamera hudCamera,
            Function<String, Texture> textures,
            List<String> difficultyNames,
            int selectedDifficultyIndex,
            boolean startMenuDropdownOpen,
            boolean pausedFromGame,
            boolean loadingPending,
            String selectedDifficultySummary,
            String statusMessage,
            boolean loadAvailable,
            String loadLabel) {
    }
}
