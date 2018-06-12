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

    public abstract <U> Try<U> chain(Function<T, Try<U>> f);
    public abstract Try<T> recover(Function<Exception, Try<T>> f);
    public abstract <U> U orElse(U uTry);

    public static class Success<T> extends Try<T> {
        final T value;

        private Success(T value) {
            this.value = value;
        }

        @Override
        public <U> Try<U> chain(Function<T, Try<U>> f) {
            return f.apply(value);
        }

        @Override
        @SuppressWarnings("unchecked")
        public Try<T> recover(Function<Exception, Try<T>> f) {
            return this;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <U> U orElse(U uTry) {
            return (U)value;
        }
    }

    public static class Failure<T> extends Try<T> {
        final Exception e;

        private Failure(Exception e) {
            this.e = e;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <U> Try<U> chain(Function<T, Try<U>> f) {
            return (Try<U>)this;
        }

        @Override
        public Try<T> recover(Function<Exception, Try<T>> f) {
            return f.apply(e);
        }

        @Override
        public <U> U orElse(U uTry) {
            return uTry;
        }
    }
}



