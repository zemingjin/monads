package com.function.monad;

import java.util.function.Function;

public abstract class Try<T> {
    public static <T> Try<T> with(SupplierWithException<T, Exception> f) {
        try {
            return new Success<>(f.get());
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return new Failure<>(ex);
        }
    }

    public abstract <U> Try<U> flatMap(Function<T, Try<U>> f);
    public abstract <U> Try<U> recover(Function<Exception, Try<U>> f);
    public abstract T orElse(T defValue);

    public static class Success<T> extends Try<T> {
        final T value;

        private Success(T value) {
            this.value = value;
        }

        @Override
        public <U> Try<U> flatMap(Function<T, Try<U>> f) {
            return f.apply(value);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <U> Try<U> recover(Function<Exception, Try<U>> f) {
            return (Try<U>)this;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T orElse(T defValue) {
            return value;
        }
    }

    public static class Failure<T> extends Try<T> {
        final Exception e;

        private Failure(Exception e) {
            this.e = e;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <U> Try<U> flatMap(Function<T, Try<U>> f) {
            return (Try<U>)this;
        }

        @Override
        public <U> Try<U> recover(Function<Exception, Try<U>> f) {
            return f.apply(e);
        }

        @Override
        public T orElse(T defValue) {
            return defValue;
        }
    }
}



