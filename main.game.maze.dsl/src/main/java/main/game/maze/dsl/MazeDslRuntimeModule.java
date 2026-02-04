/*
 * MazeGame DSL - Runtime Module
 * 
 * This class provides Guice bindings for the runtime components of the DSL.
 */
package main.game.maze.dsl;

import main.game.maze.dsl.scoping.MazeDslScopeProvider;
import main.game.maze.dsl.validation.MazeDslValidator;
import main.game.maze.dsl.generator.MazeDslGenerator;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class MazeDslRuntimeModule extends AbstractMazeDslRuntimeModule {

    // Custom bindings can be added here
    // For example:
    // @Override
    // public Class<? extends IScopeProvider> bindIScopeProvider() {
    //     return MazeDslScopeProvider.class;
    // }
}
