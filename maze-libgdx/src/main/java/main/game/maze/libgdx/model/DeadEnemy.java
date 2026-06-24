package main.game.maze.libgdx.model;

/**
 * Tracks a killed zombie that is waiting to resurrect.
 *
 * <p>Instances are held in {@link GameWorldModel#deadEnemies()} and ticked each
 * frame by {@link main.game.maze.libgdx.helper.GdxGameCombatAndEnemyFlowSupport}.
 * When {@link #ready()} returns {@code true} the zombie is re-added to
 * {@link GameWorldModel#animatedEnemies()} at its original spawn coordinates
 * with a brief invulnerability window to prevent instant damage on reappearance.
 */
public final class DeadEnemy {

    private final EnemySpawn spawn;
    private float resurrectionSecondsRemaining;

    public DeadEnemy(EnemySpawn spawn, float resurrectionSeconds) {
        this.spawn = spawn;
        this.resurrectionSecondsRemaining = Math.max(0f, resurrectionSeconds);
    }

    /** The original spawn data (position, stats, resurrection time). */
    public EnemySpawn spawn() {
        return spawn;
    }

    /** Seconds remaining until the zombie reappears. */
    public float resurrectionSecondsRemaining() {
        return resurrectionSecondsRemaining;
    }

    /** Advance the countdown by {@code dt} seconds. */
    public void tick(float dt) {
        resurrectionSecondsRemaining = Math.max(0f, resurrectionSecondsRemaining - dt);
    }

    /** Returns {@code true} when the countdown has reached zero and the zombie should reappear. */
    public boolean ready() {
        return resurrectionSecondsRemaining <= 0f;
    }
}
