package main.game.maze.characters;

/**
 * Maps the EMF-derived attribute {@code instantKillOnCollision = threatLevel &gt; 100}
 * (see movements.ecore) onto the runtime damage applied on a player-enemy
 * collision. Any enemy whose model {@code threatLevel} exceeds the threshold
 * reduces the victim to zero HP on first contact (F15), regardless of the
 * configured {@code attackDamage}.
 */
public final class CollisionDamage {

    public static final double INSTANT_KILL_THREAT_THRESHOLD = 100.0;

    private CollisionDamage() {}

    public static int effectiveDamage(double threatLevel, int baseDamage) {
        if (threatLevel > INSTANT_KILL_THREAT_THRESHOLD) {
            return Integer.MAX_VALUE;
        }
        return Math.max(0, baseDamage);
    }
}


