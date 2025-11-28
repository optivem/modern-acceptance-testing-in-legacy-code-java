package com.optivem.eshop.systemtest.core.drivers.external.erp.api;

import com.optivem.eshop.systemtest.core.drivers.external.erp.api.client.ErpApiClient;
import com.optivem.eshop.systemtest.core.drivers.commons.Result;

public class ErpApiDriver implements AutoCloseable {

    private final ErpApiClient erpApiClient;

    public ErpApiDriver(String baseUrl) {
        this.erpApiClient = new ErpApiClient(baseUrl);
    }

    public Result<Void> goToErp() {
        return erpApiClient.health().checkHealth();
    }

    public Result<Void> createProduct(String sku, String unitPrice) {
        return erpApiClient.products().createProduct(sku, unitPrice);
    }

    @Override
    public void close() {
        erpApiClient.close();
    }
}

