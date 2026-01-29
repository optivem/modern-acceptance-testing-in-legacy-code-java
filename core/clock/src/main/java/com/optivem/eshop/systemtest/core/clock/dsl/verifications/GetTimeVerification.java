package com.optivem.eshop.systemtest.core.clock.dsl.verifications;

import com.optivem.eshop.systemtest.core.clock.driver.dtos.GetTimeResponse;
import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.commons.dsl.UseCaseContext;
import com.optivem.commons.util.Converter;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class GetTimeVerification extends ResponseVerification<GetTimeResponse> {

    public GetTimeVerification(GetTimeResponse response, UseCaseContext context) {
        super(response, context);
    }

    public GetTimeVerification timeIsNotNull() {
        assertThat(response.getTime())
                .withFailMessage("Expected time to be not null")
                .isNotNull();
        return this;
    }

    public GetTimeVerification time(Instant expectedTime) {
        assertThat(response.getTime())
                .withFailMessage("Expected time %s to be equal to %s", response.getTime(), expectedTime)
                .isEqualTo(expectedTime);
        return this;
    }

    public GetTimeVerification time(String expectedTime) {
        return time(Converter.toInstant(expectedTime));
    }


    public GetTimeVerification timeIsAfter(Instant time) {
        assertThat(response.getTime())
                .withFailMessage("Expected time %s to be after %s", response.getTime(), time)
                .isAfter(time);
        return this;
    }

    public GetTimeVerification timeIsBefore(Instant time) {
        assertThat(response.getTime())
                .withFailMessage("Expected time %s to be before %s", response.getTime(), time)
                .isBefore(time);
        return this;
    }

    public GetTimeVerification timeIsBetween(Instant start, Instant end) {
        assertThat(response.getTime())
                .withFailMessage("Expected time %s to be between %s and %s", response.getTime(), start, end)
                .isBetween(start, end);
        return this;
    }
}

