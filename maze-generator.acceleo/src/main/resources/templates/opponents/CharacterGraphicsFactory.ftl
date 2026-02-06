package main.game.maze.generated;

import java.util.logging.Logger;
import main.game.maze.opponents.*;

/**
 * Generated factory for character graphics/sprites.
 * @generated from opponents.ecore via FreeMarker template
 */
public final class CharacterGraphicsFactory {

    private static final Logger LOGGER = Logger.getLogger(CharacterGraphicsFactory.class.getName());

    private CharacterGraphicsFactory() { }

    public static String getSpritePath(CharacterType character) {
        if (character == null) return "/images/default_enemy.png";
        String typeName = character.eClass().getName();
        return switch (typeName) {
<#list model.enemyTypes as type>
            case "${type}" -> get${type}Sprite((${type}) character);
</#list>
            default -> "/images/default_enemy.png";
        };
    }

<#list model.uniqueEnemies as enemy>
    private static String get${enemy.type}Sprite(${enemy.type} c) {
        String img = c.getImageBase();
        return (img != null && !img.isEmpty()) ? img : "${enemy.defaultImage?j_string}";
    }

</#list>
    public static int getAnimationFrameCount(String typeName) {
        return switch (typeName) {
<#list model.uniqueEnemies as enemy>
            case "${enemy.type}" -> ${enemy.animationFrames?c};
</#list>
            default -> 1;
        };
    }

    public static double getSpriteScale(String typeName) {
        return switch (typeName) {
<#list model.uniqueEnemies as enemy>
            case "${enemy.type}" -> ${enemy.spriteScale?c};
</#list>
            default -> 1.0;
        };
    }
}
