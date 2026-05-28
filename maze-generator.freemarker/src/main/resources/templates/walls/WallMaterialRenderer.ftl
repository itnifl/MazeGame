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
<#list model.baseTypes as bt>
            case ${bt.name} -> new Color(${bt.colorR?c}, ${bt.colorG?c}, ${bt.colorB?c}<#if bt.alpha != 1.0>, ${bt.alpha?c}</#if>);
</#list>
        };
    }

    public static double getOpacity(WallMaterialBaseType baseType) {
        if (baseType == null) return 1.0;
        return switch (baseType) {
<#list model.baseTypes as bt>
<#if bt.alpha != 1.0>
            case ${bt.name} -> ${bt.alpha?c};
</#if>
</#list>
            default -> 1.0;
        };
    }

    public static boolean isTransparent(WallMaterialBaseType baseType) {
        return baseType == WallMaterialBaseType.GLASS;
    }

    public static String getBreakSound(WallMaterialBaseType baseType) {
        if (baseType == null) return "wall_generic_break";
        return switch (baseType) {
<#list model.baseTypes as bt>
            case ${bt.name} -> "${bt.breakSound}";
</#list>
        };
    }

    public static String getHitSound(WallMaterialBaseType baseType) {
        if (baseType == null) return "wall_generic_hit";
        return switch (baseType) {
<#list model.baseTypes as bt>
            case ${bt.name} -> "${bt.hitSound}";
</#list>
        };
    }
}
