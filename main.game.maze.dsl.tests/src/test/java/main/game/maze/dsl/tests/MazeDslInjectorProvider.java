/*
 * MazeGame DSL - Injector Provider for Tests
 */
package main.game.maze.dsl.tests;

import main.game.maze.dsl.MazeDslStandaloneSetup;
import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.IRegistryConfigurator;

import com.google.inject.Injector;

/**
 * Provides the Guice injector for tests.
 */
public class MazeDslInjectorProvider implements IInjectorProvider, IRegistryConfigurator {

    protected Injector injector;

    @Override
    public Injector getInjector() {
        if (injector == null) {
            injector = new MazeDslStandaloneSetup().createInjectorAndDoEMFRegistration();
        }
        return injector;
    }

    @Override
    public void restoreRegistry() {
        // Nothing to restore
    }

    @Override
    public void setupRegistry() {
        getInjector();
    }
}


