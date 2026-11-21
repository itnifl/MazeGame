package main.game.maze.game.status;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Banner fade: in, hold, out")
class BannerFadeTest {

    @Test
    @DisplayName("transparent at the start and the end, fully visible in between")
    void shape() {
        assertEquals(0f, BannerFade.alpha(0f, 1.6f), 1e-6f);
        assertTrue(BannerFade.alpha(0.075f, 1.6f) > 0.3f && BannerFade.alpha(0.075f, 1.6f) < 0.7f, "fading in");
        assertEquals(1f, BannerFade.alpha(0.8f, 1.6f), 1e-6f);
        assertTrue(BannerFade.alpha(1.525f, 1.6f) > 0.3f && BannerFade.alpha(1.525f, 1.6f) < 0.7f, "fading out");
        assertEquals(0f, BannerFade.alpha(1.6f, 1.6f), 1e-6f);
    }

    @Test
    @DisplayName("zero outside the banner's time, and for a banner with no time")
    void outside() {
        assertEquals(0f, BannerFade.alpha(-0.1f, 1.6f), 1e-6f);
        assertEquals(0f, BannerFade.alpha(2f, 1.6f), 1e-6f);
        assertEquals(0f, BannerFade.alpha(0.5f, 0f), 1e-6f);
    }

    @Test
    @DisplayName("a banner shorter than its fades still never exceeds 1 or goes below 0")
    void shortBanner() {
        for (float t = 0; t < 0.2f; t += 0.01f) {
            float alpha = BannerFade.alpha(t, 0.2f);
            assertTrue(alpha >= 0f && alpha <= 1f);
        }
    }
}
