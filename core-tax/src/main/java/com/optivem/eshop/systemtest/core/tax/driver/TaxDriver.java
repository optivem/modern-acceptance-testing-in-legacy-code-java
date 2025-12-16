package com.optivem.eshop.systemtest.core.tax.driver;

import com.optivem.eshop.systemtest.core.tax.commons.TaxError;
import com.optivem.eshop.systemtest.core.tax.driver.client.commons.TaxHttpClient;
import com.optivem.lang.Closer;
import com.optivem.eshop.systemtest.core.tax.driver.client.TaxClient;
import com.optivem.lang.Result;

import java.net.http.HttpClient;

public class TaxDriver implements AutoCloseable {

    private final HttpClient httpClient;
    private final TaxClient taxClient;

    public TaxDriver(String baseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        var taxHttpClient = new TaxHttpClient(httpClient, baseUrl);
        this.taxClient = new TaxClient(taxHttpClient);
    }

    @Override
    public void close() {
        Closer.close(httpClient);
    }

    public Result<Void, TaxError> goToTax() {
        return taxClient.health().checkHealth();
    }
}

