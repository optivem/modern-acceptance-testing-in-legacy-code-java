package com.optivem.eshop.systemtest.core.clock.driver;

import com.optivem.eshop.systemtest.core.clock.client.ClockStubClient;
import com.optivem.eshop.systemtest.core.clock.client.dtos.ExtGetTimeResponse;
import com.optivem.eshop.systemtest.core.clock.driver.dtos.GetTimeResponse;
import com.optivem.eshop.systemtest.core.clock.driver.dtos.ReturnsTimeRequest;
import com.optivem.eshop.systemtest.core.clock.driver.dtos.error.ClockErrorResponse;
import com.optivem.commons.util.Closer;
import com.optivem.commons.util.Converter;

import static com.optivem.eshop.systemtest.core.clock.driver.ClockResult.from;

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
    public ClockResult<Void> goToClock() {
        return from(client.checkHealth().mapError(ClockErrorResponse::from));
    }

    @Override
    public ClockResult<GetTimeResponse> getTime() {
        return from(client.getTime().map(GetTimeResponse::from).mapError(ClockErrorResponse::from));
    }

    @Override
    public ClockResult<Void> returnsTime(ReturnsTimeRequest request) {
        var time = Converter.toInstant(request.getTime());

        var extResponse = ExtGetTimeResponse.builder()
                .time(time)
                .build();

        return from(client.configureGetTime(extResponse)
                .mapError(ClockErrorResponse::from));
    }
}
