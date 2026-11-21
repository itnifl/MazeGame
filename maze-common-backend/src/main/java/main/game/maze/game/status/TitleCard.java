package main.game.maze.game.status;

/**
 * How a title card moves: the big fighting-game title ("FINAL LEVEL") that slams onto the screen from far too big,
 * shakes with the impact, holds, then grows a little and fades out. Shared by both frontends, which only draw
 * the text (fill, outline, shadow) at the scale and place given here.
 */
public final class TitleCard {

    /** The slam: seconds from the first frame until the title has landed. */
    public static final float SLAM_SECONDS = 0.28f;
    /** The shake after the slam: seconds. */
    public static final float SHAKE_SECONDS = 0.35f;
    /** The fade out at the end: seconds. */
    public static final float FADE_OUT_SECONDS = 0.45f;
    /** How many times too big the title is when it starts. */
    public static final float START_SCALE = 3.4f;
    /** How far the shake moves the title at first, as a share of the screen height. */
    public static final float SHAKE_SHARE = 0.02f;
    /** How much bigger the title has grown by the end of its time. */
    public static final float END_GROWTH = 0.10f;

    private TitleCard() {
    }

    /**
     * Where the title is in its moves.
     *
     * @param alpha   0 to 1
     * @param scale   1 is the size it is meant to have; more while it slams in
     * @param shakeX  sideways, as a share of the screen height (add to the middle)
     * @param shakeY  up and down, the same
     */
    public record Frame(float alpha, float scale, float shakeX, float shakeY) {
        public static final Frame GONE = new Frame(0f, 1f, 0f, 0f);
    }

    /** The title {@code elapsedSeconds} after it was shown, when it is shown for {@code totalSeconds}. */
    public static Frame frame(float elapsedSeconds, float totalSeconds) {
        if (totalSeconds <= 0f || elapsedSeconds < 0f || elapsedSeconds >= totalSeconds) {
            return Frame.GONE;
        }
        float t = elapsedSeconds;
        float alpha = Math.min(1f, t / 0.07f);
        alpha = Math.min(alpha, Math.min(1f, (totalSeconds - t) / FADE_OUT_SECONDS));
        float scale;
        if (t < SLAM_SECONDS) {
            float left = 1f - t / SLAM_SECONDS;
            scale = 1f + (START_SCALE - 1f) * left * left;   // fast at first, then settling onto its size
        } else {
            float held = (t - SLAM_SECONDS) / Math.max(0.001f, totalSeconds - SLAM_SECONDS);
            scale = 1f + END_GROWTH * held;
        }
        float shakeX = 0f;
        float shakeY = 0f;
        float shaking = t - SLAM_SECONDS;
        if (shaking >= 0f && shaking < SHAKE_SECONDS) {
            float strength = SHAKE_SHARE * (1f - shaking / SHAKE_SECONDS);
            shakeX = strength * (float) Math.sin(shaking * 95f);
            shakeY = strength * (float) Math.cos(shaking * 77f);
        }
        return new Frame(Math.max(0f, alpha), scale, shakeX, shakeY);
    }
}
