/*
 * MazeGame DSL - Standalone Setup
 * 
 * This class is the entry point for initializing the MazeDsl language
 * in a standalone (non-OSGi) context.
 */
package main.game.maze.dsl;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
public class MazeDslStandaloneSetup extends MazeDslStandaloneSetupGenerated {

    public static void doSetup() {
        new MazeDslStandaloneSetup().createInjectorAndDoEMFRegistration();
    }
    
    @Override
    public Injector createInjector() {
        return Guice.createInjector(new MazeDslRuntimeModule());
    }
}


