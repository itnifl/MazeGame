package main.game.maze.generated;

import java.util.logging.Logger;

import main.game.maze.opponents.*;

/**
 * Generated factory for character graphics/sprites.
 * Maps character types to their image resources.
 * 
 * @generated from opponents.ecore
 */
public final class CharacterGraphicsFactory {

    private static final Logger LOGGER = Logger.getLogger(CharacterGraphicsFactory.class.getName());

    private CharacterGraphicsFactory() { /* utility class */ }

    /**
     * Gets the sprite image path for a character type.
     * 
     * @param character the character
     * @return image resource path
     */
    public static String getSpritePath(CharacterType character) {
        if (character == null) return "/images/default_enemy.png";

        String typeName = character.eClass().getName();
        
        return switch (typeName) {
            case "Zombie" -> getZombieSprite((Zombie) character);
            case "Ghost" -> getGhostSprite((Ghost) character);
            case "PumpkinBomber" -> getPumpkinBomberSprite((PumpkinBomber) character);
            default -> "/images/default_enemy.png";
        };
    }

    private static String getZombieSprite(Zombie zombie) {
        String img = zombie.getImageBase();
        return (img != null && !img.isEmpty()) ? img : "/images/zombie_default.png";
    }

    private static String getGhostSprite(Ghost ghost) {
        String img = ghost.getImageBase();
        return (img != null && !img.isEmpty()) ? img : "/images/ghost_default.png";
    }

    private static String getPumpkinBomberSprite(PumpkinBomber bomber) {
        String img = bomber.getImageBase();
        return (img != null && !img.isEmpty()) ? img : "/images/pumpkin_bomber_default.png";
    }

    /**
     * Gets the animation frame count for a character type.
     * Generated based on model configuration.
     * 
     * @param typeName the character type name
     * @return number of animation frames
     */
    public static int getAnimationFrameCount(String typeName) {
        return switch (typeName) {
            case "Zombie" -> 4;
            case "Ghost" -> 6;
            case "PumpkinBomber" -> 4;
            default -> 1;
        };
    }

    /**
     * Gets the sprite scale factor for a character type.
     * 
     * @param typeName the character type name
     * @return scale multiplier
     */
    public static double getSpriteScale(String typeName) {
        return switch (typeName) {
            case "Zombie" -> 1.0;
            case "Ghost" -> 0.8;  // ghosts are smaller
            case "PumpkinBomber" -> 1.2;  // bombers are larger
            default -> 1.0;
        };
    }
}
