package main.game.maze.game.level;

/** The three difficulty tiers a level can configure; mirrors the {@code DifficultyTier} enum of {@code levels.ecore}. */
public enum DifficultyTier {
    EASY,
    NORMAL,
    HARD;

    /**
     * The tier of a difficulty, by its model class name ({@code EasyDifficulty}, {@code NormalDifficulty},
     * {@code HardDifficulty}); anything else counts as normal.
     */
    public static DifficultyTier fromDifficultyName(String eClassName) {
        if (eClassName == null) {
            return NORMAL;
        }
        return switch (eClassName) {
            case "EasyDifficulty" -> EASY;
            case "HardDifficulty" -> HARD;
            default -> NORMAL;
        };
    }
}
