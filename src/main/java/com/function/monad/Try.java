package com.function.monad;

import java.util.function.Function;

public abstract class Try<T> {
    public static <T> Try<T> with(SupplierWithException<T, Exception> f) {
        try {
            return new Success<>(f.get());
        } catch (Exception ex) {
            return new Failure<>(ex);
        }
    }

    public abstract T get();
    public abstract <U> Try<U> chain(Function<T, Try<U>> f);
    public abstract <U> Try<U> recover(Function<Exception, Try<U>> f);

    public static class Success<T> extends Try<T> {
        final T value;

        public Success(T value) {
            this.value = value;
        }

        public T get() {
            return value;
        }

        @Override
        public <U> Try<U> chain(Function<T, Try<U>> f) {
            return f.apply(value);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <U> Try<U> recover(Function<Exception, Try<U>> f) {
            return (Try<U>)this;
        }
    }

    public static class Failure<T> extends Try<T> {
        final Exception e;

        public Failure(Exception e) {
            this.e = e;
        }

        public T get() {
            throw new RuntimeException("get should not be called on Failure");
        }

        @Override
        @SuppressWarnings("unchecked")
        public <U> Try<U> chain(Function<T, Try<U>> f) {
            return (Try<U>)this;
        }

        @Override
        public <U> Try<U> recover(Function<Exception, Try<U>> f) {
            return f.apply(e);
        }
    }
}



