package com.optivem.eshop.systemtest.core.drivers.system.shop.api.client.controllers;

import com.optivem.http.HttpGateway;
import com.optivem.http.HttpUtils;
import com.optivem.results.Result;

public class HealthController {

    private static final String ENDPOINT = "/health";

    private final HttpGateway httpClient;

    public HealthController(HttpGateway httpClient) {
        this.httpClient = httpClient;
    }

    public Result<Void> checkHealth() {
        var httpResponse = httpClient.get(ENDPOINT);
        return HttpUtils.getOkResultOrFailure(httpResponse);
    }
}

