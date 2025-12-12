package com.optivem.eshop.systemtest.core.erp.driver;

import com.optivem.eshop.systemtest.core.erp.dtos.CreateProductRequest;
import com.optivem.lang.Closer;
import com.optivem.http.HttpGateway;
import com.optivem.eshop.systemtest.core.erp.driver.client.ErpApiClient;
import com.optivem.results.Result;

import java.net.http.HttpClient;

public class ErpDriver implements AutoCloseable {

    private final HttpClient httpClient;
    private final ErpApiClient erpApiClient;

    public ErpDriver(String baseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        var testHttpClient = new HttpGateway(httpClient, baseUrl);
        this.erpApiClient = new ErpApiClient(testHttpClient);
    }

    public Result<Void> goToErp() {
        return erpApiClient.health().checkHealth();
    }

    public Result<Void> createProduct(CreateProductRequest request) {
        return erpApiClient.products().createProduct(request);
    }

    @Override
    public void close() {
        Closer.close(httpClient);
    }
}

