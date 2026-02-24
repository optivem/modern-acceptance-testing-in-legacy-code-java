package com.optivem.eshop.systemtest.driver.core.clock.client;

import com.optivem.commons.http.HttpStatus;
import com.optivem.commons.http.JsonHttpClient;
import com.optivem.commons.util.Closer;
import com.optivem.commons.util.Result;
import com.optivem.commons.wiremock.JsonWireMockClient;
import com.optivem.eshop.systemtest.driver.core.clock.client.dtos.ExtGetTimeResponse;
import com.optivem.eshop.systemtest.driver.core.clock.client.dtos.error.ExtClockErrorResponse;

public class ClockStubClient implements AutoCloseable {

    private static final String HEALTH_ENDPOINT = "/health";
    private static final String TIME_ENDPOINT = "/api/time";
    private static final String CLOCK_TIME_ENDPOINT = "/clock/api/time";

    private final JsonHttpClient<ExtClockErrorResponse> httpClient;

    private final JsonWireMockClient wireMockClient;

    public ClockStubClient(String baseUrl) {
        this.httpClient = new JsonHttpClient<>(baseUrl, ExtClockErrorResponse.class);
        this.wireMockClient = new JsonWireMockClient(baseUrl);
    }

    @Override
    public void close() {
        Closer.close(httpClient);
    }

    public Result<Void, ExtClockErrorResponse> checkHealth() {
        return httpClient.get(HEALTH_ENDPOINT);
    }

    public Result<ExtGetTimeResponse, ExtClockErrorResponse> getTime() {
        return httpClient.get(TIME_ENDPOINT, ExtGetTimeResponse.class);
    }

    public Result<Void, ExtClockErrorResponse> configureGetTime(ExtGetTimeResponse response) {
        return wireMockClient.stubGet(CLOCK_TIME_ENDPOINT, HttpStatus.OK, response)
                .mapError(ExtClockErrorResponse::new);
    }

}
