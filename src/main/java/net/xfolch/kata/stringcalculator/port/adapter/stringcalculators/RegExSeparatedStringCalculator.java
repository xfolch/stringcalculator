package net.xfolch.kata.stringcalculator.port.adapter.stringcalculators;

import net.xfolch.kata.stringcalculator.domain.Try;
import net.xfolch.kata.stringcalculator.domain.service.StringCalculator;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Root implementation of the {@link StringCalculator} that is able to calculate the add of
 *
 * Created by xfolch on 9/7/16.
 */
public final class RegExSeparatedStringCalculator implements StringCalculator {

    private final String regex;
    private final StringCalculator stringCalculator;

    private RegExSeparatedStringCalculator(String regex, StringCalculator stringCalculator) {
        this.regex = regex;
        this.stringCalculator = stringCalculator;
    }

    public static RegExSeparatedStringCalculator newOne(String regex, StringCalculator stringCalculator) {
        return new RegExSeparatedStringCalculator(regex, stringCalculator);
    }

    @Override
    public Try<Integer> add(String numbers) {
        String[] values = numbers.split(regex);

        Stream<Try<Integer>> stream = Arrays.asList(values)
                .stream()
                .filter(s -> s != null && !s.trim().isEmpty())
                .map(stringCalculator::add);

        return Try.collapse(stream)
                .map(s -> s.reduce(0, Integer::sum));
    }

}
