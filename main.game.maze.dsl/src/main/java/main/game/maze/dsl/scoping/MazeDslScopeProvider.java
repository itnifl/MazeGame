/*
 * MazeGame DSL - Scope Provider
 * 
 * This class defines scoping rules for cross-references in the DSL.
 */
package main.game.maze.dsl.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;

import main.game.maze.dsl.mazeDsl.*;

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 */
public class MazeDslScopeProvider extends AbstractMazeDslScopeProvider {

    @Override
    public IScope getScope(EObject context, EReference reference) {
        // Scope for patrol references in opponents
        if (reference == MazeDslPackage.Literals.OPPONENT_CONFIG__PATROL_REF) {
            GameConfiguration game = getContainingGame(context);
            if (game != null) {
                return Scopes.scopeFor(game.getPatrols());
            }
        }
        
        // Scope for loot table references in opponents
        if (reference == MazeDslPackage.Literals.OPPONENT_CONFIG__LOOT_REF) {
            GameConfiguration game = getContainingGame(context);
            if (game != null) {
                return Scopes.scopeFor(game.getLootTables());
            }
        }
        
        return super.getScope(context, reference);
    }

    /**
     * Finds the containing GameConfiguration for any model element.
     */
    private GameConfiguration getContainingGame(EObject context) {
        EObject current = context;
        while (current != null) {
            if (current instanceof GameConfiguration) {
                return (GameConfiguration) current;
            }
            current = current.eContainer();
        }
        return null;
    }
}


