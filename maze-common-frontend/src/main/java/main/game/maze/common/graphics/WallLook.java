package main.game.maze.common.graphics;

import java.util.Locale;

/**
 * The sprite of each wall material, shared by both frontends. A material id of the walls model
 * ({@code GLASS_BASIC}, {@code STEEL_SOLID}, ...) maps to the look of its base material, so every
 * variant of a material looks alike and a new variant needs no code.
 */
public enum WallLook {
    GLASS("/main/game/maze/glassWall.png"),
    DIRT("/main/game/maze/dirtWall.png"),
    WOOD("/main/game/maze/woodWall.png"),
    STONE("/main/game/maze/stoneWall.png"),
    STEEL("/main/game/maze/steelWall.png");

    private final String imagePath;

    WallLook(String imagePath) {
        this.imagePath = imagePath;
    }

    /** Classpath path of the wall sprite. */
    public String imagePath() {
        return imagePath;
    }

    /** The look of a material id, or null when its base material is not known (the classic wall is used then). */
    public static WallLook of(String materialId) {
        if (materialId == null || materialId.isBlank()) {
            return null;
        }
        String base = materialId.toUpperCase(Locale.ROOT);
        int separator = base.indexOf('_');
        if (separator > 0) {
            base = base.substring(0, separator);
        }
        for (WallLook look : values()) {
            if (look.name().equals(base)) {
                return look;
            }
        }
        return null;
    }
}
