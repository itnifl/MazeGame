package main.game.maze.libgdx.model;

/**
 * Remains of a killed enemy lying on the floor (libGDX screen space, y-up).
 *
 * @param x          bottom-left x, same anchor as the enemy sprite
 * @param y          bottom-left y
 * @param size       drawn size (the enemy's on-screen size)
 * @param spritePath remains sprite, from {@code RemainsSpriteResolver}
 */
public record RemainsVisual(float x, float y, float size, String spritePath) {
}
