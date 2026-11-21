package main.game.maze.game.level;

/**
 * The lava of a level, as defined in {@code levels.xmi}: random snaking streams under the walls, with a few bridges
 * over each. The player dies in lava and can only cross a stream on a bridge; enemies keep out of it.
 *
 * @param streamCount      how many streams
 * @param width            width of a stream in pixels
 * @param bridgesPerStream bridges over each stream
 * @param bridgeLength     length of a bridge along the stream in pixels
 * @param meander          how far a stream swings from side to side, in pixels
 * @param wavelength       how long one swing is along the stream, in pixels
 * @param flowSpeed        how fast the lava flows, in pixels per second
 * @param glow             how strongly the lava lights the floor round it (0 to 1)
 * @param crust            how much of the surface has cooled to dark crust (0 to 1)
 */
public record LavaSpec(int streamCount, double width, int bridgesPerStream, double bridgeLength, double meander,
                       double wavelength, double flowSpeed, double glow, double crust, String deathSound) {

    public LavaSpec {
        if (streamCount < 1 || !(width > 0) || bridgesPerStream < 1 || !(bridgeLength > 0)   // (!(x > 0) also rejects NaN)
                || !(meander >= 0) || !(wavelength > 0) || !(flowSpeed >= 0) || !(glow >= 0 && glow <= 1) || !(crust >= 0 && crust <= 1)) {
            throw new IllegalArgumentException("lava values must be positive (glow and crust between 0 and 1)");
        }
    }

    /** A lava with the usual look: the defaults of the model for how it meanders, flows, glows and crusts. */
    public static LavaSpec basic(int streamCount, double width, int bridgesPerStream, double bridgeLength, String deathSound) {
        return new LavaSpec(streamCount, width, bridgesPerStream, bridgeLength, 95.0, 340.0, 34.0, 0.65, 0.45, deathSound);
    }
}
