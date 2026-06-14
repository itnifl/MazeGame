package main.game.maze.characters.interfaces;

/**
 * Represents an animation action performed on a character's graphical representation.
 * The {@code characterGraphics} parameter is typed as {@code Object} to keep this
 * interface backend-neutral; JavaFX implementations receive a {@code javafx.scene.Node}.
 */
public interface ICharacterAction {
    void doAction(Object characterGraphics);
}
