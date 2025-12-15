package com.optivem.eshop.systemtest.core.shop.driver.api.client.controllers;

import com.optivem.http.JsonHttpClient;
import com.optivem.lang.Error;
import com.optivem.lang.Result;

public class HealthController {

    private static final String ENDPOINT = "/health";

    private final JsonHttpClient httpClient;

    public HealthController(JsonHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Result<Void, Error> checkHealth() {
        return httpClient.get(ENDPOINT, Void.class)
                .mapFailure(JsonHttpClient::convertProblemDetailToError);
    }
}

