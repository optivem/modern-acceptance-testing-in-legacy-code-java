package com.optivem.eshop.systemtest.driver.adapter.shop.api.client.controllers;

import com.optivem.eshop.systemtest.driver.adapter.shop.api.client.dtos.errors.ProblemDetailResponse;
import com.optivem.eshop.systemtest.driver.adapter.shared.http.JsonHttpClient;
import com.optivem.common.Result;

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



