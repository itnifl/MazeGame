package main.game.maze.javafx.menu;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import main.game.maze.game.shop.ShopTier;
import main.game.maze.game.theme.MenuButton;
import main.game.maze.game.theme.MenuTheme;
import main.game.maze.game.theme.MenuThemeLoader;

/**
 * The menu theme (the shared {@link MenuTheme} model) as JavaFX styles: the FreeMarker template
 * {@code menu-theme.css.ftl} is rendered once from the theme into a stylesheet, which the screens between levels add to
 * their root ({@link #apply}). The FXML views only name style classes ({@code menu-panel}, {@code menu-card}, ...); the
 * colours and textures are the theme's, the same ones the libGDX screens draw with.
 */
public final class FxMenuTheme {

    static final String TEMPLATE = "menu-theme.css.ftl";
    public static final String ROOT_CLASS = "menu-root";
    public static final String BUTTON_CLASS = "menu-button";

    private static volatile String stylesheet;
    private static final Map<String, Image> IMAGES = new ConcurrentHashMap<>();

    private FxMenuTheme() {
    }

    /** The theme's stylesheet as a {@code data:} URL, for {@code getStylesheets()}. */
    public static String stylesheet() {
        String sheet = stylesheet;
        if (sheet == null) {
            synchronized (FxMenuTheme.class) {
                if (stylesheet == null) {
                    String css = css(MenuThemeLoader.loadDefault());
                    stylesheet = "data:text/css;base64,"
                            + Base64.getEncoder().encodeToString(css.getBytes(StandardCharsets.UTF_8));
                }
                sheet = stylesheet;
            }
        }
        return sheet;
    }

    /** Gives a screen between levels the theme: its stylesheet and the root style class (the font). */
    public static void apply(Parent root) {
        if (!root.getStylesheets().contains(stylesheet())) {
            root.getStylesheets().add(stylesheet());
        }
        if (!root.getStyleClass().contains(ROOT_CLASS)) {
            root.getStyleClass().add(ROOT_CLASS);
        }
    }

    /** Makes {@code button} the themed button {@code kind}: its label and its metal plate. */
    public static void style(Button button, MenuButton kind) {
        button.setText(kind.label());
        button.getStyleClass().removeIf(name -> name.equals("button"));
        if (!button.getStyleClass().contains(BUTTON_CLASS)) {
            button.getStyleClass().add(BUTTON_CLASS);
        }
        button.getStyleClass().add(styleClass(kind));
        button.setMinWidth(javafx.scene.layout.Region.USE_PREF_SIZE);   // the label is never cut short
    }

    /** The style class of a button kind, e.g. {@code next-level}. */
    public static String styleClass(MenuButton kind) {
        return kind.name().toLowerCase(Locale.ROOT).replace('_', '-');
    }

    /** The style class of a shop tier's label, e.g. {@code tier-medium}. */
    public static String tierStyleClass(ShopTier tier) {
        return "tier-" + tier.name().toLowerCase(Locale.ROOT);
    }

    /** A texture of the theme (or any classpath image), loaded once. */
    public static Image image(String path) {
        return IMAGES.computeIfAbsent(path, p -> new Image(resource(p).toExternalForm()));
    }

    public static Image image(MenuTheme.Art art) {
        return image(MenuThemeLoader.loadDefault().image(art).path());
    }

    /** The stylesheet text for a theme, rendered from the template. */
    static String css(MenuTheme theme) {
        Map<String, String> colours = new HashMap<>();
        for (MenuTheme.Swatch swatch : MenuTheme.Swatch.values()) {
            colours.put(swatch.name(), rgba(theme.colour(swatch)));
        }
        Map<String, String> images = new HashMap<>();
        Map<String, Integer> slices = new HashMap<>();
        for (MenuTheme.Art art : MenuTheme.Art.values()) {
            images.put(art.name(), resource(theme.image(art).path()).toExternalForm());
            slices.put(art.name(), theme.image(art).slice());
        }
        Map<String, String> badges = new HashMap<>();
        for (ShopTier tier : ShopTier.values()) {
            badges.put(tier.name(), rgba(theme.badge(tier).colour()));
        }
        List<Map<String, String>> buttons = new ArrayList<>();
        for (MenuButton kind : MenuButton.values()) {
            buttons.add(Map.of("styleClass", styleClass(kind), "art", kind.art().name()));
        }
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
        configuration.setClassForTemplateLoading(FxMenuTheme.class, "");
        configuration.setDefaultEncoding("UTF-8");
        configuration.setLocale(Locale.ROOT);
        configuration.setTemplateExceptionHandler(freemarker.template.TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        try {
            StringWriter out = new StringWriter();
            configuration.getTemplate(TEMPLATE).process(Map.of("font", theme.font(), "colour", colours, "image", images,
                    "slice", slices, "badge", badges, "buttons", buttons), out);
            return out.toString();
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException("could not render the menu stylesheet from " + TEMPLATE, e);
        }
    }

    /** {@code #rrggbb} or {@code #rrggbbaa} as a CSS {@code rgba(...)}. */
    static String rgba(String hex) {
        float[] c = MenuTheme.rgba(hex);
        return String.format(Locale.ROOT, "rgba(%d,%d,%d,%.3f)", Math.round(c[0] * 255), Math.round(c[1] * 255),
                Math.round(c[2] * 255), c[3]);
    }

    private static URL resource(String path) {
        URL url = FxMenuTheme.class.getResource(path);
        if (url == null) {
            throw new IllegalStateException("menu theme image not found on the classpath: " + path);
        }
        return url;
    }
}
