package com.optivem.testing.dsl;

public interface Command<T> {
    T execute();
}