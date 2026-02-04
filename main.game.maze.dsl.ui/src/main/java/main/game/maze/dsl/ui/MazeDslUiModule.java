/*
 * MazeGame DSL - UI Module
 * 
 * Guice module for UI components.
 */
package main.game.maze.dsl.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * Use this class to register components to be used within the Eclipse IDE.
 */
public class MazeDslUiModule extends AbstractMazeDslUiModule {

    public MazeDslUiModule(AbstractUIPlugin plugin) {
        super(plugin);
    }

    // Custom UI bindings can be added here
    // For example:
    // public Class<? extends IHighlightingConfiguration> bindIHighlightingConfiguration() {
    //     return MazeDslHighlightingConfiguration.class;
    // }
}
