package com.optivem.eshop.systemtest.core.clock.driver;

import com.optivem.eshop.systemtest.core.clock.driver.dtos.GetTimeResponse;
import com.optivem.eshop.systemtest.core.clock.driver.dtos.ReturnsTimeRequest;

import java.time.Instant;

public class ClockRealDriver implements ClockDriver {

    @Override
    public void close() throws Exception {
        // No resources to close in the real driver
    }

    @Override
    public ClockResult<Void> goToClock() {
        Instant.now();
        return ClockResult.success();
    }

    @Override
    public ClockResult<GetTimeResponse> getTime() {
        var currentTime = Instant.now();

        var response = GetTimeResponse.builder()
                .time(currentTime)
                .build();

        return ClockResult.success(response);
    }

    @Override
    public ClockResult<Void> returnsTime(ReturnsTimeRequest request) {
        // No op because real clock cannot be configured
        return ClockResult.success();
    }

}
