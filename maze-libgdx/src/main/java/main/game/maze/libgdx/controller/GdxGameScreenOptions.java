package main.game.maze.libgdx.controller;

import main.game.maze.common.graphics.config.MazeRuntimeConfig;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.libgdx.service.GdxAssetService;
import main.game.maze.mazeworld.generators.MazeArena;

/**
 * Immutable construction options for {@link GdxGameScreenController}.
 *
 * <p>Introduced for SR-41 to replace the previous telescoping-constructor chain
 * (multiple overloads ending in four consecutive boolean parameters:
 * {@code ownsAssetService}, {@code autoStartOnCreate},
 * {@code immediateStartOnCreate}, {@code showHighScoresOnCreate}). Call sites now
 * read as self-documenting builder calls, and adding a new flag only touches this
 * class instead of every overload. This addresses the long-parameter-list and
 * boolean-parameter code smell (CRR-15).
 */
public final class GdxGameScreenOptions {

    /** Default maze cell size in pixels, matching {@link MazeRuntimeConfig#DEFAULT}. */
    public static final float DEFAULT_CELL_SIZE = 48f;

    private final MazeArena arena;
    private final float cellSize;
    private final boolean useRealMaze;
    private final GdxAssetService assetService;
    private final boolean ownsAssetService;
    private final boolean autoStartOnCreate;
    private final boolean immediateStartOnCreate;
    private final boolean showHighScoresOnCreate;
    private final Runnable returnToMenuAction;
    private final Difficulty forcedDifficulty;

    private GdxGameScreenOptions(Builder builder) {
        this.arena = builder.arena;
        this.cellSize = builder.cellSize;
        this.useRealMaze = builder.useRealMaze;
        this.assetService = builder.assetService != null ? builder.assetService : new GdxAssetService();
        this.ownsAssetService = builder.ownsAssetService;
        this.autoStartOnCreate = builder.autoStartOnCreate;
        this.immediateStartOnCreate = builder.immediateStartOnCreate;
        this.showHighScoresOnCreate = builder.showHighScoresOnCreate;
        this.returnToMenuAction = builder.returnToMenuAction;
        this.forcedDifficulty = builder.forcedDifficulty;
    }

    /** Creates a builder pre-populated with the controller's default values. */
    public static Builder builder() {
        return new Builder();
    }

    public MazeArena arena() {
        return arena;
    }

    public float cellSize() {
        return cellSize;
    }

    public boolean useRealMaze() {
        return useRealMaze;
    }

    public GdxAssetService assetService() {
        return assetService;
    }

    public boolean ownsAssetService() {
        return ownsAssetService;
    }

    public boolean autoStartOnCreate() {
        return autoStartOnCreate;
    }

    public boolean immediateStartOnCreate() {
        return immediateStartOnCreate;
    }

    public boolean showHighScoresOnCreate() {
        return showHighScoresOnCreate;
    }

    public Runnable returnToMenuAction() {
        return returnToMenuAction;
    }

    public Difficulty forcedDifficulty() {
        return forcedDifficulty;
    }

    /**
     * Fluent builder for {@link GdxGameScreenOptions}. Defaults mirror the
     * controller's previous no-argument constructor: standalone maze owned asset
     * service, auto start on create, and no overlay shortcuts.
     */
    public static final class Builder {

        private MazeArena arena;
        private float cellSize = DEFAULT_CELL_SIZE;
        private boolean useRealMaze = true;
        private GdxAssetService assetService;
        private boolean ownsAssetService = true;
        private boolean autoStartOnCreate = true;
        private boolean immediateStartOnCreate;
        private boolean showHighScoresOnCreate;
        private Runnable returnToMenuAction;
        private Difficulty forcedDifficulty;

        private Builder() {
        }

        public Builder arena(MazeArena value) {
            this.arena = value;
            return this;
        }

        public Builder cellSize(float value) {
            this.cellSize = value;
            return this;
        }

        public Builder useRealMaze(boolean value) {
            this.useRealMaze = value;
            return this;
        }

        /** Copies {@code cellSize} and {@code useRealMaze} from a runtime config. */
        public Builder runtimeConfig(MazeRuntimeConfig cfg) {
            this.cellSize = cfg.cellSize();
            this.useRealMaze = cfg.useRealMaze();
            return this;
        }

        public Builder assetService(GdxAssetService value) {
            this.assetService = value;
            return this;
        }

        public Builder ownsAssetService(boolean value) {
            this.ownsAssetService = value;
            return this;
        }

        public Builder autoStartOnCreate(boolean value) {
            this.autoStartOnCreate = value;
            return this;
        }

        public Builder immediateStartOnCreate(boolean value) {
            this.immediateStartOnCreate = value;
            return this;
        }

        public Builder showHighScoresOnCreate(boolean value) {
            this.showHighScoresOnCreate = value;
            return this;
        }

        public Builder returnToMenuAction(Runnable value) {
            this.returnToMenuAction = value;
            return this;
        }

        public Builder forcedDifficulty(Difficulty value) {
            this.forcedDifficulty = value;
            return this;
        }

        public GdxGameScreenOptions build() {
            return new GdxGameScreenOptions(this);
        }
    }
}
