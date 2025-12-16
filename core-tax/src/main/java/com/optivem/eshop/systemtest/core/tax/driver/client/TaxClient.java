package com.optivem.eshop.systemtest.core.tax.driver.client;

import com.optivem.http.JsonHttpClient;
import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailResponse;
import com.optivem.eshop.systemtest.core.tax.driver.client.controllers.HealthController;

public class TaxClient {

    private final HealthController healthController;

    public TaxClient(JsonHttpClient<ProblemDetailResponse> httpGateway) {
        this.healthController = new HealthController(httpGateway);
    }

    public HealthController health() {
        return healthController;
    }
}

