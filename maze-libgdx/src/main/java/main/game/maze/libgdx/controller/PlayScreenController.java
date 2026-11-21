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
        this(context, forcedDifficulty, false, returnToMenuAction);
    }

    /** @param loadSavedGame start from the saved level, with the state it was entered at, instead of a new game */
    public PlayScreenController(GdxGameContext context, Difficulty forcedDifficulty, boolean loadSavedGame, Runnable returnToMenuAction) {
        super(context, new GdxGameScreenController(GdxGameScreenOptions.builder()
                .runtimeConfig(context.runtimeConfig())
                .assetService(context.assets())
                .ownsAssetService(false)
                .autoStartOnCreate(false)
                .immediateStartOnCreate(true)
                .returnToMenuAction(returnToMenuAction)
                .forcedDifficulty(forcedDifficulty)
                .loadSavedGame(loadSavedGame)
                .build()));
    }
}
