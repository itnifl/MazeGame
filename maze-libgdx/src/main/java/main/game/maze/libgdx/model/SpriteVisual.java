package main.game.maze.libgdx.model;

/**
 * A sprite to draw this frame (libGDX screen space, y-up): pickups on the floor, the weapon in
 * the player's hands.
 *
 * @param x,y             sprite centre
 * @param size            drawn size
 * @param rotationDegrees counter-clockwise rotation
 * @param flipX           mirrored left-right (e.g. a weapon held facing left)
 */
public record SpriteVisual(float x, float y, float size, float rotationDegrees, boolean flipX, String spritePath) {
}
