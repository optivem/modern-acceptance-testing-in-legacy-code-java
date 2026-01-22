package com.optivem.eshop.systemtest.core.clock.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.optivem.eshop.systemtest.core.clock.client.dtos.ExtGetTimeResponse;
import com.optivem.eshop.systemtest.core.clock.client.dtos.error.ExtClockErrorResponse;
import com.optivem.eshop.systemtest.core.clock.driver.dtos.GetTimeResponse;
import com.optivem.commons.http.JsonHttpClient;
import com.optivem.commons.util.Closer;
import com.optivem.commons.util.Result;
import com.optivem.commons.wiremock.JsonWireMockClient;

import java.net.URI;
import java.net.http.HttpClient;

public class ClockStubClient implements AutoCloseable {

    private final JsonHttpClient<ExtClockErrorResponse> httpClient;
    private final HttpClient rawHttpClient;

    private final WireMock wireMock;
    private final JsonWireMockClient wireMockClient;

    public ClockStubClient(String baseUrl) {
        this.rawHttpClient = HttpClient.newHttpClient();
        this.httpClient = new JsonHttpClient<>(rawHttpClient, baseUrl, ExtClockErrorResponse.class);

        var url = URI.create(baseUrl);
        this.wireMock = new WireMock(url.getHost(), url.getPort());
        this.wireMockClient = new JsonWireMockClient(wireMock);
    }

    @Override
    public void close() {
        Closer.close(rawHttpClient);
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
