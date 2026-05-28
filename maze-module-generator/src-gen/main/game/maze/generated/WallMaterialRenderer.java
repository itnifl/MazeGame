package main.game.maze.generated;

import main.game.maze.walls.WallMaterialBaseType;

/**
 * Generated renderer for WallMaterial instances.
 * @generated from walls.ecore via FreeMarker template
 */
public final class WallMaterialRenderer {

    private WallMaterialRenderer() { }

    public record Color(int r, int g, int b, double alpha) {
        public Color(int r, int g, int b) { this(r, g, b, 1.0); }
    }

    public static Color getBaseColor(WallMaterialBaseType baseType) {
        if (baseType == null) return new Color(128, 128, 128);
        return switch (baseType) {
            case GLASS -> new Color(200, 230, 255, 0.6);
            case DIRT -> new Color(139, 90, 43);
            case WOOD -> new Color(160, 82, 45);
            case STONE -> new Color(128, 128, 128);
            case STEEL -> new Color(70, 70, 90);
        };
    }

    public static double getOpacity(WallMaterialBaseType baseType) {
        if (baseType == null) return 1.0;
        return switch (baseType) {
            case GLASS -> 0.6;
            default -> 1.0;
        };
    }

    public static boolean isTransparent(WallMaterialBaseType baseType) {
        return baseType == WallMaterialBaseType.GLASS;
    }

    public static String getBreakSound(WallMaterialBaseType baseType) {
        if (baseType == null) return "wall_generic_break";
        return switch (baseType) {
            case GLASS -> "wall_glass_shatter";
            case DIRT -> "wall_dirt_crumble";
            case WOOD -> "wall_wood_splinter";
            case STONE -> "wall_stone_crack";
            case STEEL -> "wall_steel_dent";
        };
    }

    public static String getHitSound(WallMaterialBaseType baseType) {
        if (baseType == null) return "wall_generic_hit";
        return switch (baseType) {
            case GLASS -> "wall_glass_crack";
            case DIRT -> "wall_dirt_thud";
            case WOOD -> "wall_wood_knock";
            case STONE -> "wall_stone_chip";
            case STEEL -> "wall_steel_clang";
        };
    }
}
