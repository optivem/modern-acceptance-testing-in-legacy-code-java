package com.optivem.eshop.systemtest.driver.core.clock.client;

import com.optivem.commons.util.Result;
import com.optivem.eshop.systemtest.driver.core.clock.client.dtos.ExtGetTimeResponse;
import com.optivem.eshop.systemtest.driver.core.clock.client.dtos.error.ExtClockErrorResponse;

import java.time.Instant;

public class ClockRealClient {

    public Result<Void, ExtClockErrorResponse> checkHealth() {
        now();
        return Result.success();
    }

    public Result<ExtGetTimeResponse, ExtClockErrorResponse> getTime() {
        var response = ExtGetTimeResponse.builder()
                .time(now())
                .build();
        return Result.success(response);
    }

    private static Instant now() {
        return Instant.now();
    }
}

