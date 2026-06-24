package main.game.maze.common.graphics;

/**
 * Shared utility for deriving walk-cycle animation frame paths from a
 * naming convention shared by all enemy sprite assets.
 *
 * <p>Convention: the first digit sequence in the <em>filename</em> (not the
 * directory path) is the 1-indexed frame number. Example:
 * {@code /main/game/maze/zombie1-right.png} is frame 1 of the right-facing
 * zombie walk cycle; frame 3 is {@code /main/game/maze/zombie3-right.png}.
 *
 * <p>If the filename has no digit (e.g. {@code pumpkinbomber.png}), the path
 * is returned unchanged — single-frame sprites are unaffected.
 */
public final class SpriteAnimationUtil {

    private SpriteAnimationUtil() {
    }

    /**
     * Replaces the first digit sequence in the filename part of {@code frame1Path}
     * with {@code frameIndex + 1} to produce the path for the Nth animation frame.
     *
     * @param frame1Path the path to the first (index 0) animation frame
     * @param frameIndex 0-based frame index
     * @return the derived path for frame {@code frameIndex}; {@code frame1Path}
     *         unchanged when the filename contains no digit
     */
    public static String deriveAnimationFramePath(String frame1Path, int frameIndex) {
        if (frame1Path == null || frame1Path.isBlank()) {
            return frame1Path;
        }
        int lastSlash = Math.max(frame1Path.lastIndexOf('/'), frame1Path.lastIndexOf('\\'));
        String parent = lastSlash >= 0 ? frame1Path.substring(0, lastSlash + 1) : "";
        String name   = lastSlash >= 0 ? frame1Path.substring(lastSlash + 1) : frame1Path;
        String animated = name.replaceFirst("\\d+", String.valueOf(frameIndex + 1));
        return parent + animated;
    }
}
