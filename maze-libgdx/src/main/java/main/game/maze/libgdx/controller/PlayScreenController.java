package main.game.maze.libgdx.controller;

import main.game.maze.difficulties.Difficulty;
import main.game.maze.libgdx.GdxGameContext;
import main.game.maze.libgdx.adapter.AbstractLegacyAdapterScreen;

/**
 * Transitional gameplay screen. Internally delegates to legacy gameplay while
 * the monolith is being extracted.
 */
public final class PlayScreenController extends AbstractLegacyAdapterScreen {

    public PlayScreenController(GdxGameContext context, Runnable returnToMenuAction) {
        this(context, null, returnToMenuAction);
    }

    public PlayScreenController(GdxGameContext context, Difficulty forcedDifficulty, Runnable returnToMenuAction) {
        super(context, new GdxGameScreenController(
                null,
                context.runtimeConfig(),
                context.assets(),
                false,
                false,
                true,
                returnToMenuAction,
                forcedDifficulty));
    }
}
