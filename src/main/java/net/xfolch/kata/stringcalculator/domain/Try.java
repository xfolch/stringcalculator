package net.xfolch.kata.stringcalculator.domain;

import java.util.concurrent.Callable;
import java.util.stream.Stream;

/**
 * Sealed type that represents a computation that either succeeds or fails.
 * <p>
 * Using this immutable functional data structure allows you to separate the execution from the error handling
 * <p>
 * Created by xfolch on 9/7/16.
 */
public abstract class Try<T> {

    /**
     * Function that could throw an exception, unlike Java one's
     */
    @FunctionalInterface
    public interface Function<A, B> {
        B apply(A a) throws Exception;
    }

    /**
     * Private constructor to keep it sealed
     */
    private Try() {
    }

    /**
     * Factory method that executes a {@code callable} and encapsulates the possible exception into this structure
     */
    public static <T> Try<T> newOne(Callable<T> callable) {
        try {
            return new Success<>(callable.call());
        } catch (Exception e) {
            return new Failure<>(e);
        }
    }

    /**
     * Encapsulates the exception so that some higher abstraction level could manage it
     */
    public static <T> Try<T> failure(Exception e) {
        return new Failure<>(e);
    }

    public static <T> Try<Stream<T>> collapse(Stream<Try<T>> stream) {
        return newOne(() -> stream.map(Try::get));
    }

    public abstract T get();

    public abstract boolean isSuccess();

    public abstract boolean isFailure();

    public abstract <R> Try<R> map(Function<T, R> function);

    private static final class Success<T> extends Try<T> {
        private final T res;

        private Success(T res) {
            this.res = res;
        }

        @Override
        public T get() {
            return res;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public boolean isFailure() {
            return false;
        }

        @Override
        public <R> Try<R> map(Function<T, R> function) {
            return Try.newOne(() -> function.apply(res));
        }
    }

    private static final class Failure<T> extends Try<T> {
        private final Exception e;

        private Failure(Exception e) {
            this.e = e;
        }

        @Override
        public T get() {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }

            throw new RuntimeException(e.getMessage(), e);
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public boolean isFailure() {
            return true;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <R> Try<R> map(Function<T, R> function) {
            return (Try<R>) this;
        }
    }

}
