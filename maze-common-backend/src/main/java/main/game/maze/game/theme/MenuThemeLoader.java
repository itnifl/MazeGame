package main.game.maze.game.theme;

import java.util.EnumMap;
import java.util.Map;
import main.game.maze.game.shop.ShopTier;
import main.game.maze.runtime.DynamicModel;
import org.eclipse.emf.ecore.EObject;

/**
 * Loads the menu theme with dynamic EMF: the metamodel ({@code menutheme.ecore}) is read at runtime and the instance
 * ({@code menutheme.xmi}, generated from {@code menutheme.mazegame} by the MazeGame DSL) is validated against it, so the
 * look of the screens between levels lives in the model rather than in either frontend.
 */
public final class MenuThemeLoader {

    public static final String DEFAULT_ECORE = "/xmi/theme/menutheme.ecore";
    public static final String DEFAULT_MODEL = "/xmi/theme/menutheme.xmi";

    private static volatile MenuTheme defaultTheme;

    private MenuThemeLoader() {
    }

    /** The shipped theme (loaded once). */
    public static MenuTheme loadDefault() {
        MenuTheme theme = defaultTheme;
        if (theme == null) {
            synchronized (MenuThemeLoader.class) {
                if (defaultTheme == null) {
                    defaultTheme = load(DEFAULT_ECORE, DEFAULT_MODEL);
                }
                theme = defaultTheme;
            }
        }
        return theme;
    }

    /** Loads a theme from classpath metamodel and model paths. */
    public static MenuTheme load(String ecorePath, String modelPath) {
        EObject root = DynamicModel.load(MenuThemeLoader.class, ecorePath, modelPath, "menu theme");
        Map<MenuTheme.Swatch, String> swatches = new EnumMap<>(MenuTheme.Swatch.class);
        for (EObject swatch : DynamicModel.list(root, "swatches")) {
            swatches.put(MenuTheme.Swatch.valueOf(DynamicModel.literalName(swatch, "role")),
                    DynamicModel.string(swatch, "colour"));
        }
        Map<MenuTheme.Art, MenuTheme.Image> images = new EnumMap<>(MenuTheme.Art.class);
        for (EObject image : DynamicModel.list(root, "images")) {
            images.put(MenuTheme.Art.valueOf(DynamicModel.literalName(image, "role")),
                    new MenuTheme.Image(DynamicModel.string(image, "path"), DynamicModel.integer(image, "slice")));
        }
        Map<ShopTier, MenuTheme.Badge> badges = new EnumMap<>(ShopTier.class);
        for (EObject badge : DynamicModel.list(root, "badges")) {
            badges.put(ShopTier.valueOf(DynamicModel.literalName(badge, "tier")),
                    new MenuTheme.Badge(DynamicModel.string(badge, "image"), DynamicModel.string(badge, "colour")));
        }
        return new MenuTheme(DynamicModel.string(root, "font"), swatches, images, badges);
    }
}
