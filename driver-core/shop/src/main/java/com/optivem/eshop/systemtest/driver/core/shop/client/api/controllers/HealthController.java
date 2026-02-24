package com.optivem.eshop.systemtest.driver.core.shop.client.api.controllers;

import com.optivem.eshop.systemtest.driver.core.shop.client.api.dtos.errors.ProblemDetailResponse;
import com.optivem.commons.http.JsonHttpClient;
import com.optivem.commons.util.Result;

public class HealthController {

    private static final String ENDPOINT = "/health";

    private final JsonHttpClient<ProblemDetailResponse> httpClient;

    public HealthController(JsonHttpClient<ProblemDetailResponse> httpClient) {
        this.httpClient = httpClient;
    }

    public Result<Void, ProblemDetailResponse> checkHealth() {
        return httpClient.get(ENDPOINT);
    }
}

