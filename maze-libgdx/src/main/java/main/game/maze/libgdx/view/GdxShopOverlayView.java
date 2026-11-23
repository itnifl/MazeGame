package main.game.maze.libgdx.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import main.game.maze.game.items.PickupCatalog;
import main.game.maze.game.shop.ShopMenu;

/**
 * View of the shop between levels (#164) in libGDX, drawn over the win screen; the counterpart of the JavaFX
 * {@code shopScreen.fxml}. It only draws the shared {@link ShopMenu} and returns where its rows and buttons are, for the
 * {@code GdxShopOverlayController} to hit-test clicks. HUD space (y up).
 */
public final class GdxShopOverlayView {

    private static final Color GOLD = new Color(1f, 0.90f, 0.43f, 1f);
    private static final Color CYAN = new Color(0.56f, 1.0f, 0.88f, 1f);
    private static final Color GREY = new Color(0.49f, 0.52f, 0.56f, 1f);
    private static final Color RED = new Color(1f, 0.42f, 0.37f, 1f);
    private static final float ROW_HEIGHT = 34f;
    private static final float ICON = 28f;
    private static final float BUTTON_W = 110f;
    private static final float BUTTON_H = 34f;

    /** An area on the screen. */
    public record Rect(float x, float y, float w, float h) {
        public boolean contains(float px, float py) {
            return px >= x && px <= x + w && py >= y && py <= y + h;
        }
    }

    /** Where the rows (in menu order) and the buttons were drawn. */
    public record ShopLayout(List<Rect> rows, Rect buy, Rect done) {
        public ShopLayout {
            rows = List.copyOf(rows);
        }
    }

    /** What the view draws with. */
    public record RenderContext(SpriteBatch batch, ShapeRenderer shapes, BitmapFont font, GlyphLayout glyphLayout,
                                OrthographicCamera hudCamera, Function<String, Texture> textures, PickupCatalog items) {
    }

    public ShopLayout render(RenderContext context, ShopMenu menu) {
        float w = context.hudCamera().viewportWidth;
        float h = context.hudCamera().viewportHeight;
        List<ShopMenu.Row> rows = menu.rows();
        float panelW = Math.min(620f, w - 40f);
        float panelH = Math.min(h - 40f, 150f + rows.size() * ROW_HEIGHT + BUTTON_H);
        float panelX = (w - panelW) / 2f;
        float panelY = (h - panelH) / 2f;
        float rowsTop = panelY + panelH - 84f;

        List<Rect> rowRects = new ArrayList<>(rows.size());
        for (int i = 0; i < rows.size(); i++) {
            rowRects.add(new Rect(panelX + 14f, rowsTop - (i + 1) * ROW_HEIGHT, panelW - 28f, ROW_HEIGHT - 2f));
        }
        float buttonsY = panelY + 14f;
        Rect buy = new Rect(panelX + panelW / 2f - BUTTON_W - 8f, buttonsY, BUTTON_W, BUTTON_H);
        Rect done = new Rect(panelX + panelW / 2f + 8f, buttonsY, BUTTON_W, BUTTON_H);

        ShapeRenderer shapes = context.shapes();
        shapes.setProjectionMatrix(context.hudCamera().combined);
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0f, 0f, 0f, 0.92f);
        shapes.rect(panelX, panelY, panelW, panelH);
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).selected()) {
                Rect r = rowRects.get(i);
                shapes.setColor(1f, 0.90f, 0.43f, 0.22f);
                shapes.rect(r.x(), r.y(), r.w(), r.h());
            }
        }
        shapes.setColor(0.29f, 0.87f, 0.50f, 1f);
        shapes.rect(buy.x(), buy.y(), buy.w(), buy.h());
        shapes.setColor(GOLD);
        shapes.rect(done.x(), done.y(), done.w(), done.h());
        shapes.end();
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(GOLD);
        shapes.rect(panelX, panelY, panelW, panelH);
        shapes.end();

        SpriteBatch batch = context.batch();
        BitmapFont font = context.font();
        batch.setProjectionMatrix(context.hudCamera().combined);
        batch.begin();
        font.getData().setScale(1.5f);
        centred(context, ShopMenu.TITLE, GOLD, panelX, panelW, panelY + panelH - 16f);
        font.getData().setScale(1.0f);
        centred(context, menu.creditsText(), CYAN, panelX, panelW, panelY + panelH - 50f);
        for (int i = 0; i < rows.size(); i++) {
            drawRow(context, rows.get(i), rowRects.get(i));
        }
        centred(context, menu.status(), Color.WHITE, panelX, panelW, buttonsY + BUTTON_H + 36f);
        centred(context, ShopMenu.HELP, GREY, panelX, panelW, buttonsY + BUTTON_H + 18f);
        font.setColor(new Color(0.02f, 0.19f, 0.06f, 1f));
        label(context, "Buy", buy);
        font.setColor(new Color(0.18f, 0.11f, 0f, 1f));
        label(context, "Done", done);
        batch.end();
        return new ShopLayout(rowRects, buy, done);
    }

    private static void drawRow(RenderContext context, ShopMenu.Row row, Rect r) {
        BitmapFont font = context.font();
        Texture icon = context.textures().apply(ShopMenu.iconOf(row.item().kind(), context.items()));
        float textY = r.y() + r.h() / 2f + 6f;
        if (icon != null) {
            context.batch().setColor(Color.WHITE);
            context.batch().draw(icon, r.x() + 4f, r.y() + (r.h() - ICON) / 2f, ICON, ICON);
        }
        font.setColor(row.affordable() ? Color.WHITE : GREY);
        font.draw(context.batch(), row.title(), r.x() + ICON + 14f, textY);
        font.setColor(GREY);
        font.draw(context.batch(), row.item().tier().label(), r.x() + r.w() * 0.55f, textY);
        font.setColor(row.locked() ? GREY : row.affordable() ? GOLD : RED);
        font.draw(context.batch(), Integer.toString(row.item().price()), r.x() + r.w() * 0.72f, textY);
        font.setColor(row.locked() ? GREY : CYAN);
        font.draw(context.batch(), row.ownedText(), r.x() + r.w() * 0.84f, textY);
    }

    private static void centred(RenderContext context, String text, Color colour, float panelX, float panelW, float y) {
        if (text == null || text.isEmpty()) {
            return;
        }
        context.font().setColor(colour);
        context.glyphLayout().setText(context.font(), text);
        context.font().draw(context.batch(), text, panelX + (panelW - context.glyphLayout().width) / 2f, y);
    }

    private static void label(RenderContext context, String text, Rect button) {
        context.glyphLayout().setText(context.font(), text);
        context.font().draw(context.batch(), text, button.x() + (button.w() - context.glyphLayout().width) / 2f,
                button.y() + button.h() / 2f + 6f);
    }
}
