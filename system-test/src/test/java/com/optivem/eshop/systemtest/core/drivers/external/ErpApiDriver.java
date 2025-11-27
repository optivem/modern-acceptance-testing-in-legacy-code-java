package com.optivem.eshop.systemtest.core.drivers.external;

import com.optivem.eshop.systemtest.core.clients.external.erp.ErpApiClient;
import com.optivem.eshop.systemtest.core.commons.results.Result;

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
        // TODO: VJ: Assert successful creation
    }

    @Override
    public void close() {
        erpApiClient.close();
    }
}

