package main.game.maze.generated;

import java.util.logging.Logger;

import main.game.maze.opponents.*;

/**
 * Generated attribute setter for applying difficulty multipliers.
 * Eliminates instanceof chains with type-safe switch dispatch.
 * 
 * @generated from opponents.ecore
 */
public final class CharacterAttributeSetter {

    private static final Logger LOGGER = Logger.getLogger(CharacterAttributeSetter.class.getName());

    private CharacterAttributeSetter() { /* utility class */ }

    /**
     * Applies difficulty multipliers to a character based on its type.
     * 
     * @param character the character to modify
     * @param healthMultiplier multiplier for health
     * @param threatMultiplier multiplier for threat level
     * @param speedMultiplier multiplier for speed
     */
    public static void applyDifficultyMultipliers(
            CharacterType character,
            double healthMultiplier,
            double threatMultiplier,
            double speedMultiplier) {
        
        if (character == null) return;

        String typeName = character.eClass().getName();
        
        switch (typeName) {
            case "Zombie" -> applyZombieMultipliers((Zombie) character, 
                healthMultiplier, threatMultiplier, speedMultiplier);
            case "Ghost" -> applyGhostMultipliers((Ghost) character,
                healthMultiplier, threatMultiplier, speedMultiplier);
            case "PumpkinBomber" -> applyPumpkinBomberMultipliers((PumpkinBomber) character,
                healthMultiplier, threatMultiplier, speedMultiplier);
            default -> LOGGER.warning("Unknown character type for multipliers: " + typeName);
        }
    }

    private static void applyZombieMultipliers(Zombie zombie, 
            double healthMult, double threatMult, double speedMult) {
        zombie.setHealth((int) (zombie.getHealth() * healthMult));
        zombie.setThreatLevel(zombie.getThreatLevel() * threatMult);
        zombie.setSpeed(zombie.getSpeed() * speedMult);
    }

    private static void applyGhostMultipliers(Ghost ghost,
            double healthMult, double threatMult, double speedMult) {
        ghost.setHealth((int) (ghost.getHealth() * healthMult));
        ghost.setThreatLevel(ghost.getThreatLevel() * threatMult);
        ghost.setSpeed(ghost.getSpeed() * speedMult);
    }

    private static void applyPumpkinBomberMultipliers(PumpkinBomber bomber,
            double healthMult, double threatMult, double speedMult) {
        bomber.setHealth((int) (bomber.getHealth() * healthMult));
        bomber.setThreatLevel(bomber.getThreatLevel() * threatMult);
        bomber.setSpeed(bomber.getSpeed() * speedMult);
    }

    /**
     * Gets the base threat level for a character type from the model.
     * Generated from model attribute values.
     * 
     * @param typeName the character type name
     * @return base threat level or 0.0 if unknown
     */
    public static double getBaseThreatLevel(String typeName) {
        return switch (typeName) {
            case "Zombie" -> 1.0;       // from model
            case "Ghost" -> 0.5;        // from model
            case "PumpkinBomber" -> 1.5; // from model
            default -> 0.0;
        };
    }

    /**
     * Gets the base health for a character type from the model.
     * Generated from model attribute values.
     * 
     * @param typeName the character type name
     * @return base health value or 0 if unknown
     */
    public static int getBaseHealth(String typeName) {
        return switch (typeName) {
            case "Zombie" -> 100;      // from model
            case "Ghost" -> 50;        // from model  
            case "PumpkinBomber" -> 75; // from model
            default -> 0;
        };
    }
}
