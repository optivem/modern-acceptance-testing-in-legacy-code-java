package com.optivem.eshop.systemtest.core.tax.driver;

import com.optivem.lang.Closer;
import com.optivem.http.HttpGateway;
import com.optivem.eshop.systemtest.core.tax.driver.client.TaxClient;
import com.optivem.lang.Result;

import java.net.http.HttpClient;

public class TaxDriver implements AutoCloseable {

    private final HttpClient httpClient;
    private final TaxClient taxClient;

    public TaxDriver(String baseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        var testHttpClient = new HttpGateway(httpClient, baseUrl);
        this.taxClient = new TaxClient(testHttpClient);
    }

    @Override
    public void close() {
        Closer.close(httpClient);
    }

    public Result<Void> goToTax() {
        return taxClient.health().checkHealth();
    }
}

