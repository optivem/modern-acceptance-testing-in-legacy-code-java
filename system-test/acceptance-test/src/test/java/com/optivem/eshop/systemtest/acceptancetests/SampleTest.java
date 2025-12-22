package com.optivem.eshop.systemtest.acceptancetests;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Sample tests to verify the acceptance-test module is properly configured.
 * These simple tests help ensure the test infrastructure is working correctly.
 */
public class SampleTest {

    @Test
    void shouldPass() {
        assertThat(1).isEqualTo(1);
    }

    @Test
    void shouldAssertTrue() {
        assertThat(true).isTrue();
    }

    @Test
    void shouldCalculateSum() {
        int sum = 2 + 2;
        assertThat(sum).isEqualTo(4);
    }

    @Test
    void shouldVerifyStringEquality() {
        String expected = "Hello";
        String actual = "Hello";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldCheckCollectionSize() {
        var list = java.util.List.of(1, 2, 3);
        assertThat(list).hasSize(3);
    }
}

