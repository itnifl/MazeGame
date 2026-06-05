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
        super(context, new GdxGameScreenController(
                null,
                context.runtimeConfig(),
                context.assets(),
                false,
                autoStartOnCreate,
                false,
                returnToMenuAction));
    }
}



