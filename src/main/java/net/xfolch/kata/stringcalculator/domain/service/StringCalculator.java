package net.xfolch.kata.stringcalculator.domain.service;

import net.xfolch.kata.stringcalculator.domain.Try;

/**
 * Applies numeric operations over strings
 * <p>
 * Created by xfolch on 9/7/16.
 */
@FunctionalInterface
public interface StringCalculator {

    /**
     * Given a string that contains a set of numbers separated by at least one separator this method will return the
     * sum of them.
     *
     * @param numbers a string that will contain a set of numbers separated by separators
     * @return a functional structure that either will contain the sum of the numbers or an exception
     */
    Try<Integer> add(String numbers);

}
