package main.game.maze.common.remains;

/**
 * How an enemy was killed; decides what it leaves on the floor.
 * Shared by both frontends and by the domain's {@code ICanDie}.
 */
public enum DeathCause {
    /** Bomb blast or flame: a pile of coal. */
    FIRE,
    /** Gun (future pickup): bleeding body, partly splattered. */
    SHOT,
    /** Collapsing wall, or sledgehammer (future pickup): flattened into a splat. */
    CRUSHED,
    /** Anything else (debug commands, unknown): no remains, as before. */
    OTHER;

    /** Whether this kind of death leaves remains behind. */
    public boolean leavesRemains() {
        return this != OTHER;
    }
}
