package main.game.maze.game.status;

/**
 * Stores a transient status message with a countdown timer.
 */
public final class StatusMessageBus {

    private static final float DEFAULT_DURATION_SECONDS = 2.4f;

    private String message = "";
    private float secondsRemaining;

    public void publish(String text) {
        publish(text, DEFAULT_DURATION_SECONDS);
    }

    public void publish(String text, float durationSeconds) {
        message = text == null ? "" : text;
        secondsRemaining = Math.max(0f, durationSeconds);
        if (message.isBlank() || secondsRemaining <= 0f) {
            clear();
        }
    }

    public void tick(float dt) {
        float safeDt = (Float.isFinite(dt) && dt > 0f) ? dt : 0f;
        if (bannerSecondsRemaining > 0f) {
            bannerSecondsRemaining = Math.max(0f, bannerSecondsRemaining - safeDt);
            if (bannerSecondsRemaining == 0f) {
                clearBanner();
            }
        }
        if (secondsRemaining <= 0f) {
            return;
        }
        secondsRemaining = Math.max(0f, secondsRemaining - safeDt);
        if (secondsRemaining == 0f) {
            clear();
        }
    }

    public String currentMessage() {
        return message;
    }

    public boolean hasMessage() {
        return !message.isBlank();
    }

    public float secondsRemaining() {
        return secondsRemaining;
    }

    public void clear() {
        message = "";
        secondsRemaining = 0f;
    }

    // ---- banner: a big word in the middle of the screen (e.g. "Teleported"), apart from the status line ----

    private String banner = "";
    private float bannerSecondsRemaining;
    private float bannerTotalSeconds;

    /** Shows {@code text} big in the middle of the screen for {@code durationSeconds}, fading in and out. */
    public void showBanner(String text, float durationSeconds) {
        banner = text == null ? "" : text;
        bannerTotalSeconds = Math.max(0f, durationSeconds);
        bannerSecondsRemaining = bannerTotalSeconds;
        if (banner.isBlank() || bannerTotalSeconds <= 0f) {
            clearBanner();
        }
    }

    public String bannerText() {
        return banner;
    }

    public boolean hasBanner() {
        return !banner.isBlank();
    }

    /** How opaque the banner is now: 0 to 1, fading in at the start and out at the end; 0 without a banner. */
    public float bannerAlpha() {
        if (!hasBanner()) {
            return 0f;
        }
        return BannerFade.alpha(bannerTotalSeconds - bannerSecondsRemaining, bannerTotalSeconds);
    }

    public void clearBanner() {
        banner = "";
        bannerSecondsRemaining = 0f;
        bannerTotalSeconds = 0f;
    }
}
