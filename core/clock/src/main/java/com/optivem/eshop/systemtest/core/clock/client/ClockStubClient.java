package com.optivem.eshop.systemtest.core.clock.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.optivem.eshop.systemtest.core.clock.client.dtos.error.ExtClockErrorResponse;
import com.optivem.eshop.systemtest.core.clock.driver.ClockStubDriver;
import com.optivem.eshop.systemtest.core.clock.driver.dtos.GetTimeResponse;
import com.optivem.http.JsonHttpClient;
import com.optivem.lang.Closer;
import com.optivem.lang.Result;
import com.optivem.wiremock.JsonWireMockClient;

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

    public Result<GetTimeResponse, ExtClockErrorResponse> getTime() {
        return httpClient.get("/api/time", GetTimeResponse.class);
    }

    public Result<Void, ExtClockErrorResponse> configureGetTime(GetTimeResponse response) {
        return wireMockClient.configureGet("/clock/api/time", 200, response)
                .mapError(ExtClockErrorResponse::new);
    }

    public Result<Void, ExtClockErrorResponse> checkHealth() {
        return httpClient.get("/health");
    }
}
