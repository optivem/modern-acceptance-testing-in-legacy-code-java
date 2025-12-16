package com.optivem.eshop.systemtest.core.tax.driver;

import com.optivem.lang.Closer;
import com.optivem.eshop.systemtest.core.commons.error.Error;
import com.optivem.http.JsonHttpClient;
import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailResponse;
import com.optivem.eshop.systemtest.core.tax.driver.client.TaxClient;
import com.optivem.lang.Result;

import java.net.http.HttpClient;

public class TaxDriver implements AutoCloseable {

    private final HttpClient httpClient;
    private final TaxClient taxClient;

    public TaxDriver(String baseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        var testHttpClient = new JsonHttpClient<>(httpClient, baseUrl, ProblemDetailResponse.class);
        this.taxClient = new TaxClient(testHttpClient);
    }

    @Override
    public void close() {
        Closer.close(httpClient);
    }

    public Result<Void, Error> goToTax() {
        return taxClient.health().checkHealth();
    }
}

