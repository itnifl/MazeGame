package main.game.maze.characters;

import javafx.scene.Node;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.game.level.GuardTowerSpec;
import main.game.maze.opponents.Zombie;

/**
 * A guard tower (JavaFX): an enemy that never moves, takes a very great deal of damage, looks more damaged as it
 * does (the damage-state sprites {@code guard-tower-damaged.png} ... come with the enemy's health), and when it falls
 * leaves no corpse: the explosion is the end of it. What it shoots, and when, is decided by the shared
 * {@link main.game.maze.game.level.GuardTowerSession}, driven by {@code FxGuardTowerController}. Killing it is
 * scored like any enemy, and it never comes back.
 */
public class GuardTowerCharacter extends ZombieCharacter {

    private final GuardTowerSpec spec;
    private final double centerX;
    private final double centerY;

    /** @param size side of the sprite in pixels; (centerX, centerY) is where the tower stands (world space) */
    public GuardTowerCharacter(Node characterGraphics, double centerX, double centerY, int size, Zombie model, GuardTowerSpec spec) {
        super(characterGraphics, centerX - size / 2.0, centerY - size / 2.0, model);
        this.characterXYSizeFromPoint = size;
        this.spec = spec;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public GuardTowerSpec getSpec() {
        return spec;
    }

    /** Where the tower stands: its centre in world space (it never moves). */
    public double centerX() {
        return centerX;
    }

    public double centerY() {
        return centerY;
    }

    /** Never moves. */
    @Override
    public boolean move(boolean force) {
        return false;
    }

    /** A tower falls apart in the blast the controller starts; no remains and no zombie death animation. */
    @Override
    protected void leaveRemains(DeathCause cause) {
        // no corpse
    }

    @Override
    public void PlayDieAnimation() {
        // the blast is the death animation
    }

    /** A destroyed tower stays destroyed. */
    @Override
    protected void resurrect() {
        // no-op: towers do not resurrect
    }
}
