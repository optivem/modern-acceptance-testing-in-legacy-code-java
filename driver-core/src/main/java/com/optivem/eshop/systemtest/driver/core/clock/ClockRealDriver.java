package com.optivem.eshop.systemtest.driver.core.clock.driver;

import com.optivem.eshop.systemtest.driver.api.clock.ClockDriver;

import com.optivem.eshop.systemtest.driver.core.clock.client.ClockRealClient;
import com.optivem.eshop.systemtest.driver.api.clock.dtos.GetTimeResponse;
import com.optivem.eshop.systemtest.driver.api.clock.dtos.ReturnsTimeRequest;
import com.optivem.eshop.systemtest.driver.api.clock.dtos.error.ClockErrorResponse;
import com.optivem.commons.util.Result;

public class ClockRealDriver implements ClockDriver {
    private final ClockRealClient client;

    public ClockRealDriver() {
        this.client = new ClockRealClient();
    }

    @Override
    public void close() {
        // No resources to close - ClockRealClient has no HTTP or other resources
    }

    @Override
    public Result<Void, ClockErrorResponse> goToClock() {
        return client.checkHealth()
                .mapError(ext -> ClockErrorResponse.builder().message(ext.getMessage()).build());
    }

    @Override
    public Result<GetTimeResponse, ClockErrorResponse> getTime() {
        return client.getTime()
                .map(ext -> GetTimeResponse.builder().time(ext.getTime()).build())
                .mapError(ext -> ClockErrorResponse.builder().message(ext.getMessage()).build());
    }

    @Override
    public Result<Void, ClockErrorResponse> returnsTime(ReturnsTimeRequest request) {
        // No-op because real clock cannot be configured
        return Result.success();
    }
}

