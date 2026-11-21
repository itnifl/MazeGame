/*
 * MazeGame DSL - Value converters
 */
package main.game.maze.dsl;

import java.math.BigDecimal;

import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.INode;

/**
 * Writes decimal numbers the way the grammar reads them. Java prints 0.0001 as {@code 1.0E-4}, which the
 * {@code DOUBLE} and {@code DOUBLE_OBJECT} rules cannot parse (they take digits, a dot and digits), so a model
 * serialized without its original text would not parse again. Plain decimal notation always does.
 */
public class MazeDslValueConverters extends DefaultTerminalConverters {

    private static final IValueConverter<Double> PLAIN_DECIMAL = new IValueConverter<Double>() {
        @Override
        public String toString(Double value) throws ValueConverterException {
            if (value == null) {
                throw new ValueConverterException("a decimal value may not be null", null, null);
            }
            if (value.isNaN() || value.isInfinite()) {
                throw new ValueConverterException("a decimal value must be finite: " + value, null, null);
            }
            return new BigDecimal(Double.toString(value)).toPlainString();
        }

        @Override
        public Double toValue(String string, INode node) throws ValueConverterException {
            if (string == null || string.isBlank()) {
                throw new ValueConverterException("a decimal value may not be empty", node, null);
            }
            try {
                // the rule is "-" INT "." INT, so hidden whitespace may sit between its tokens
                return Double.valueOf(string.replaceAll("\\s", ""));
            } catch (NumberFormatException e) {
                throw new ValueConverterException("not a decimal value: " + string, node, e);
            }
        }
    };

    @ValueConverter(rule = "DOUBLE")
    public IValueConverter<Double> DOUBLE() {
        return PLAIN_DECIMAL;
    }

    @ValueConverter(rule = "DOUBLE_OBJECT")
    public IValueConverter<Double> DOUBLE_OBJECT() {
        return PLAIN_DECIMAL;
    }
}
