package com.optivem.eshop.systemtest.core.erp.client.base.controllers;

import com.optivem.eshop.systemtest.core.commons.error.Error;
import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailConverter;
import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailResponse;
import com.optivem.eshop.systemtest.core.erp.client.base.controllers.base.BaseController;
import com.optivem.http.JsonHttpClient;
import com.optivem.lang.Result;

/**
 * Health controller for checking ERP system health.
 * Shared between real and stub implementations.
 */
public class HealthController extends BaseController {

    private static final String ENDPOINT = "/health";

    public HealthController(JsonHttpClient<ProblemDetailResponse> httpClient) {
        super(httpClient);
    }

    public Result<Void, Error> checkHealth() {
        return httpClient.get(ENDPOINT)
                .mapError(ProblemDetailConverter::toError);
    }
}

