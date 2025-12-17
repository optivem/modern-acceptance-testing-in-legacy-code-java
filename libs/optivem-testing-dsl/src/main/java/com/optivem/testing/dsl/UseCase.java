package com.optivem.testing.dsl;

public interface UseCase<TResult> {
    TResult execute();
}