package com.optivem.eshop.systemtest.core.clock.driver;

import com.optivem.eshop.systemtest.core.clock.driver.dtos.GetTimeResponse;
import com.optivem.eshop.systemtest.core.clock.driver.dtos.ReturnsTimeRequest;
import com.optivem.eshop.systemtest.core.clock.driver.dtos.error.ClockErrorResponse;
import com.optivem.commons.util.Result;

import java.time.Instant;

public class ClockRealDriver implements ClockDriver {

    @Override
    public void close() throws Exception {
        // No resources to close in the real driver
    }

    @Override
    public Result<Void, ClockErrorResponse> goToClock() {
        return Result.success();
    }

    @Override
    public Result<GetTimeResponse, ClockErrorResponse> getTime() {
        var currentTime = Instant.now();

        var response = GetTimeResponse.builder()
                .time(currentTime)
                .build();

        return Result.success(response);
    }

    @Override
    public Result<Void, ClockErrorResponse> returnsTime(ReturnsTimeRequest request) {
        // No op because real clock cannot be configured
        return Result.success();
    }

}
