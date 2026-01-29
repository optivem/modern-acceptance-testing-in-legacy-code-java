package com.optivem.eshop.systemtest.core.clock.client;

import com.optivem.commons.http.JsonHttpClient;
import com.optivem.commons.util.Closer;
import com.optivem.commons.util.Result;
import com.optivem.commons.wiremock.JsonWireMockClient;
import com.optivem.eshop.systemtest.core.clock.client.dtos.ExtGetTimeResponse;
import com.optivem.eshop.systemtest.core.clock.client.dtos.error.ExtClockErrorResponse;

public class ClockStubClient implements AutoCloseable {

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
        return httpClient.get("/health");
    }

    public Result<ExtGetTimeResponse, ExtClockErrorResponse> getTime() {
        return httpClient.get("/api/time", ExtGetTimeResponse.class);
    }

    public Result<Void, ExtClockErrorResponse> configureGetTime(ExtGetTimeResponse response) {
        return wireMockClient.stubGet("/clock/api/time", 200, response)
                .mapError(ExtClockErrorResponse::new);
    }

}
