package com.optivem.commons.dsl;

public interface UseCase<TResult> {
    TResult execute();
}