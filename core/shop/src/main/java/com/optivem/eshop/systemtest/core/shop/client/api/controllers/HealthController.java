package com.optivem.eshop.systemtest.core.shop.client.api.controllers;

import com.optivem.eshop.systemtest.core.shop.client.dtos.error.ProblemDetailResponse;
import com.optivem.http.JsonHttpClient;
import com.optivem.lang.Result;

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

