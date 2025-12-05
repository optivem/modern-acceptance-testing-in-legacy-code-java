package com.optivem.eshop.systemtest.core.drivers.external.tax.api.client;

import com.optivem.eshop.systemtest.core.drivers.commons.clients.HttpGateway;
import com.optivem.eshop.systemtest.core.drivers.external.tax.api.client.controllers.HealthController;

public class TaxApiClient {

    private final HealthController healthController;

    public TaxApiClient(HttpGateway httpGateway) {
        this.healthController = new HealthController(httpGateway);
    }

    public HealthController health() {
        return healthController;
    }
}

