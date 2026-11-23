package main.game.maze.game.theme;

import java.util.EnumMap;
import java.util.Map;
import main.game.maze.game.shop.ShopTier;

/**
 * The look of the screens between levels (the win screen and the shop), shared by the JavaFX and libGDX views: the
 * palette, the textures (with their 9-slice insets) and the badge of each shop tier. It comes from the menu theme model
 * ({@link MenuThemeLoader}), and is complete: every {@link Swatch}, {@link Art} and tier has a value.
 */
public record MenuTheme(String font, Map<Swatch, String> swatches, Map<Art, Image> images, Map<ShopTier, Badge> badges) {

    /** The colours, by what they are for. */
    public enum Swatch {
        /** Headings and the banner text. */
        TITLE,
        /** Body text. */
        TEXT,
        /** Help lines, labels, what cannot be bought yet. */
        MUTED,
        /** The score to spend, owned items, the win bonus. */
        ACCENT,
        /** A price the player can pay, the score. */
        PRICE,
        /** A price the player cannot pay. */
        TOO_DEAR,
        /** A good outcome (bought, level cleared). */
        GOOD,
        /** A bad outcome (penalties, refusals). */
        BAD,
        /** The labels on the buttons. */
        BUTTON_TEXT,
        /** The drop shadow under text. */
        SHADOW
    }

    /** The textures, by where they are drawn. */
    public enum Art {
        BACKDROP, VIGNETTE, HAZARD_STRIP, PANEL, BANNER, CARD, CARD_SELECTED, CARD_LOCKED, SLOT, BUTTON_GO, BUTTON_BUY,
        BUTTON_STEEL, BUTTON_DANGER, TOKEN, LOCK
    }

    /** A texture: its classpath path and how many pixels of each edge stay unstretched (0: drawn as it is). */
    public record Image(String path, int slice) {
        public Image {
            if (path == null || path.isBlank() || slice < 0) {
                throw new IllegalArgumentException("a theme image needs a path and a slice >= 0: " + path);
            }
        }
    }

    /** The rank badge of a shop tier and its colour. */
    public record Badge(String image, String colour) {
        public Badge {
            if (image == null || image.isBlank()) {
                throw new IllegalArgumentException("a tier badge needs an image");
            }
            rgba(colour);
        }
    }

    public MenuTheme {
        if (font == null || font.isBlank()) {
            throw new IllegalArgumentException("the menu theme needs a font");
        }
        swatches = complete(Swatch.class, swatches, "colour");
        images = complete(Art.class, images, "image");
        badges = complete(ShopTier.class, badges, "tier badge");
        swatches.values().forEach(MenuTheme::rgba);
    }

    private static <K extends Enum<K>, V> Map<K, V> complete(Class<K> keys, Map<K, V> values, String what) {
        Map<K, V> copy = new EnumMap<>(keys);
        if (values != null) {
            copy.putAll(values);
        }
        for (K key : keys.getEnumConstants()) {
            if (copy.get(key) == null) {
                throw new IllegalArgumentException("the menu theme has no " + what + " for " + key);
            }
        }
        return java.util.Collections.unmodifiableMap(copy);
    }

    /** The colour of a swatch, {@code #rrggbb} or {@code #rrggbbaa}. */
    public String colour(Swatch swatch) {
        return swatches.get(swatch);
    }

    public Image image(Art art) {
        return images.get(art);
    }

    public Badge badge(ShopTier tier) {
        return badges.get(tier);
    }

    /**
     * Red, green, blue and alpha (0..1) of a {@code #rrggbb} or {@code #rrggbbaa} colour.
     *
     * @throws IllegalArgumentException when it is not one
     */
    public static float[] rgba(String hex) {
        if (hex == null || !hex.matches("#([0-9a-fA-F]{6}|[0-9a-fA-F]{8})")) {
            throw new IllegalArgumentException("not a #rrggbb or #rrggbbaa colour: " + hex);
        }
        float[] rgba = {0, 0, 0, 1};
        for (int i = 0; i < (hex.length() - 1) / 2; i++) {
            rgba[i] = Integer.parseInt(hex.substring(1 + 2 * i, 3 + 2 * i), 16) / 255f;
        }
        return rgba;
    }
}
