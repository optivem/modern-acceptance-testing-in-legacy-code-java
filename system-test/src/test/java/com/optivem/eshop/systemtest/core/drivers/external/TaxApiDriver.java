package com.optivem.eshop.systemtest.core.drivers.external;

import com.optivem.eshop.systemtest.core.clients.external.tax.TaxApiClient;
import com.optivem.eshop.systemtest.core.commons.results.Result;

public class TaxApiDriver implements AutoCloseable {

    private final TaxApiClient taxApiClient;

    public TaxApiDriver(String baseUrl) {
        this.taxApiClient = new TaxApiClient(baseUrl);
    }

    public Result<Void> goToTax() {
        return taxApiClient.health().checkHealth();
    }

    @Override
    public void close() {
        if (taxApiClient != null) {
            taxApiClient.close();
        }
    }
}

