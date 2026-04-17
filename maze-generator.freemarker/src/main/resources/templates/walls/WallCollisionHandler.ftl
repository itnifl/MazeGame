package main.game.maze.generated;

import java.util.function.Consumer;
import java.util.logging.Logger;
import main.game.maze.walls.WallMaterial;
import main.game.maze.walls.WallMaterialBaseType;

/**
 * Generated collision handler for wall materials.
 * @generated from walls.ecore via FreeMarker template
 */
public final class WallCollisionHandler {

    private static final Logger LOGGER = Logger.getLogger(WallCollisionHandler.class.getName());

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
<#list model.baseTypes as bt>
            case ${bt.name} -> ${bt.damageMultiplier?c};
</#list>
        };
        return (int) Math.ceil(baseDamage * multiplier);
    }

    public static int getResistance(WallMaterialBaseType baseType) {
        if (baseType == null) return 0;
        return switch (baseType) {
<#list model.baseTypes as bt>
            case ${bt.name} -> ${bt.resistance?c};
</#list>
        };
    }
}
