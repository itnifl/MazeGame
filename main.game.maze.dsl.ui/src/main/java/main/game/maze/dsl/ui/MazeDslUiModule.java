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
 */
public class MazeDslUiModule extends AbstractMazeDslUiModule {

    public MazeDslUiModule(AbstractUIPlugin plugin) {
        super(plugin);
    }

    public Class<? extends IHighlightingConfiguration> bindIHighlightingConfiguration() {
        return MazeDslHighlightingConfiguration.class;
    }
}
