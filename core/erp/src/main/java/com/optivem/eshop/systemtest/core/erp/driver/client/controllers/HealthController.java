package com.optivem.eshop.systemtest.core.erp.driver.client.controllers;

import com.optivem.eshop.systemtest.core.commons.error.Error;
import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailConverter;
import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailResponse;
import com.optivem.http.JsonHttpClient;
import com.optivem.lang.Result;

/**
 * Health controller for checking ERP system health.
 * Shared between real and stub implementations.
 */
public class HealthController {

    private static final String ENDPOINT = "/health";

    private final JsonHttpClient<ProblemDetailResponse> httpClient;

    public HealthController(JsonHttpClient<ProblemDetailResponse> httpClient) {
        this.httpClient = httpClient;
    }

    public Result<Void, Error> checkHealth() {
        return httpClient.get(ENDPOINT)
                .mapFailure(ProblemDetailConverter::toError);
    }
}

