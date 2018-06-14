package com.function.monad;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A container abstract class providing the API to handle exceptions with Monad pattern.
 */
public abstract class Try<T> {
    /**
     * Execute the given supplier function and store the result as the value of container.
     * The given exception handler would be called if exception were thrown.
     * @param supplier the given supplier function providing the value for the container.
     * @param handler the given exception handler.
     * @param <T> the class of value
     * @return Success&lt;T&gt; if the given supplier action was successful. Otherwise, a Failure&lt;T&gt;
     */
    public static <T> Try<T> with(SupplierWithException<T, Exception> supplier, Consumer<Exception> handler) {
        try {
            return new Success<>(supplier.get());
        } catch (Exception ex) {
            handler.accept(ex);
            return new Failure<>(ex);
        }
    }

    /**
     * If Success, apply the given mapping function to it, return that result, otherwise return itself.
     * @param mapper a mapping function to apply to the value for Success
     * @param <U> The type parameter to the Try returned by
     * @return the result of applying the given mapping function to the value of this Try, if Success, otherwise Failure.
     */
    public abstract <U> Try<U> then(Function<T, Try<U>> mapper);

    /**
     * For Success, simply return Success itself. Otherwise, apply the given mapping function to it and return the result.
     * @param mapper the given mapping function to recoverWith from previous Failure.
     * @param <U> The type parameter to the Try returned by
     * @return the result of applying the given mapping function to the value of this Try, if Failure, otherwise Success.
     */
    public abstract <U> Try<U> recoverWith(Function<Exception, Try<U>> mapper);

    /**
     * Return the value if Success, otherwise return the given default value.
     * @param defValue the given default value for Failure
     * @return the value if Success, otherwise, the given default value.
     */
    public abstract T orElse(T defValue);

    private static class Success<T> extends Try<T> {
        final T value;

        private Success(T value) {
            this.value = value;
        }

        @Override
        public <U> Try<U> then(Function<T, Try<U>> mapper) {
            return mapper.apply(value);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <U> Try<U> recoverWith(Function<Exception, Try<U>> mapper) {
            return (Try<U>)this;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T orElse(T defValue) {
            return value;
        }
    }

    private static class Failure<T> extends Try<T> {
        final Exception e;

        private Failure(Exception e) {
            this.e = e;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <U> Try<U> then(Function<T, Try<U>> mapper) {
            return (Try<U>)this;
        }

        @Override
        public <U> Try<U> recoverWith(Function<Exception, Try<U>> mapper) {
            return mapper.apply(e);
        }

        @Override
        public T orElse(T defValue) {
            return defValue;
        }
    }
}



