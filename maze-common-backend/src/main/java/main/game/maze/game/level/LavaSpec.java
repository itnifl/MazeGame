package main.game.maze.game.level;

/**
 * The lava of a level, as defined in {@code levels.xmi}: random snaking streams under the walls, with a few bridges
 * over each. The player dies in lava and can only cross a stream on a bridge; enemies keep out of it.
 *
 * @param streamCount      how many streams
 * @param width            width of a stream in pixels
 * @param bridgesPerStream bridges over each stream
 * @param bridgeLength     length of a bridge along the stream in pixels
 */
public record LavaSpec(int streamCount, double width, int bridgesPerStream, double bridgeLength, String deathSound) {

    public LavaSpec {
        if (streamCount < 1 || width <= 0 || bridgesPerStream < 1 || bridgeLength <= 0) {
            throw new IllegalArgumentException("lava values must be positive");
        }
    }
}
