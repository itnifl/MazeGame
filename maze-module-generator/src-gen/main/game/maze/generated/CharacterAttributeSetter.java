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
     * Note: attackDamage is handled separately with instantDeath logic.
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

    /**
     * Applies damage multiplier with instantDeath handling.
     * INTENDED TO BE GENERATED - Currently added manually due to Acceleo build caching issue.
     * Generated to eliminate code duplication across character types.
     * 
     * @param character the character to modify
     * @param damageMultiplier multiplier for attack damage
     * @param instantDeath if true, sets damage to Integer.MAX_VALUE
     */
    public static void applyDamageMultiplier(
            CharacterType character,
            double damageMultiplier,
            boolean instantDeath) {
        
        if (character == null) return;

        String typeName = character.eClass().getName();
        
        switch (typeName) {
            case "Zombie" -> applyZombieDamage((Zombie) character, damageMultiplier, instantDeath);
            case "Ghost" -> applyGhostDamage((Ghost) character, damageMultiplier, instantDeath);
            case "PumpkinBomber" -> applyPumpkinBomberDamage((PumpkinBomber) character, damageMultiplier, instantDeath);
            default -> LOGGER.warning("Unknown character type for damage multiplier: " + typeName);
        }
    }

    private static void applyZombieDamage(Zombie zombie, double multiplier, boolean instantDeath) {
        if (instantDeath) {
            zombie.setAttackDamage(Integer.MAX_VALUE);
        } else {
            zombie.setAttackDamage(Math.max(1, (int) Math.round(zombie.getAttackDamage() * multiplier)));
        }
    }

    private static void applyGhostDamage(Ghost ghost, double multiplier, boolean instantDeath) {
        if (instantDeath) {
            ghost.setAttackDamage(Integer.MAX_VALUE);
        } else {
            ghost.setAttackDamage(Math.max(1, (int) Math.round(ghost.getAttackDamage() * multiplier)));
        }
    }

    private static void applyPumpkinBomberDamage(PumpkinBomber bomber, double multiplier, boolean instantDeath) {
        if (instantDeath) {
            bomber.setAttackDamage(Integer.MAX_VALUE);
        } else {
            bomber.setAttackDamage(Math.max(1, (int) Math.round(bomber.getAttackDamage() * multiplier)));
        }
    }
}
