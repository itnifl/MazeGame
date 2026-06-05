/*
 * MazeGame DSL - IDE Setup
 * 
 * Setup class for initializing the language in IDE context.
 */
package main.game.maze.dsl.ide;

import com.google.inject.Guice;
import com.google.inject.Injector;
import main.game.maze.dsl.MazeDslRuntimeModule;
import main.game.maze.dsl.MazeDslStandaloneSetup;
import org.eclipse.xtext.util.Modules2;

/**
 * Initialization support for running Xtext languages as language servers.
 */
public class MazeDslIdeSetup extends MazeDslStandaloneSetup {

    @Override
    public Injector createInjector() {
        return Guice.createInjector(Modules2.mixin(new MazeDslRuntimeModule(), new MazeDslIdeModule()));
    }
}


