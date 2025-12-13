package com.optivem.eshop.systemtest.core.erp.driver;

import com.optivem.eshop.systemtest.core.erp.driver.dtos.requests.CreateProductRequest;
import com.optivem.lang.Closer;
import com.optivem.lang.Error;
import com.optivem.http.HttpGateway;
import com.optivem.eshop.systemtest.core.erp.driver.client.ErpClient;
import com.optivem.lang.Result;

import java.net.http.HttpClient;

public class ErpDriver implements AutoCloseable {

    private final HttpClient httpClient;
    private final ErpClient erpClient;

    public ErpDriver(String baseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        var httpGateway = new HttpGateway(httpClient, baseUrl);
        this.erpClient = new ErpClient(httpGateway);
    }

    public Result<Void, Error> goToErp() {
        return erpClient.health().checkHealth();
    }

    public Result<Void, Error> createProduct(CreateProductRequest request) {
        return erpClient.products().createProduct(request);
    }

    @Override
    public void close() {
        Closer.close(httpClient);
    }
}

