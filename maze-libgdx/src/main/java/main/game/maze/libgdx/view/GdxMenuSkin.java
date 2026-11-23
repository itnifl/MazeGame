package main.game.maze.libgdx.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import main.game.maze.game.shop.ShopTier;
import main.game.maze.game.theme.MenuButton;
import main.game.maze.game.theme.MenuTheme;

/**
 * The menu theme (the shared {@link MenuTheme} model) for the libGDX screens between levels: its 9-slice plates as
 * {@link NinePatch}es, the tiled hazard strip, its colours, and text with a drop shadow. The JavaFX screens draw from
 * the same theme ({@code FxMenuTheme}). A texture that cannot be loaded (no GL context in tests) is simply not drawn.
 * Draw between {@code batch.begin()} and {@code batch.end()}, HUD space (y up).
 */
public final class GdxMenuSkin {

    /** The height of the title plate. */
    public static final float BANNER_H = 40f;
    private static final Color DIMMED = new Color(0.45f, 0.45f, 0.45f, 1f);

    private final MenuTheme theme;
    private final Function<String, Texture> textures;
    private final Map<MenuTheme.Art, NinePatch> patches = new EnumMap<>(MenuTheme.Art.class);
    private final Map<MenuTheme.Swatch, Color> colours = new EnumMap<>(MenuTheme.Swatch.class);
    private final Map<ShopTier, Color> badgeColours = new EnumMap<>(ShopTier.class);
    private final Map<String, Texture> loaded = new HashMap<>();

    public GdxMenuSkin(MenuTheme theme, Function<String, Texture> textures) {
        this.theme = theme;
        this.textures = textures;
        for (MenuTheme.Swatch swatch : MenuTheme.Swatch.values()) {
            colours.put(swatch, color(theme.colour(swatch)));
        }
        for (ShopTier tier : ShopTier.values()) {
            badgeColours.put(tier, color(theme.badge(tier).colour()));
        }
    }

    public MenuTheme theme() {
        return theme;
    }

    /** A {@code #rrggbb} or {@code #rrggbbaa} colour of the theme as a libGDX colour. */
    public static Color color(String hex) {
        float[] c = MenuTheme.rgba(hex);
        return new Color(c[0], c[1], c[2], c[3]);
    }

    public Color colour(MenuTheme.Swatch swatch) {
        return colours.get(swatch);
    }

    public Color badgeColour(ShopTier tier) {
        return badgeColours.get(tier);
    }

    /** A texture by classpath path (loaded once), or null when it cannot be loaded. */
    public Texture texture(String path) {
        if (path == null || textures == null) {
            return null;
        }
        return loaded.computeIfAbsent(path, textures);
    }

    public Texture texture(MenuTheme.Art art) {
        return texture(theme.image(art).path());
    }

    /** Draws a texture of the theme over (x, y, w, h): as a 9-slice plate when it has a slice, else stretched. */
    public void plate(SpriteBatch batch, MenuTheme.Art art, float x, float y, float w, float h) {
        plate(batch, art, x, y, w, h, Color.WHITE);
    }

    /** As {@link #plate(SpriteBatch, MenuTheme.Art, float, float, float, float)}, tinted. */
    public void plate(SpriteBatch batch, MenuTheme.Art art, float x, float y, float w, float h, Color tint) {
        MenuTheme.Image image = theme.image(art);
        Texture texture = texture(image.path());
        if (texture == null) {
            return;
        }
        batch.setColor(tint);
        if (image.slice() <= 0) {
            batch.draw(texture, x, y, w, h);
        } else {
            patches.computeIfAbsent(art,
                    a -> new NinePatch(texture, image.slice(), image.slice(), image.slice(), image.slice()))
                    .draw(batch, x, y, w, h);
        }
        batch.setColor(Color.WHITE);
    }

    /** The hazard strip along (x, y) to (x + w), tiled at its own size. */
    public void hazard(SpriteBatch batch, float x, float y, float w) {
        Texture texture = texture(MenuTheme.Art.HAZARD_STRIP);
        if (texture == null) {
            return;
        }
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
        batch.setColor(Color.WHITE);
        batch.draw(texture, x, y, w, texture.getHeight(), 0f, 1f, w / texture.getWidth(), 0f);
    }

    /** The height of the hazard strip. */
    public float hazardHeight() {
        Texture texture = texture(MenuTheme.Art.HAZARD_STRIP);
        return texture == null ? 12f : texture.getHeight();
    }

    /** An icon (any classpath image) in a square of {@code size} at (x, y). */
    public void icon(SpriteBatch batch, String path, float x, float y, float size) {
        Texture texture = texture(path);
        if (texture != null) {
            batch.setColor(Color.WHITE);
            batch.draw(texture, x, y, size, size);
        }
    }

    /** Text whose baseline-top is at y (libGDX draws text down from y), with the theme's drop shadow. */
    public void text(SpriteBatch batch, BitmapFont font, String text, Color colour, float x, float y) {
        if (text == null || text.isEmpty()) {
            return;
        }
        font.setColor(colour(MenuTheme.Swatch.SHADOW));
        font.draw(batch, text, x + 1f, y - 1.5f);
        font.setColor(colour);
        font.draw(batch, text, x, y);
    }

    /** Text centred on {@code centreX}. */
    public void centred(SpriteBatch batch, BitmapFont font, GlyphLayout layout, String text, Color colour, float centreX,
                        float y) {
        if (text == null || text.isEmpty()) {
            return;
        }
        layout.setText(font, text);
        text(batch, font, text, colour, centreX - layout.width / 2f, y);
    }

    /** Text ending at {@code rightX}. */
    public void rightAligned(SpriteBatch batch, BitmapFont font, GlyphLayout layout, String text, Color colour,
                             float rightX, float y) {
        if (text == null || text.isEmpty()) {
            return;
        }
        layout.setText(font, text);
        text(batch, font, text, colour, rightX - layout.width, y);
    }

    /**
     * Draws the font smoothly (linear filtering) until the returned action is run, which puts the filters back: the
     * menus scale the bitmap font down, where the default nearest filtering drops thin letters. The game's own HUD text
     * is not changed.
     */
    public static Runnable smoothText(BitmapFont font) {
        java.util.List<Runnable> restore = new java.util.ArrayList<>();
        for (com.badlogic.gdx.graphics.g2d.TextureRegion region : font.getRegions()) {
            Texture texture = region.getTexture();
            Texture.TextureFilter min = texture.getMinFilter();
            Texture.TextureFilter mag = texture.getMagFilter();
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            restore.add(() -> texture.setFilter(min, mag));
        }
        return () -> restore.forEach(Runnable::run);
    }

    /** The olive title plate with {@code title} on it, centred under {@code top}; returns the y of its bottom. */
    public float banner(SpriteBatch batch, BitmapFont font, GlyphLayout layout, String title, float centreX, float top) {
        font.getData().setScale(1.7f);
        layout.setText(font, title);
        float width = layout.width + 70f;
        plate(batch, MenuTheme.Art.BANNER, centreX - width / 2f, top - BANNER_H, width, BANNER_H);
        centred(batch, font, layout, title, colour(MenuTheme.Swatch.TITLE), centreX, top - (BANNER_H - layout.height) / 2f);
        font.getData().setScale(1f);
        return top - BANNER_H;
    }

    /** A button: its metal plate and its label, centred. {@code enabled} false draws it dimmed. */
    public void button(SpriteBatch batch, BitmapFont font, GlyphLayout layout, MenuButton kind, float x, float y, float w,
                       float h, boolean enabled) {
        plate(batch, kind.art(), x, y, w, h, enabled ? Color.WHITE : DIMMED);
        layout.setText(font, kind.label());
        text(batch, font, kind.label(), colour(MenuTheme.Swatch.BUTTON_TEXT), x + (w - layout.width) / 2f,
                y + (h + layout.height) / 2f);
    }
}
