package com.optivem.eshop.systemtest.core.tax.driver.client.controllers;

import com.optivem.eshop.systemtest.core.tax.commons.TaxError;
import com.optivem.eshop.systemtest.core.tax.driver.client.commons.TaxErrorConverter;
import com.optivem.eshop.systemtest.core.tax.driver.client.commons.TaxHttpClient;
import com.optivem.http.JsonHttpClient;
import com.optivem.lang.Result;

public class HealthController {

    private static final String ENDPOINT = "/health";

    private final TaxHttpClient httpClient;

    public HealthController(TaxHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Result<Void, TaxError> checkHealth() {
        return httpClient.get(ENDPOINT)
                .mapFailure(TaxErrorConverter::from);
    }
}

