package main.game.maze.libgdx.controller;

import main.game.maze.common.movement.GameplayTickRate;
import main.game.maze.libgdx.model.EnemySpawn;

/**
 * Pure, stateless layout metrics and frame-independent gameplay constants for
 * the libGDX gameplay screen.
 *
 * <p>Extracted from {@link GdxGameScreenController} (MVC / SRP) so HUD strip
 * geometry, bar sizing, death-display timing, and the JavaFX speed-parity factor
 * live outside the controller and can be unit-tested in isolation.
 */
public final class GdxGameScreenMetrics {

    /**
     * Gameplay tick rate; libGDX speeds are scaled by this for parity with the
     * JavaFX frontend. Sourced from the shared {@link GameplayTickRate} so both
     * frontends move enemies the same distance per real-time second.
     */
    public static final float JAVA_FX_TICK_RATE = (float) GameplayTickRate.ENEMY_MOVEMENT_TICKS_PER_SECOND;

    /** HUD pixel height reserved for the bottom command row. */
    public static final float BOTTOM_BAR_HEIGHT = 40f;

    /** HUD pixel height reserved for the HP bar at the window top. */
    public static final float HP_BAR_HEIGHT = 20f;

    /** Seconds the death sprite is shown before transitioning to game over. */
    public static final float DEATH_DISPLAY_DELAY_SECONDS = 3f;

    private GdxGameScreenMetrics() {
    }

    /**
     * Gameplay viewport screen-pixel bounds. Match JavaFX by rendering the
     * maze under HUD overlays across the full window.
     */
    public static GameStripBounds computeGameStripBounds(int windowWidth, int windowHeight) {
        int w = Math.max(1, windowWidth);
        int h = Math.max(1, windowHeight);
        return new GameStripBounds(0, 0, w, h);
    }

    /** HUD y of the HP bar lower edge so the bar is pinned to the window top. */
    public static float hpBarBottomY(float windowHeight) {
        return Math.max(0f, windowHeight - HP_BAR_HEIGHT);
    }

    /** HUD y of the bottom command row lower edge so the bar is pinned to the window bottom. */
    public static float bottomRowY() {
        return 0f;
    }

    /** HUD pixel height reserved for the bottom command row. */
    public static float bottomRowHeight() {
        return BOTTOM_BAR_HEIGHT;
    }

    public static float bottomBarHeight() {
        return BOTTOM_BAR_HEIGHT;
    }

    public static float hpBarHeight() {
        return HP_BAR_HEIGHT;
    }

    public static float deathDisplayDelaySeconds() {
        return DEATH_DISPLAY_DELAY_SECONDS;
    }

    public static boolean isInfectious(EnemySpawn spawn) {
        return spawn != null && spawn.infectionLevel() > 0;
    }

    public static float toJavaFxLikeSpeed(float playerSpeed) {
        return playerSpeed * JAVA_FX_TICK_RATE;
    }

    /** Viewport-strip record exposed for unit tests. */
    public record GameStripBounds(int x, int y, int width, int height) {}
}
