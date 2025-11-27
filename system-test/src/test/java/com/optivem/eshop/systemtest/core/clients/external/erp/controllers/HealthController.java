package com.optivem.eshop.systemtest.core.clients.external.erp.controllers;

import com.optivem.eshop.systemtest.core.clients.commons.TestHttpClient;
import com.optivem.eshop.systemtest.core.clients.commons.TestHttpUtils;
import com.optivem.eshop.systemtest.core.commons.results.Result;

public class HealthController {

    private static final String ENDPOINT = "/health";

    private final TestHttpClient httpClient;

    public HealthController(TestHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Result<Void> checkHealth() {
        var httpResponse = httpClient.get(ENDPOINT);
        return TestHttpUtils.getOkResultOrFailure(httpResponse);
    }
}

