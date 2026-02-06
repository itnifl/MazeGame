package main.game.maze.generated;

import java.util.logging.Logger;
import main.game.maze.opponents.*;

/**
 * Generated attribute setter for applying difficulty multipliers.
 * @generated from opponents.ecore
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
            case "Zombie" -> applyZombieMultipliers((Zombie) character, healthMultiplier, threatMultiplier, speedMultiplier);
            case "Ghost" -> applyGhostMultipliers((Ghost) character, healthMultiplier, threatMultiplier, speedMultiplier);
            case "PumpkinBomber" -> applyPumpkinBomberMultipliers((PumpkinBomber) character, healthMultiplier, threatMultiplier, speedMultiplier);
            default -> LOGGER.warning("Unknown character type for multipliers: " + typeName);
        }
    }

    private static void applyZombieMultipliers(Zombie z, double hm, double tm, double sm) {
        z.setHealth((int) (z.getHealth() * hm));
        z.setThreatLevel(z.getThreatLevel() * tm);
        z.setSpeed(z.getSpeed() * sm);
    }

    private static void applyGhostMultipliers(Ghost g, double hm, double tm, double sm) {
        g.setHealth((int) (g.getHealth() * hm));
        g.setThreatLevel(g.getThreatLevel() * tm);
        g.setSpeed(g.getSpeed() * sm);
    }

    private static void applyPumpkinBomberMultipliers(PumpkinBomber p, double hm, double tm, double sm) {
        p.setHealth((int) (p.getHealth() * hm));
        p.setThreatLevel(p.getThreatLevel() * tm);
        p.setSpeed(p.getSpeed() * sm);
    }

    public static int getBaseHealth(String typeName) {
        return switch (typeName) {
            case "Zombie" -> 100;
            case "Ghost" -> 50;
            case "PumpkinBomber" -> 75;
            default -> 0;
        };
    }

    public static double getBaseThreatLevel(String typeName) {
        return switch (typeName) {
            case "Zombie" -> 1.0;
            case "Ghost" -> 0.5;
            case "PumpkinBomber" -> 1.5;
            default -> 0.0;
        };
    }

    public static void applyDamageMultiplier(CharacterType character, double damageMultiplier, boolean instantDeath) {
        if (character == null) return;
        String typeName = character.eClass().getName();
        switch (typeName) {
            case "Zombie" -> applyZombieDamage((Zombie) character, damageMultiplier, instantDeath);
            case "Ghost" -> applyGhostDamage((Ghost) character, damageMultiplier, instantDeath);
            case "PumpkinBomber" -> applyPumpkinBomberDamage((PumpkinBomber) character, damageMultiplier, instantDeath);
            default -> LOGGER.warning("Unknown character type for damage multiplier: " + typeName);
        }
    }

    private static void applyZombieDamage(Zombie z, double multiplier, boolean instantDeath) {
        if (instantDeath) z.setAttackDamage(Integer.MAX_VALUE);
        else z.setAttackDamage(Math.max(1, (int) Math.round(z.getAttackDamage() * multiplier)));
    }

    private static void applyGhostDamage(Ghost g, double multiplier, boolean instantDeath) {
        if (instantDeath) g.setAttackDamage(Integer.MAX_VALUE);
        else g.setAttackDamage(Math.max(1, (int) Math.round(g.getAttackDamage() * multiplier)));
    }

    private static void applyPumpkinBomberDamage(PumpkinBomber p, double multiplier, boolean instantDeath) {
        if (instantDeath) p.setAttackDamage(Integer.MAX_VALUE);
        else p.setAttackDamage(Math.max(1, (int) Math.round(p.getAttackDamage() * multiplier)));
    }
}
