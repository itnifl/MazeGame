/*
 * MazeGame DSL - UI Module
 * 
 * Guice module for UI components.
 */
package main.game.maze.dsl.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;

import main.game.maze.dsl.ui.highlighting.MazeDslHighlightingConfiguration;

/**
 * Use this class to register components to be used within the Eclipse IDE.
 * 
 * Custom semantic or lexical highlighting bindings should only be added once
 * a corresponding ISemanticHighlightingCalculator or ITokenToAttributeIdMapper
 * is implemented to emit the configured highlighting IDs.
 * 
 * Currently bound:
 * - IHighlightingConfiguration: Provides color/style scheme for DSL elements (available in preferences)
 * 
 * Note: The custom highlighting IDs defined in MazeDslHighlightingConfiguration are currently
 * only visible in the IDE preferences. To apply them to the editor, implement and bind a
 * semantic highlighting calculator or token-to-attribute mapper.
 */
public class MazeDslUiModule extends AbstractMazeDslUiModule {

    public MazeDslUiModule(AbstractUIPlugin plugin) {
        super(plugin);
    }

    public Class<? extends IHighlightingConfiguration> bindIHighlightingConfiguration() {
        return MazeDslHighlightingConfiguration.class;
    }
}


