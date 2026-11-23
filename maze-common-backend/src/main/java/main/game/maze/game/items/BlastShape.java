package main.game.maze.game.items;

/** How a weapon's blast spreads; mirrors the {@code BlastShape} enum of {@code xmi/items/items.ecore}. */
public enum BlastShape {
    /** Flames along the corridors from the centre, stopped by walls they cannot break (the bomb, grenades). */
    FLAMES,
    /** A round blast through walls, strongest at the centre, drawn as a mushroom cloud (small nuclear grenade). */
    NUCLEAR
}
