package com.optivem.eshop.systemtest.core.erp.driver.client;

import com.optivem.eshop.systemtest.core.commons.error.Error;
import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailResponse;
import com.optivem.eshop.systemtest.core.erp.driver.client.controllers.HealthController;
import com.optivem.http.JsonHttpClient;
import com.optivem.lang.Result;

/**
 * Base ERP client with common endpoints shared between real and stub implementations.
 */
public abstract class BaseErpClient {

    protected final JsonHttpClient<ProblemDetailResponse> httpClient;
    private final HealthController healthController;

    protected BaseErpClient(JsonHttpClient<ProblemDetailResponse> httpClient) {
        this.httpClient = httpClient;
        this.healthController = new HealthController(httpClient);
    }

    public HealthController health() {
        return healthController;
    }
}

