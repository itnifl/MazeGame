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
<#list model.enemyTypes as type>
            case "${type}" -> apply${type}Multipliers((${type}) character, healthMultiplier, threatMultiplier, speedMultiplier);
</#list>
            default -> LOGGER.warning("Unknown character type for multipliers: " + typeName);
        }
    }

<#list model.enemyTypes as type>
    private static void apply${type}Multipliers(${type} c, double hm, double tm, double sm) {
        c.setHealth((int) (c.getHealth() * hm));
        c.setThreatLevel(c.getThreatLevel() * tm);
        c.setSpeed(c.getSpeed() * sm);
    }

</#list>
    public static int getBaseHealth(String typeName) {
        return switch (typeName) {
<#list model.uniqueEnemies as enemy>
            case "${enemy.type}" -> ${enemy.health?c};
</#list>
            default -> 0;
        };
    }

    public static double getBaseThreatLevel(String typeName) {
        return switch (typeName) {
<#list model.uniqueEnemies as enemy>
            case "${enemy.type}" -> ${enemy.threatLevel?c};
</#list>
            default -> 0.0;
        };
    }

    public static void applyDamageMultiplier(CharacterType character, double damageMultiplier, boolean instantDeath) {
        if (character == null) return;
        String typeName = character.eClass().getName();
        switch (typeName) {
<#list model.enemyTypes as type>
            case "${type}" -> apply${type}Damage((${type}) character, damageMultiplier, instantDeath);
</#list>
            default -> LOGGER.warning("Unknown character type for damage multiplier: " + typeName);
        }
    }

<#list model.enemyTypes as type>
    private static void apply${type}Damage(${type} c, double multiplier, boolean instantDeath) {
        if (instantDeath) c.setAttackDamage(Integer.MAX_VALUE);
        else c.setAttackDamage(Math.max(1, (int) Math.round(c.getAttackDamage() * multiplier)));
    }

</#list>
}
