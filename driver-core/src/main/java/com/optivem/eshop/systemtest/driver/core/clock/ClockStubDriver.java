package com.optivem.eshop.systemtest.driver.core.clock.driver;

import com.optivem.eshop.systemtest.driver.api.clock.ClockDriver;

import com.optivem.eshop.systemtest.driver.core.clock.client.ClockStubClient;
import com.optivem.eshop.systemtest.driver.core.clock.client.dtos.ExtGetTimeResponse;
import com.optivem.eshop.systemtest.driver.api.clock.dtos.GetTimeResponse;
import com.optivem.eshop.systemtest.driver.api.clock.dtos.ReturnsTimeRequest;
import com.optivem.eshop.systemtest.driver.api.clock.dtos.error.ClockErrorResponse;
import com.optivem.common.Closer;
import com.optivem.common.Converter;
import com.optivem.common.Result;

public class ClockStubDriver implements ClockDriver {
    private final ClockStubClient client;

    public ClockStubDriver(String baseUrl) {
        this.client = new ClockStubClient(baseUrl);
    }

    @Override
    public void close() {
        Closer.close(client);
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
        var time = Converter.toInstant(request.getTime());

        var extResponse = ExtGetTimeResponse.builder()
                .time(time)
                .build();

        return client.configureGetTime(extResponse)
                .mapError(ext -> ClockErrorResponse.builder().message(ext.getMessage()).build());
    }
}

