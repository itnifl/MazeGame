package main.game.maze.characters;

import javafx.scene.Node;
import main.game.maze.game.level.WallBuilderSpec;
import main.game.maze.opponents.Zombie;
import main.game.maze.characters.interfaces.PositionBounds;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;

/**
 * A wall builder (JavaFX): a construction worker zombie. The player can kill it like any enemy, but it cannot harm
 * the player (no touch damage) and it does not resurrect. It does not walk by the enemy AI: the shared
 * {@link main.game.maze.game.builder.WallBuilderSession} decides where it goes and what it builds, and
 * {@code FxWallBuilderController} places it.
 */
public class WallBuilderCharacter extends ZombieCharacter {

    private final WallBuilderSpec spec;
    private final int size;

    public WallBuilderCharacter(Node characterGraphics, double x, double y, int size, Zombie model, WallBuilderSpec spec) {
        super(characterGraphics, x, y, model);
        this.spec = spec;
        this.size = size;
    }

    public WallBuilderSpec getSpec() {
        return spec;
    }

    /** Where the builder stands: the centre of its sprite on the board. */
    public double centerX() {
        return getCharacterPosition().getX() + size / 2.0;
    }

    public double centerY() {
        return getCharacterPosition().getY() + size / 2.0;
    }

    /** Puts the builder so that its centre is at (x, y). */
    public void placeCentreAt(double x, double y) {
        teleportTo(x - size / 2.0, y - size / 2.0);
    }

    /** Never walks by itself. */
    @Override
    public boolean move(boolean force) {
        return false;
    }

    /** Harmless: touching the player does nothing. */
    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public void doPositionEvaluation(PositionBounds nodeBounds, ICanSubscribeAndNotifyPosition entity) {
        // no touch damage and no zombie growl
    }

    /** A killed builder stays dead. */
    @Override
    protected void resurrect() {
        // no-op: builders do not resurrect
    }
}
