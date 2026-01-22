package com.optivem.eshop.systemtest.core.clock.driver;

import com.optivem.eshop.systemtest.core.clock.client.ClockStubClient;
import com.optivem.eshop.systemtest.core.clock.client.dtos.ExtGetTimeResponse;
import com.optivem.eshop.systemtest.core.clock.driver.dtos.GetTimeResponse;
import com.optivem.eshop.systemtest.core.clock.driver.dtos.ReturnsTimeRequest;
import com.optivem.eshop.systemtest.core.clock.driver.dtos.error.ClockErrorResponse;
import com.optivem.commons.util.Closer;
import com.optivem.commons.util.Result;


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
        return client.checkHealth().mapError(ClockErrorResponse::from);
    }

    @Override
    public Result<GetTimeResponse, ClockErrorResponse> getTime() {
        return client.getTime().map(GetTimeResponse::from).mapError(ClockErrorResponse::from);
    }

    @Override
    public Result<Void, ClockErrorResponse> returnsTime(ReturnsTimeRequest request) {
        var extResponse = ExtGetTimeResponse.builder()
                .time(request.getTime())
                .build();

        return client.configureGetTime(extResponse)
                .mapError(ClockErrorResponse::from);
    }
}
