package main.game.maze.common.graphics.fire;

import java.util.Locale;

/**
 * Look of the rubble a collapsing wall leaves, taken from the wall material in the walls model
 * ({@code walls.ecore} {@code WallMaterialBaseType}; material ids such as {@code STONE_BASIC}).
 * Colours are {@code {red, green, blue}} in [0, 1].
 */
public enum RubbleMaterial {
    GLASS(new float[] {0.62f, 0.80f, 0.88f}, new float[] {0.88f, 0.97f, 1.0f}, new float[] {0.80f, 0.86f, 0.90f}),
    DIRT(new float[] {0.36f, 0.25f, 0.15f}, new float[] {0.52f, 0.38f, 0.24f}, new float[] {0.50f, 0.40f, 0.30f}),
    WOOD(new float[] {0.42f, 0.26f, 0.12f}, new float[] {0.66f, 0.46f, 0.24f}, new float[] {0.56f, 0.48f, 0.40f}),
    STONE(new float[] {0.38f, 0.37f, 0.36f}, new float[] {0.62f, 0.60f, 0.57f}, new float[] {0.60f, 0.58f, 0.55f}),
    STEEL(new float[] {0.26f, 0.28f, 0.31f}, new float[] {0.62f, 0.66f, 0.72f}, new float[] {0.45f, 0.45f, 0.46f});

    private final float[] chunk;
    private final float[] highlight;
    private final float[] dust;

    RubbleMaterial(float[] chunk, float[] highlight, float[] dust) {
        this.chunk = chunk;
        this.highlight = highlight;
        this.dust = dust;
    }

    float[] chunk() {
        return chunk;
    }

    float[] highlight() {
        return highlight;
    }

    float[] dust() {
        return dust;
    }

    /**
     * Maps a wall material id from the walls model ({@code GLASS_BASIC}, {@code STEEL_SOLID}, ...)
     * to its rubble look by its base type prefix; unknown or missing ids fall back to stone.
     */
    public static RubbleMaterial fromMaterialId(String materialId) {
        if (materialId == null || materialId.isBlank()) {
            return STONE;
        }
        String base = materialId.trim().toUpperCase(Locale.ROOT);
        int underscore = base.indexOf('_');
        if (underscore > 0) {
            base = base.substring(0, underscore);
        }
        for (RubbleMaterial material : values()) {
            if (material.name().equals(base)) {
                return material;
            }
        }
        return STONE;
    }
}
