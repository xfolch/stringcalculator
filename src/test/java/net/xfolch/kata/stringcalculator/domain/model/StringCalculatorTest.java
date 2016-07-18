package net.xfolch.kata.stringcalculator.domain.model;

import net.xfolch.kata.stringcalculator.domain.Try;
import net.xfolch.kata.stringcalculator.port.adapter.StringCalculators;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by xfolch on 9/7/16.
 */
public class StringCalculatorTest {

    @Test
    public void singleNumberTest() {
        String s = "1";

        Try<Integer> res = StringCalculators.unit().add(s);

        assertThat(res, notNullValue());
        assertThat(res.isSuccess(), is(true));
        assertThat(res.get(), is(1));
    }

    @Test
    public void singleNumberTestApplyingACommaSeparatedSolution() {
        String s = "1";

        Try<Integer> res = StringCalculators.commaSeparated().add(s);

        assertThat(res, notNullValue());
        assertThat(res.isSuccess(), is(true));
        assertThat(res.get(), is(1));
    }

    @Test
    public void failWhenTryingToSolveCommaSeparatedStringByUnitSolution() {
        String s = "1,2";

        Try<Integer> res = StringCalculators.unit().add(s);

        assertThat(res, notNullValue());
        assertThat(res.isFailure(), is(true));
    }

    @Test
    public void addTwoNumbersSeparatedByCommaApplyingACommaSeparatedSolution() {
        String s = "1,2";

        Try<Integer> res = StringCalculators.commaSeparated().add(s);

        assertThat(res, notNullValue());
        assertThat(res.isSuccess(), is(true));
        assertThat(res.get(), is(3));
    }

    @Test
    public void addTwoDirtyNumbersSeparatedByCommaApplyingACommaSeparatedSolution() {
        String s = "1,   2,";

        Try<Integer> res = StringCalculators.commaSeparated().add(s);

        assertThat(res, notNullValue());
        assertThat(res.isSuccess(), is(true));
        assertThat(res.get(), is(3));
    }

    @Test
    public void failAddingTwoDirtyNumbersSeparatedByCommaApplyingALineBreakSeparatedSolution() {
        String s = "1,   2,";

        Try<Integer> res = StringCalculators.lineBreakSeparated().add(s);

        assertThat(res, notNullValue());
        assertThat(res.isFailure(), is(true));
    }

    @Test
    public void failAddingTwoNumbersSeparatedByCommaAndOneByLineBreakApplyingACommaSeparatedSolution() {
        String s = "1,2\n3";

        Try<Integer> res = StringCalculators.commaSeparated().add(s);

        assertThat(res, notNullValue());
        assertThat(res.isFailure(), is(true));
    }

    @Test
    public void addTwoNumbersSeparatedByCommaAndOneByLineBreakApplyingACommaAndLineBreakSeparatedSolution() {
        String s = "1,2\n3";

        Try<Integer> res = StringCalculators.commaAndLineBreakSeparated().add(s);

        assertThat(res, notNullValue());
        assertThat(res.isSuccess(), is(true));
        assertThat(res.get(), is(6));
    }

    @Test
    public void addTwoNumbersSeparatedByCommaAndAnotherTwoByLineBreakApplyingACommaAndLineBreakSeparatedSolution() {
        String s = "1,2\n3,4";

        Try<Integer> res = StringCalculators.commaAndLineBreakSeparated().add(s);

        assertThat(res, notNullValue());
        assertThat(res.isSuccess(), is(true));
        assertThat(res.get(), is(10));
    }

    @Test
    public void addDirtyNumbersByOurFinalSolution() {
        String s = "1,   2,\n3  \n,4  , 6";

        Try<Integer> res = StringCalculators.commaAndLineBreakSeparated().add(s);

        assertThat(res, notNullValue());
        assertThat(res.isSuccess(), is(true));
        assertThat(res.get(), is(16));
    }

    @Test
    public void failWhenTryingToAddNoNumbers() {
        String s = "1,   2,\n3  \n,4  , 6,null";

        Try<Integer> res = StringCalculators.commaAndLineBreakSeparated().add(s);

        assertThat(res, notNullValue());
        assertThat(res.isFailure(), is(true));
    }
}
