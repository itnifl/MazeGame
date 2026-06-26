package main.game.maze.libgdx.render;

import main.game.maze.libgdx.controller.GdxGameScreenMetrics;
import main.game.maze.mazeworld.constants.StageConstants;

/**
 * Single source of the libGDX gameplay render tuning values.
 *
 * <p>Extracted from {@code GdxGameScreenController} (SRP) so the render-only
 * constants (wall thickness, player scaling, infection-overlay geometry, score
 * panel sizing) live next to the render layer they configure and the screen
 * controller stays thinner. Bar heights are sourced from
 * {@link GdxGameScreenMetrics} so HUD geometry has a single definition.
 */
public final class GdxGameRenderConstants {

    private static final float WALL_THICKNESS = (float) StageConstants.WallThicknessPx;
    private static final float PLAYER_ALIVE_SCALE = 1f;
    private static final float PLAYER_DEAD_SCALE = 1.8f;
    private static final float HALF_RATIO = 0.5f;
    private static final int INFECTION_EDGE_LAYERS = 4;
    private static final float INFECTION_PULSE_SPEED = 3.2f;
    private static final float INFECTION_TRIANGLE_WIDTH = 120f;
    private static final float INFECTION_TRIANGLE_HEIGHT = 106f;
    private static final int INFECTION_GLOW_LAYERS = 6;
    private static final String INFECTION_WARNING_TEXT = "Infected!";
    private static final float TOP_MARGIN = 22f;
    private static final float SCORE_PANEL_WIDTH = 170f;
    /** Tall enough for the score line and the bombs line (≈24 px each + padding). */
    private static final float SCORE_PANEL_HEIGHT = 54f;

    private GdxGameRenderConstants() {
    }

    /** Builds the immutable render tuning block consumed by the render coordinator. */
    public static GdxGameRenderCoordinator.RenderConstants defaults() {
        return new GdxGameRenderCoordinator.RenderConstants(
                WALL_THICKNESS,
                PLAYER_ALIVE_SCALE,
                PLAYER_DEAD_SCALE,
                HALF_RATIO,
                INFECTION_EDGE_LAYERS,
                INFECTION_PULSE_SPEED,
                INFECTION_TRIANGLE_WIDTH,
                INFECTION_TRIANGLE_HEIGHT,
                INFECTION_GLOW_LAYERS,
                INFECTION_WARNING_TEXT,
                TOP_MARGIN,
                SCORE_PANEL_WIDTH,
                SCORE_PANEL_HEIGHT,
                GdxGameScreenMetrics.BOTTOM_BAR_HEIGHT,
                GdxGameScreenMetrics.HP_BAR_HEIGHT);
    }
}
