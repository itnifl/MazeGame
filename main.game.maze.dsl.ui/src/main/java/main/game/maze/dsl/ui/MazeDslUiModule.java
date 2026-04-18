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
 * Currently bound:
 * - IHighlightingConfiguration: Provides color/style scheme for DSL elements
 * 
 * Future enhancements:
 * - ISemanticHighlightingCalculator: To assign highlighting IDs to semantic tokens
 * - ITokenToAttributeIdMapper: Alternative approach to map lexical tokens to highlighting IDs
 */
public class MazeDslUiModule extends AbstractMazeDslUiModule {

    public MazeDslUiModule(AbstractUIPlugin plugin) {
        super(plugin);
    }

    public Class<? extends IHighlightingConfiguration> bindIHighlightingConfiguration() {
        return MazeDslHighlightingConfiguration.class;
    }
}
