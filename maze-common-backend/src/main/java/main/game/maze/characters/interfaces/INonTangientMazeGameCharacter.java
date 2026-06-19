package main.game.maze.characters.interfaces;

public interface INonTangientMazeGameCharacter {
    double getNonTangientEnergy();
    void setNonTangientEnergy(double value);
    void setCharacterOpacity(double value);

    /**
     * Returns the configured visibility level (0–100). Governs the base opacity
     * of a solid instance; also caps opacity during phasing.
     * Default is 100 (fully visible) for any non-ghost implementation.
     */
    default int getVisibilityLevel() {
        return 100;
    }
}
