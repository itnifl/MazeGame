package main.game.maze.libgdx.controller;

import main.game.maze.libgdx.GdxGameContext;
import main.game.maze.libgdx.adapter.AbstractLegacyAdapterScreen;

/**
 * Transitional screen wrapper around the existing monolithic gameplay class.
 */
public final class LegacyPlayScreenController extends AbstractLegacyAdapterScreen {

    public LegacyPlayScreenController(GdxGameContext context) {
        this(context, false, null);
    }

    public LegacyPlayScreenController(GdxGameContext context, boolean autoStartOnCreate) {
        this(context, autoStartOnCreate, null);
    }

    public LegacyPlayScreenController(GdxGameContext context, boolean autoStartOnCreate, Runnable returnToMenuAction) {
        super(context, new GdxGameScreenController(GdxGameScreenOptions.builder()
                .runtimeConfig(context.runtimeConfig())
                .assetService(context.assets())
                .ownsAssetService(false)
                .autoStartOnCreate(autoStartOnCreate)
                .returnToMenuAction(returnToMenuAction)
                .build()));
    }

    /**
     * Creates a screen that boots a game session and immediately opens the high-scores overlay.
     */
    public static LegacyPlayScreenController forHighScores(GdxGameContext context, Runnable returnToMenuAction) {
        GdxGameScreenController controller = GdxGameScreenController.forHighScores(
                context.runtimeConfig(), context.assets(), returnToMenuAction);
        return new LegacyPlayScreenController(context, controller);
    }

    private LegacyPlayScreenController(GdxGameContext context, GdxGameScreenController controller) {
        super(context, controller);
    }
}
