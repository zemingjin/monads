package com.function.monad;

@FunctionalInterface
public interface SupplierWithException<T, E extends Exception> {
    T get() throws E;
}
