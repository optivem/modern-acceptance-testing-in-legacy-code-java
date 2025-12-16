package com.optivem.eshop.systemtest.core.tax.driver.client;

import com.optivem.eshop.systemtest.core.tax.driver.client.commons.TaxHttpClient;
import com.optivem.eshop.systemtest.core.tax.driver.client.controllers.HealthController;

public class TaxClient {

    private final HealthController healthController;

    public TaxClient(TaxHttpClient taxHttpClient) {
        this.healthController = new HealthController(taxHttpClient);
    }

    public HealthController health() {
        return healthController;
    }
}

