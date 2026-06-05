package main.game.maze.generated;

import java.util.logging.Logger;
import main.game.maze.opponents.*;

/**
 * Generated attribute setter for applying difficulty multipliers.
 * @generated from opponents.ecore via FreeMarker template
 */
public final class CharacterAttributeSetter {

    private static final Logger LOGGER = Logger.getLogger(CharacterAttributeSetter.class.getName());

    private CharacterAttributeSetter() { }

    public static void applyDifficultyMultipliers(
            CharacterType character,
            double healthMultiplier,
            double threatMultiplier,
            double speedMultiplier) {
        if (character == null) return;
        String typeName = character.eClass().getName();
        switch (typeName) {
            case "Ghost" -> applyGhostMultipliers((Ghost) character, healthMultiplier, threatMultiplier, speedMultiplier);
            case "Zombie" -> applyZombieMultipliers((Zombie) character, healthMultiplier, threatMultiplier, speedMultiplier);
            case "PumpkinBomber" -> applyPumpkinBomberMultipliers((PumpkinBomber) character, healthMultiplier, threatMultiplier, speedMultiplier);
            default -> LOGGER.warning("Unknown character type for multipliers: " + typeName);
        }
    }

    private static void applyGhostMultipliers(Ghost c, double hm, double tm, double sm) {
        c.setHealth((int) (c.getHealth() * hm));
        c.setThreatLevel(c.getThreatLevel() * tm);
        c.setSpeed(c.getSpeed() * sm);
    }

    private static void applyZombieMultipliers(Zombie c, double hm, double tm, double sm) {
        c.setHealth((int) (c.getHealth() * hm));
        c.setThreatLevel(c.getThreatLevel() * tm);
        c.setSpeed(c.getSpeed() * sm);
    }

    private static void applyPumpkinBomberMultipliers(PumpkinBomber c, double hm, double tm, double sm) {
        c.setHealth((int) (c.getHealth() * hm));
        c.setThreatLevel(c.getThreatLevel() * tm);
        c.setSpeed(c.getSpeed() * sm);
    }

    public static int getBaseHealth(String typeName) {
        return switch (typeName) {
            case "Ghost" -> 120;
            case "Zombie" -> 120;
            case "PumpkinBomber" -> 100;
            default -> 0;
        };
    }

    public static double getBaseThreatLevel(String typeName) {
        return switch (typeName) {
            case "Ghost" -> 1;
            case "Zombie" -> 1;
            case "PumpkinBomber" -> 0;
            default -> 0.0;
        };
    }

    public static void applyDamageMultiplier(CharacterType character, double damageMultiplier, boolean instantDeath) {
        if (character == null) return;
        String typeName = character.eClass().getName();
        switch (typeName) {
            case "Ghost" -> applyGhostDamage((Ghost) character, damageMultiplier, instantDeath);
            case "Zombie" -> applyZombieDamage((Zombie) character, damageMultiplier, instantDeath);
            case "PumpkinBomber" -> applyPumpkinBomberDamage((PumpkinBomber) character, damageMultiplier, instantDeath);
            default -> LOGGER.warning("Unknown character type for damage multiplier: " + typeName);
        }
    }

    private static void applyGhostDamage(Ghost c, double multiplier, boolean instantDeath) {
        if (instantDeath) c.setAttackDamage(Integer.MAX_VALUE);
        else c.setAttackDamage(Math.max(1, (int) Math.round(c.getAttackDamage() * multiplier)));
    }

    private static void applyZombieDamage(Zombie c, double multiplier, boolean instantDeath) {
        if (instantDeath) c.setAttackDamage(Integer.MAX_VALUE);
        else c.setAttackDamage(Math.max(1, (int) Math.round(c.getAttackDamage() * multiplier)));
    }

    private static void applyPumpkinBomberDamage(PumpkinBomber c, double multiplier, boolean instantDeath) {
        if (instantDeath) c.setAttackDamage(Integer.MAX_VALUE);
        else c.setAttackDamage(Math.max(1, (int) Math.round(c.getAttackDamage() * multiplier)));
    }

}


