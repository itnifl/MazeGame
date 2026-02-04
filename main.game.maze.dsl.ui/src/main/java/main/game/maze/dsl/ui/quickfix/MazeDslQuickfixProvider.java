/*
 * MazeGame DSL - Quick Fix Provider
 * 
 * Provides quick fixes for validation errors and warnings.
 */
package main.game.maze.dsl.ui.quickfix;

import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;

import main.game.maze.dsl.validation.MazeDslValidator;

/**
 * Custom quickfixes for MazeDsl validation issues.
 *
 * See https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#quick-fixes
 */
public class MazeDslQuickfixProvider extends DefaultQuickfixProvider {

    /**
     * Quick fix for threat level exceeding maximum.
     */
    @Fix(MazeDslValidator.INVALID_THREAT_LEVEL)
    public void fixThreatLevel(final Issue issue, IssueResolutionAcceptor acceptor) {
        acceptor.accept(issue, 
            "Set threat level to 100", 
            "Sets the threat level to the maximum allowed value of 100.", 
            null,
            (element, context) -> {
                // Replace the invalid value with 100
                context.getXtextDocument().replace(issue.getOffset(), issue.getLength(), "100");
            }
        );
        
        acceptor.accept(issue, 
            "Set threat level to 50", 
            "Sets the threat level to a moderate value of 50.", 
            null,
            (element, context) -> {
                context.getXtextDocument().replace(issue.getOffset(), issue.getLength(), "50");
            }
        );
    }

    /**
     * Quick fix for insufficient waypoints.
     */
    @Fix(MazeDslValidator.INSUFFICIENT_WAYPOINTS)
    public void fixInsufficientWaypoints(final Issue issue, IssueResolutionAcceptor acceptor) {
        acceptor.accept(issue, 
            "Add sample waypoint", 
            "Adds a second waypoint at position (100, 100).", 
            null,
            (element, context) -> {
                // Find the closing bracket and add a waypoint before it
                String text = context.getXtextDocument().get();
                int endPos = text.indexOf("]", issue.getOffset());
                if (endPos > 0) {
                    context.getXtextDocument().replace(endPos, 0, ", (100, 100)");
                }
            }
        );
    }

    /**
     * Quick fix for mismatched character specifics.
     */
    @Fix(MazeDslValidator.MISMATCHED_SPECIFICS)
    public void fixMismatchedSpecifics(final Issue issue, IssueResolutionAcceptor acceptor) {
        acceptor.accept(issue, 
            "Remove character-specific block", 
            "Removes the mismatched character-specific configuration block.", 
            null,
            (element, context) -> {
                // This would need more sophisticated parsing to remove the block
                // For now, just highlight the issue
            }
        );
    }
}
