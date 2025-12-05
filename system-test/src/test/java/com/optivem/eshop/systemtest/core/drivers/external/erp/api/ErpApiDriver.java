package com.optivem.eshop.systemtest.core.drivers.external.erp.api;

import com.optivem.io.Closer;
import com.optivem.http.HttpGateway;
import com.optivem.eshop.systemtest.core.drivers.external.erp.api.client.ErpApiClient;
import com.optivem.results.Result;

import java.net.http.HttpClient;

public class ErpApiDriver implements AutoCloseable {

    private final HttpClient httpClient;
    private final ErpApiClient erpApiClient;

    public ErpApiDriver(String baseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        var testHttpClient = new HttpGateway(httpClient, baseUrl);
        this.erpApiClient = new ErpApiClient(testHttpClient);
    }

    public Result<Void> goToErp() {
        return erpApiClient.health().checkHealth();
    }

    public Result<Void> createProduct(String sku, String unitPrice) {
        return erpApiClient.products().createProduct(sku, unitPrice);
    }

    @Override
    public void close() {
        Closer.close(httpClient);
    }
}

