package com.optivem.eshop.systemtest.core.drivers.external.tax.api.client.controllers;

import com.optivem.eshop.systemtest.core.drivers.commons.clients.HttpGateway;
import com.optivem.eshop.systemtest.core.drivers.commons.clients.TestHttpUtils;
import com.optivem.eshop.systemtest.core.drivers.commons.Result;

public class HealthController {

    private static final String ENDPOINT = "/health";

    private final HttpGateway httpClient;

    public HealthController(HttpGateway httpClient) {
        this.httpClient = httpClient;
    }

    public Result<Void> checkHealth() {
        var httpResponse = httpClient.get(ENDPOINT);
        return TestHttpUtils.getOkResultOrFailure(httpResponse);
    }
}

