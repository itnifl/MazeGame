package main.game.maze.libgdx.model;

/**
 * Immutable enemy sprite placement derived from the shared Ecore/XMI model.
 */
public record EnemySpawn(
        String id,
        String imagePath,
        float x,
        float y,
        float size,
        float effectiveThreat,
        int attackDamage,
        int infectionLevel,
        String touchSoundPath) {
}
