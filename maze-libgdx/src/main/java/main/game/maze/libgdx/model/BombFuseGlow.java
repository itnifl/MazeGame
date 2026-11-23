package main.game.maze.libgdx.model;

/**
 * Pulsing glow drawn on a placed player bomb while its fuse burns (libGDX screen space).
 *
 * @param alpha    opacity in [0, 1]; fades as the fuse burns down
 * @param red      colour of the glow (amber for a fuse, radioactive green round a nuclear grenade)
 * @param haloOnly only the outer glow, no hot core: for a grenade drawn as a sprite, which the core would cover
 */
public record BombFuseGlow(float x, float y, float radius, float alpha, float red, float green, float blue,
                           boolean haloOnly) {

    /** The amber glow of a bomb's fuse, with its hot core. */
    public BombFuseGlow(float x, float y, float radius, float alpha) {
        this(x, y, radius, alpha, 1f, 0.72f, 0.18f, false);
    }
}
