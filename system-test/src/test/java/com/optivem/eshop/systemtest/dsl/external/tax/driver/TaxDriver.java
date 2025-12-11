package com.optivem.eshop.systemtest.dsl.external.tax.driver;

import com.optivem.lang.Closer;
import com.optivem.http.HttpGateway;
import com.optivem.eshop.systemtest.dsl.external.tax.driver.client.TaxApiClient;
import com.optivem.results.Result;

import java.net.http.HttpClient;

public class TaxDriver implements AutoCloseable {

    private final HttpClient httpClient;
    private final TaxApiClient taxApiClient;

    public TaxDriver(String baseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        var testHttpClient = new HttpGateway(httpClient, baseUrl);
        this.taxApiClient = new TaxApiClient(testHttpClient);
    }

    public Result<Void> goToTax() {
        return taxApiClient.health().checkHealth();
    }

    @Override
    public void close() {
        Closer.close(httpClient);
    }
}

