package com.optivem.eshop.systemtest.core.tax.client.controllers;

import com.optivem.eshop.systemtest.core.tax.commons.TaxError;
import com.optivem.eshop.systemtest.core.tax.client.commons.TaxErrorConverter;
import com.optivem.eshop.systemtest.core.tax.client.commons.TaxHttpClient;
import com.optivem.lang.Result;

public class HealthController {

    private static final String ENDPOINT = "/health";

    private final TaxHttpClient httpClient;

    public HealthController(TaxHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Result<Void, TaxError> checkHealth() {
        return httpClient.get(ENDPOINT)
                .mapError(TaxErrorConverter::from);
    }
}

