package main.game.maze.libgdx.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import main.game.maze.game.items.PickupCatalog;
import main.game.maze.game.shop.ShopMenu;
import main.game.maze.game.theme.MenuButton;
import main.game.maze.game.theme.MenuTheme;
import main.game.maze.game.theme.MenuThemeLoader;

/**
 * View of the shop between levels (#164) in libGDX, drawn over the win screen; the counterpart of the JavaFX
 * {@code shopScreen.fxml}. It draws the shared {@link ShopMenu} as a supply depot in the shared menu theme
 * ({@link GdxMenuSkin}): a shelf per tier with its rank badge, an ammo-crate card per item, the skull-coin prices, and
 * returns where its cards and buttons are, for the {@code GdxShopOverlayController} to hit-test clicks. HUD space (y up).
 */
public final class GdxShopOverlayView {

    static final float CARD_H = 44f;
    static final float CARD_GAP = 4f;
    static final float SHELF_GAP = 8f;
    static final float BUTTON_W = 110f;
    static final float BUTTON_H = 36f;
    private static final float INSET = 24f;
    private static final float SHELF_HEADER = 24f;
    private static final float SLOT = 32f;
    private static final float ICON = 24f;
    private static final float TOKEN = 14f;

    /** An area on the screen. */
    public record Rect(float x, float y, float w, float h) {
        public boolean contains(float px, float py) {
            return px >= x && px <= x + w && py >= y && py <= y + h;
        }
    }

    /** Where the cards (in menu order, as {@link ShopMenu#rows()}) and the buttons were drawn. */
    public record ShopLayout(List<Rect> rows, Rect buy, Rect done) {
        public ShopLayout {
            rows = List.copyOf(rows);
        }
    }

    /** What the view draws with. */
    public record RenderContext(SpriteBatch batch, BitmapFont font, GlyphLayout glyphLayout,
                                OrthographicCamera hudCamera, Function<String, Texture> textures, PickupCatalog items) {
    }

    private GdxMenuSkin skin;

    private GdxMenuSkin skin(RenderContext context) {
        if (skin == null) {
            skin = new GdxMenuSkin(MenuThemeLoader.loadDefault(), context.textures());
        }
        return skin;
    }

    /** The panel height the menu needs: header, the tallest shelf, the text lines and the buttons. */
    static float panelHeight(ShopMenu menu) {
        int tallest = menu.shelves().stream().mapToInt(shelf -> shelf.items().size()).max().orElse(0);
        return INSET + 18f + GdxMenuSkin.BANNER_H + 6f + 20f + 32f + SHELF_HEADER + tallest * (CARD_H + CARD_GAP) + 6f + 54f + 28f
                + BUTTON_H + INSET;
    }

    public ShopLayout render(RenderContext context, ShopMenu menu) {
        GdxMenuSkin skin = skin(context);
        SpriteBatch batch = context.batch();
        BitmapFont font = context.font();
        GlyphLayout glyphs = context.glyphLayout();
        float w = context.hudCamera().viewportWidth;
        float h = context.hudCamera().viewportHeight;
        float panelW = Math.min(780f, w - 20f);
        float panelH = Math.min(h - 10f, panelHeight(menu));
        float panelX = (w - panelW) / 2f;
        float panelY = (h - panelH) / 2f;
        float left = panelX + INSET + 4f;
        float contentW = panelW - 2f * (INSET + 4f);
        float centreX = panelX + panelW / 2f;

        batch.setProjectionMatrix(context.hudCamera().combined);
        batch.begin();
        Runnable restoreFont = GdxMenuSkin.smoothText(font);
        skin.plate(batch, MenuTheme.Art.BACKDROP, 0f, 0f, w, h);
        skin.plate(batch, MenuTheme.Art.VIGNETTE, 0f, 0f, w, h);
        skin.plate(batch, MenuTheme.Art.PANEL, panelX, panelY, panelW, panelH);

        float top = panelY + panelH - INSET;
        skin.hazard(batch, left, top - skin.hazardHeight(), contentW);
        top -= 18f;
        top = skin.banner(batch, font, glyphs, ShopMenu.TITLE, centreX, top) - 6f;
        font.getData().setScale(0.8f);
        skin.centred(batch, font, glyphs, ShopMenu.SUBTITLE, skin.colour(MenuTheme.Swatch.MUTED), centreX, top);
        top -= 20f;
        font.getData().setScale(1.15f);
        glyphs.setText(font, menu.creditsText());
        float creditsX = centreX - (20f + 8f + glyphs.width) / 2f;
        skin.icon(batch, skin.theme().image(MenuTheme.Art.TOKEN).path(), creditsX, top - 20f, 20f);
        skin.text(batch, font, menu.creditsText(), skin.colour(MenuTheme.Swatch.ACCENT), creditsX + 28f, top - 3f);
        top -= 32f;

        Rect[] rects = new Rect[menu.rows().size()];
        List<ShopMenu.Shelf> shelves = menu.shelves();
        float shelfW = shelves.isEmpty() ? contentW : (contentW - (shelves.size() - 1) * SHELF_GAP) / shelves.size();
        int tallest = 0;
        for (int s = 0; s < shelves.size(); s++) {
            ShopMenu.Shelf shelf = shelves.get(s);
            float shelfX = left + s * (shelfW + SHELF_GAP);
            shelfHeader(skin, batch, font, glyphs, shelf, shelfX, top);
            float cardTop = top - SHELF_HEADER;
            for (ShopMenu.ShelfItem item : shelf.items()) {
                Rect card = new Rect(shelfX, cardTop - CARD_H, shelfW, CARD_H);
                rects[item.index()] = card;
                card(skin, batch, font, glyphs, item.row(), card, context.items());
                cardTop -= CARD_H + CARD_GAP;
            }
            tallest = Math.max(tallest, shelf.items().size());
        }
        top -= SHELF_HEADER + tallest * (CARD_H + CARD_GAP) + 6f;

        font.getData().setScale(0.85f);
        skin.centred(batch, font, glyphs, menu.briefing(), skin.colour(MenuTheme.Swatch.TEXT), centreX, top);
        top -= 18f;
        MenuTheme.Swatch statusColour = menu.statusIsGood() ? MenuTheme.Swatch.GOOD : MenuTheme.Swatch.BAD;
        skin.centred(batch, font, glyphs, menu.status(), skin.colour(statusColour), centreX, top);
        top -= 18f;
        font.getData().setScale(0.75f);
        skin.centred(batch, font, glyphs, ShopMenu.HELP, skin.colour(MenuTheme.Swatch.MUTED), centreX, top);

        float buttonsY = panelY + INSET;
        skin.hazard(batch, left, buttonsY + BUTTON_H + 8f, contentW);
        Rect buy = new Rect(centreX - BUTTON_W - 8f, buttonsY, BUTTON_W, BUTTON_H);
        Rect done = new Rect(centreX + 8f, buttonsY, BUTTON_W, BUTTON_H);
        font.getData().setScale(1f);
        skin.button(batch, font, glyphs, MenuButton.BUY, buy.x(), buy.y(), buy.w(), buy.h(), true);
        skin.button(batch, font, glyphs, MenuButton.DONE, done.x(), done.y(), done.w(), done.h(), true);
        font.getData().setScale(1f);
        batch.end();
        restoreFont.run();
        return new ShopLayout(Arrays.stream(rects).map(r -> r == null ? new Rect(0, 0, 0, 0) : r).toList(), buy, done);
    }

    private static void shelfHeader(GdxMenuSkin skin, SpriteBatch batch, BitmapFont font, GlyphLayout glyphs,
                                    ShopMenu.Shelf shelf, float x, float top) {
        skin.icon(batch, skin.theme().badge(shelf.tier()).image(), x, top - 19f, 18f);
        font.getData().setScale(0.95f);
        String name = shelf.tier().label().toUpperCase(Locale.ROOT);
        skin.text(batch, font, name, skin.badgeColour(shelf.tier()), x + 24f, top - 4f);
        if (shelf.locked()) {
            glyphs.setText(font, name);
            float lockX = x + 24f + glyphs.width + 8f;
            skin.icon(batch, skin.theme().image(MenuTheme.Art.LOCK).path(), lockX, top - 17f, TOKEN);
            font.getData().setScale(0.8f);
            skin.text(batch, font, shelf.lockedText(), skin.colour(MenuTheme.Swatch.MUTED), lockX + TOKEN + 4f, top - 5f);
        }
    }

    private static void card(GdxMenuSkin skin, SpriteBatch batch, BitmapFont font, GlyphLayout glyphs, ShopMenu.Row row,
                             Rect r, PickupCatalog items) {
        MenuTheme.Art plate = row.selected() ? MenuTheme.Art.CARD_SELECTED
                : row.locked() ? MenuTheme.Art.CARD_LOCKED : MenuTheme.Art.CARD;
        skin.plate(batch, plate, r.x(), r.y(), r.w(), r.h(), row.locked() && row.selected() ? LOCKED_SELECTED : Color.WHITE);
        float slotY = r.y() + (r.h() - SLOT) / 2f;
        skin.plate(batch, MenuTheme.Art.SLOT, r.x() + 6f, slotY, SLOT, SLOT);
        skin.icon(batch, ShopMenu.iconOf(row.item().kind(), items), r.x() + 6f + (SLOT - ICON) / 2f,
                slotY + (SLOT - ICON) / 2f, ICON);

        float textX = r.x() + 6f + SLOT + 8f;
        font.getData().setScale(0.85f);
        skin.text(batch, font, row.title(), skin.colour(row.locked() ? MenuTheme.Swatch.MUTED : MenuTheme.Swatch.TEXT),
                textX, r.y() + r.h() - 7f);
        skin.icon(batch, skin.theme().image(MenuTheme.Art.TOKEN).path(), textX, r.y() + 6f, TOKEN);
        MenuTheme.Swatch priceColour = row.locked() ? MenuTheme.Swatch.MUTED
                : row.affordable() ? MenuTheme.Swatch.PRICE : MenuTheme.Swatch.TOO_DEAR;
        skin.text(batch, font, Integer.toString(row.item().price()), skin.colour(priceColour), textX + TOKEN + 4f,
                r.y() + 19f);
        if (!row.locked()) {
            skin.rightAligned(batch, font, glyphs, row.ownedText(), skin.colour(MenuTheme.Swatch.ACCENT),
                    r.x() + r.w() - 8f, r.y() + 19f);
        }
    }

    private static final Color LOCKED_SELECTED = new Color(0.8f, 0.8f, 0.8f, 1f);
}
