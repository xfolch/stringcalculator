package net.xfolch.kata.stringcalculator.port.adapter;

import net.xfolch.kata.stringcalculator.domain.Try;
import net.xfolch.kata.stringcalculator.domain.service.StringCalculator;
import net.xfolch.kata.stringcalculator.port.adapter.stringcalculators.RegExSeparatedStringCalculator;

import java.util.function.UnaryOperator;

/**
 * Created by xfolch on 9/7/16.
 */
public final class StringCalculators {

    private static final String COMMA_REGEX = "[,]";
    private static final String LINEBREAK_REGEX = "[\\n]";

    private StringCalculators() {
    }

    public static StringCalculator unit() {
        return numbers -> Try.newOne(() -> Integer.parseInt(numbers.trim()));
    }

    public static StringCalculator commaSeparated() {
        return UnaryOperator.<StringCalculator>identity()
                .andThen(StringCalculators::nullSafe)
                .andThen(StringCalculators::unsafeCommaSeparated)
                .apply(unit());
    }

    public static StringCalculator lineBreakSeparated() {
        return UnaryOperator.<StringCalculator>identity()
                .andThen(StringCalculators::nullSafe)
                .andThen(StringCalculators::unsafeLineBreakSeparated)
                .apply(unit());
    }

    public static StringCalculator commaAndLineBreakSeparated() {
        return UnaryOperator.<StringCalculator>identity()
                .andThen(StringCalculators::nullSafe)
                .andThen(StringCalculators::unsafeCommaSeparated)
                .andThen(StringCalculators::unsafeLineBreakSeparated)
                .apply(unit());
    }

    private static StringCalculator nullSafe(StringCalculator stringCalculator) {
        return numbers -> {
            if (numbers == null) {
                return Try.failure(new NullPointerException("Numbers must contain at least one value"));
            }

            return stringCalculator.add(numbers);
        };
    }

    private static StringCalculator unsafeLineBreakSeparated(StringCalculator stringCalculator) {
        return RegExSeparatedStringCalculator.newOne(LINEBREAK_REGEX, stringCalculator);
    }

    private static StringCalculator unsafeCommaSeparated(StringCalculator stringCalculator) {
        return RegExSeparatedStringCalculator.newOne(COMMA_REGEX, stringCalculator);
    }

}
