package com.optivem.eshop.systemtest.core.tax.client;

import com.optivem.eshop.systemtest.core.tax.client.commons.TaxHttpClient;
import com.optivem.eshop.systemtest.core.tax.client.controllers.HealthController;
import com.optivem.eshop.systemtest.core.tax.client.controllers.CountryController;

public abstract class BaseTaxClient {

    protected final TaxHttpClient httpClient;
    private final HealthController healthController;
    private final CountryController countryController;

    protected BaseTaxClient(TaxHttpClient httpClient) {
        this.httpClient = httpClient;
        this.healthController = new HealthController(httpClient);
        this.countryController = new CountryController(httpClient);
    }

    public HealthController health() {
        return healthController;
    }

    public CountryController countries() {
        return countryController;
    }
}

