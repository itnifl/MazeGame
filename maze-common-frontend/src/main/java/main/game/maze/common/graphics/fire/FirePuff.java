package main.game.maze.common.graphics.fire;

/**
 * One filled circle of a fire blast frame. Colour channels and alpha are in {@code [0, 1]}.
 *
 * @param layer  what the puff represents; frontends draw {@link Layer#SMOKE} and
 *               {@link Layer#DEBRIS} with normal alpha blending and every other layer additively
 * @param x      centre x in the blast's coordinate space
 * @param y      centre y in the blast's coordinate space
 * @param radius circle radius
 */
public record FirePuff(Layer layer, double x, double y, double radius,
                       float red, float green, float blue, float alpha) {

    /** Draw layers, back to front. */
    public enum Layer {
        SMOKE, RIM, BODY, INNER, CORE, SHOCKWAVE, EMBER,
        /** Solid flying fragments (e.g. pumpkin shell), drawn on top of the fire. */
        DEBRIS;

        /** Whether the layer should be blended additively (glowing light) rather than painted over. */
        public boolean additive() {
            return this != SMOKE && this != DEBRIS;
        }
    }
}
