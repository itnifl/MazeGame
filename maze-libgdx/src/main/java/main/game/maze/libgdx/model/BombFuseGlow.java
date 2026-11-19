package main.game.maze.libgdx.model;

/**
 * Pulsing glow drawn on a placed player bomb while its fuse burns (libGDX screen space).
 *
 * @param alpha opacity in [0, 1]; fades as the fuse burns down
 */
public record BombFuseGlow(float x, float y, float radius, float alpha) {
}
