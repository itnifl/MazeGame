package main.game.maze.service;

import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.Zombie;

/**
 * Per-enemy-type life rules read from the opponent model, shared by both frontends so an enemy
 * has the same health, resurrection delay and respawn protection in libGDX and JavaFX.
 */
public final class MortalityRules {

    /** Health when no model is available. */
    public static final int DEFAULT_MAX_HIT_POINTS = 100;
    /** Zombie resurrection delay when the model leaves {@code resurrectionTime} at 0. */
    public static final int DEFAULT_ZOMBIE_RESURRECTION_MS = 5_000;
    /** Invulnerability right after a respawn, so the enemy is not killed again instantly. */
    public static final double RESPAWN_INVULNERABILITY_SECONDS = 2.0;

    private MortalityRules() {
    }

    /** Full health of an enemy of this type (at least 1). */
    public static int maxHitPoints(CharacterType type) {
        return type == null ? DEFAULT_MAX_HIT_POINTS : Math.max(1, type.getHealth());
    }

    /** Delay before a killed enemy of this type comes back; 0 means it stays dead. */
    public static int resurrectionTimeMs(CharacterType type) {
        if (type instanceof Zombie zombie) {
            int modelMs = Math.max(0, zombie.getResurrectionTime()) * 1_000;
            return modelMs > 0 ? modelMs : DEFAULT_ZOMBIE_RESURRECTION_MS;
        }
        return 0;
    }
}
