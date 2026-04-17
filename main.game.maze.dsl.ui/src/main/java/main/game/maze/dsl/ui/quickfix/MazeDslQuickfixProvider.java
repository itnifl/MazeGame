/*
 * MazeGame DSL - Quick Fix Provider
 * 
 * Provides quick fixes for validation errors and warnings.
 */
package main.game.maze.dsl.ui.quickfix;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static final Pattern PATH_BRACKET_PATTERN = Pattern.compile("path\\s*\\[");

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
                String text = context.getXtextDocument().get();
                int issueOffset = Math.max(0, issue.getOffset());
                
                Matcher matcher = PATH_BRACKET_PATTERN.matcher(text);
                int pathPos = -1;
                int bracketPos = -1;
                
                while (matcher.find()) {
                    if (matcher.start() >= issueOffset) {
                        pathPos = matcher.start();
                        bracketPos = matcher.end() - 1;
                        break;
                    }
                }
                
                if (pathPos < 0) {
                    matcher.reset();
                    int lastPathPos = -1;
                    int lastBracketPos = -1;
                    while (matcher.find() && matcher.start() < issueOffset) {
                        lastPathPos = matcher.start();
                        lastBracketPos = matcher.end() - 1;
                    }
                    pathPos = lastPathPos;
                    bracketPos = lastBracketPos;
                }
                
                if (pathPos < 0 || bracketPos < 0) {
                    return;
                }

                int listStart = bracketPos;
                int listEnd = text.indexOf(']', listStart);
                if (listEnd < 0) {
                    return;
                }

                String existing = text.substring(listStart + 1, listEnd).trim();
                String insertion = existing.isEmpty() ? "(100, 100)" : ", (100, 100)";
                context.getXtextDocument().replace(listEnd, 0, insertion);
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
                String text = context.getXtextDocument().get();
                int issueOffset = issue.getOffset();
                if (issueOffset < 0 || issueOffset >= text.length()) {
                    return;
                }

                int openBrace = text.indexOf('{', issueOffset);
                if (openBrace < 0) {
                    return;
                }

                int depth = 0;
                int closeBrace = -1;
                for (int i = openBrace; i < text.length(); i++) {
                    char ch = text.charAt(i);
                    if (ch == '{') {
                        depth++;
                    } else if (ch == '}') {
                        depth--;
                        if (depth == 0) {
                            closeBrace = i;
                            break;
                        }
                    }
                }

                if (closeBrace < 0) {
                    return;
                }

                int start = issueOffset;
                while (start > 0 && Character.isWhitespace(text.charAt(start - 1))) {
                    start--;
                }

                int end = closeBrace + 1;
                while (end < text.length() && Character.isWhitespace(text.charAt(end))) {
                    end++;
                }

                context.getXtextDocument().replace(start, end - start, "");
            }
        );
    }
}
