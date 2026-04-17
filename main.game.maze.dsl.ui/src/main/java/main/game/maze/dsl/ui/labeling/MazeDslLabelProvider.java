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
        String type = element.getType() != null ? element.getType().getLiteral() : "unknown";
        return element.getName() + " [" + type + "]";
    }

    public String text(PatrolConfig element) {
        return "Patrol: " + element.getName();
    }

    public String text(DifficultyConfig element) {
        String level = element.getLevel() != null ? element.getLevel().getLiteral() : "unknown";
        return "Difficulty: " + level;
    }

    public String text(LootTableConfig element) {
        return "Loot: " + element.getName();
    }

    public String text(Waypoint element) {
        return "(" + element.getX() + ", " + element.getY() + ")";
    }

    public String text(EnemyLimit element) {
        String type = element.getType() != null ? element.getType().getLiteral() : "unknown";
        return type + " max " + element.getMaxCount();
    }
}
