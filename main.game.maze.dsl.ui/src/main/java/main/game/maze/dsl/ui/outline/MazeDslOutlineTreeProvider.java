/*
 * MazeGame DSL - Outline View
 * 
 * Defines the structure of the outline view for DSL files.
 */
package main.game.maze.dsl.ui.outline;

import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider;

import main.game.maze.dsl.mazeDsl.*;

/**
 * Customization of the default outline structure.
 *
 * See https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#outline
 */
public class MazeDslOutlineTreeProvider extends DefaultOutlineTreeProvider {

    /**
     * Creates outline nodes for game configuration.
     */
    protected void _createChildren(IOutlineNode parentNode, GameConfiguration game) {
        // Difficulty section
        if (game.getDifficulty() != null) {
            createNode(parentNode, game.getDifficulty());
        }
        
        // Opponents section
        for (OpponentConfig opponent : game.getOpponents()) {
            createNode(parentNode, opponent);
        }
        
        // Patrols section
        for (PatrolConfig patrol : game.getPatrols()) {
            createNode(parentNode, patrol);
        }
        
        // Loot tables section
        for (LootTableConfig lootTable : game.getLootTables()) {
            createNode(parentNode, lootTable);
        }
    }

    /**
     * Suppress children for difficulty (leaf node).
     */
    protected boolean _isLeaf(DifficultyConfig difficulty) {
        return true;
    }

    /**
     * Suppress children for waypoints (leaf node).
     */
    protected boolean _isLeaf(Waypoint waypoint) {
        return true;
    }

    /**
     * Display text for game configuration.
     */
    protected Object _text(GameConfiguration game) {
        return "Game: " + game.getName();
    }

    /**
     * Display text for opponent.
     */
    protected Object _text(OpponentConfig opponent) {
        String type = opponent.getType() != null ? opponent.getType().getLiteral() : "unknown";
        return opponent.getName() + " (" + type + ")";
    }

    /**
     * Display text for patrol.
     */
    protected Object _text(PatrolConfig patrol) {
        return "Patrol: " + patrol.getName() + " [" + patrol.getWaypoints().size() + " waypoints]";
    }

    /**
     * Display text for difficulty.
     */
    protected Object _text(DifficultyConfig difficulty) {
        String level = difficulty.getLevel() != null ? difficulty.getLevel().getLiteral() : "unknown";
        return "Difficulty: " + level;
    }

    /**
     * Display text for loot table.
     */
    protected Object _text(LootTableConfig lootTable) {
        return "Loot: " + lootTable.getName() + " [" + lootTable.getItems().size() + " items]";
    }
}


