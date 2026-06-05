package main.game.maze.generated;

import main.game.maze.walls.WallMaterialBaseType;

/**
 * Generated collision handler for wall materials.
 * @generated from walls.ecore via FreeMarker template
 */
public final class WallCollisionHandler {

    private WallCollisionHandler() { }

    public record CollisionResult(
        boolean wallDestroyed,
        int damageDealt,
        int remainingHitPoints,
        String soundEffect,
        String particleEffect
    ) {}

    public static int calculateEffectiveDamage(int baseDamage, WallMaterialBaseType baseType) {
        if (baseType == null) return baseDamage;
        double multiplier = switch (baseType) {
            case GLASS -> 2;
            case DIRT -> 1.5;
            case WOOD -> 1;
            case STONE -> 0.75;
            case STEEL -> 0.5;
        };
        return (int) Math.ceil(baseDamage * multiplier);
    }

    public static int getResistance(WallMaterialBaseType baseType) {
        if (baseType == null) return 0;
        return switch (baseType) {
            case GLASS -> 0;
            case DIRT -> 1;
            case WOOD -> 2;
            case STONE -> 3;
            case STEEL -> 5;
        };
    }
}


