/*
 * MazeGame DSL - Label Provider
 * 
 * Provides labels and icons for model elements in the UI.
 */
package main.game.maze.dsl.ui.labeling;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

import com.google.inject.Inject;

import main.game.maze.dsl.mazeDsl.*;

/**
 * Provides labels for EObjects.
 * 
 * See https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#label-provider
 */
public class MazeDslLabelProvider extends DefaultEObjectLabelProvider {

    @Inject
    public MazeDslLabelProvider(AdapterFactoryLabelProvider delegate) {
        super(delegate);
    }

    public String text(GameConfiguration element) {
        return "Game: " + element.getName();
    }

    public String text(OpponentConfig element) {
        return element.getName() + " [" + element.getType().getLiteral() + "]";
    }

    public String text(PatrolConfig element) {
        return "Patrol: " + element.getName();
    }

    public String text(DifficultyConfig element) {
        return "Difficulty: " + element.getLevel().getLiteral();
    }

    public String text(LootTableConfig element) {
        return "Loot: " + element.getName();
    }

    public String text(Waypoint element) {
        return "(" + element.getX() + ", " + element.getY() + ")";
    }

    public String text(EnemyLimit element) {
        return element.getType().getLiteral() + " max " + element.getMaxCount();
    }

    // Icons - return icon file names
    public String image(GameConfiguration element) {
        return "game.png";
    }

    public String image(OpponentConfig element) {
        switch (element.getType()) {
            case ZOMBIE: return "zombie.png";
            case GHOST: return "ghost.png";
            case PUMPKINBOMBER: return "pumpkin.png";
            default: return "enemy.png";
        }
    }

    public String image(PatrolConfig element) {
        return "patrol.png";
    }

    public String image(DifficultyConfig element) {
        return "difficulty.png";
    }
}
