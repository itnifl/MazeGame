package main.game.maze.generated;

import java.util.logging.Logger;
import main.game.maze.opponents.*;

/**
 * Generated factory for character graphics/sprites.
 * @generated from opponents.ecore
 */
public final class CharacterGraphicsFactory {

    private static final Logger LOGGER = Logger.getLogger(CharacterGraphicsFactory.class.getName());

    private CharacterGraphicsFactory() { }

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

    private static String getZombieSprite(Zombie z) {
        String img = z.getImageBase();
        return (img != null && !img.isEmpty()) ? img : "/images/zombie_default.png";
    }

    private static String getGhostSprite(Ghost g) {
        String img = g.getImageBase();
        return (img != null && !img.isEmpty()) ? img : "/images/ghost_default.png";
    }

    private static String getPumpkinBomberSprite(PumpkinBomber p) {
        String img = p.getImageBase();
        return (img != null && !img.isEmpty()) ? img : "/images/pumpkinbomber_default.png";
    }

    public static int getAnimationFrameCount(String typeName) {
        return switch (typeName) {
            case "Zombie" -> 4;
            case "Ghost" -> 6;
            case "PumpkinBomber" -> 4;
            default -> 1;
        };
    }

    public static double getSpriteScale(String typeName) {
        return switch (typeName) {
            case "Zombie" -> 1.0;
            case "Ghost" -> 0.8;
            case "PumpkinBomber" -> 1.2;
            default -> 1.0;
        };
    }
}
