package com.optivem.eshop.systemtest.core.erp.driver.client.controllers;

import com.optivem.http.HttpGateway;
import com.optivem.http.HttpUtils;
import com.optivem.lang.Error;
import com.optivem.lang.Result;

public class HealthController {

    private static final String ENDPOINT = "/health";

    private final HttpGateway httpClient;

    public HealthController(HttpGateway httpClient) {
        this.httpClient = httpClient;
    }

    public Result<Void, Error> checkHealth() {
        var httpResponse = httpClient.get(ENDPOINT);
        return HttpUtils.getOkResultOrFailure(httpResponse);
    }
}

