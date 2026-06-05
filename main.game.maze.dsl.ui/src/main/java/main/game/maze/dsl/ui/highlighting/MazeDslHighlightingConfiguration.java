/*
 * MazeGame DSL - Syntax Highlighting Configuration
 * 
 * Defines the colors and styles for DSL syntax highlighting.
 * 
 * Note: This configuration defines the highlighting ID scheme and their default styles.
 * To apply these styles to actual tokens/semantic elements, implement a semantic highlighting
 * calculator (ISemanticHighlightingCalculator) or token-to-attribute mapper (ITokenToAttributeIdMapper)
 * and bind it in the UI module. Currently, these styles are available in preferences but not
 * applied to syntax elements in the editor.
 */
package main.game.maze.dsl.ui.highlighting;

import org.eclipse.swt.SWT;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

/**
 * Custom highlighting configuration for MazeDsl.
 */
public class MazeDslHighlightingConfiguration extends DefaultHighlightingConfiguration {

    // Custom highlighting IDs
    public static final String GAME_NAME = "game_name";
    public static final String OPPONENT_NAME = "opponent_name";
    public static final String PATROL_NAME = "patrol_name";
    public static final String NUMERIC_VALUE = "numeric_value";
    public static final String ENUM_VALUE = "enum_value";
    public static final String CROSS_REFERENCE = "cross_reference";

    @Override
    public void configure(IHighlightingConfigurationAcceptor acceptor) {
        super.configure(acceptor);
        
        acceptor.acceptDefaultHighlighting(GAME_NAME, "Game Name", gameNameStyle());
        acceptor.acceptDefaultHighlighting(OPPONENT_NAME, "Opponent Name", opponentNameStyle());
        acceptor.acceptDefaultHighlighting(PATROL_NAME, "Patrol Name", patrolNameStyle());
        acceptor.acceptDefaultHighlighting(NUMERIC_VALUE, "Numeric Value", numericValueStyle());
        acceptor.acceptDefaultHighlighting(ENUM_VALUE, "Enum Value", enumValueStyle());
        acceptor.acceptDefaultHighlighting(CROSS_REFERENCE, "Cross Reference", crossReferenceStyle());
    }

    private TextStyle gameNameStyle() {
        TextStyle style = defaultTextStyle().copy();
        style.setStyle(SWT.BOLD);
        return style;
    }

    private TextStyle opponentNameStyle() {
        TextStyle style = defaultTextStyle().copy();
        style.setStyle(SWT.BOLD);
        return style;
    }

    private TextStyle patrolNameStyle() {
        TextStyle style = defaultTextStyle().copy();
        style.setStyle(SWT.BOLD);
        return style;
    }

    private TextStyle numericValueStyle() {
        TextStyle style = defaultTextStyle().copy();
        return style;
    }

    private TextStyle enumValueStyle() {
        TextStyle style = defaultTextStyle().copy();
        style.setStyle(SWT.ITALIC);
        return style;
    }

    private TextStyle crossReferenceStyle() {
        TextStyle style = defaultTextStyle().copy();
        style.setStyle(SWT.ITALIC);
        return style;
    }
}


