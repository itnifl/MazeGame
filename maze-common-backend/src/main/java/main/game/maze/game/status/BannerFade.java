package main.game.maze.game.status;

/** How a banner (a big word in the middle of the screen) fades: in, hold, out. Shared by both frontends. */
public final class BannerFade {

    /** Fade-in and fade-out time, in seconds. */
    public static final float FADE_SECONDS = 0.15f;

    private BannerFade() {
    }

    /**
     * How opaque the banner is {@code elapsedSeconds} after it was shown, when it is shown for {@code totalSeconds}:
     * 0 to 1, fading in at the start and out at the end; 0 outside its time.
     */
    public static float alpha(float elapsedSeconds, float totalSeconds) {
        if (totalSeconds <= 0f || elapsedSeconds < 0f || elapsedSeconds >= totalSeconds) {
            return 0f;
        }
        float in = Math.min(1f, elapsedSeconds / FADE_SECONDS);
        float out = Math.min(1f, (totalSeconds - elapsedSeconds) / FADE_SECONDS);
        return Math.max(0f, Math.min(in, out));
    }
}
