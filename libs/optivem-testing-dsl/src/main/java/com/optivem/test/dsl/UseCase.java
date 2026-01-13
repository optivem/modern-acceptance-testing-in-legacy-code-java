package com.optivem.test.dsl;

public interface UseCase<TResult> {
    TResult execute();
}