package com.optivem.eshop.systemtest.core.tax.client;

import com.optivem.eshop.systemtest.core.tax.client.dtos.ExtCountryDetailsResponse;
import com.optivem.eshop.systemtest.core.tax.client.dtos.error.ExtTaxErrorResponse;
import com.optivem.commons.http.JsonHttpClient;
import com.optivem.commons.util.Closer;
import com.optivem.commons.util.Result;

public abstract class BaseTaxClient implements AutoCloseable {

    protected final JsonHttpClient<ExtTaxErrorResponse> httpClient;

    protected BaseTaxClient(String baseUrl) {
        this.httpClient = new JsonHttpClient<>(baseUrl, ExtTaxErrorResponse.class);
    }

    @Override
    public void close() {
        Closer.close(httpClient);
    }

    public Result<Void, ExtTaxErrorResponse> checkHealth() {
        return httpClient.get("/health");
    }

    public Result<ExtCountryDetailsResponse, ExtTaxErrorResponse> getCountry(String country) {
        return httpClient.get("/api/countries/" + country, ExtCountryDetailsResponse.class);
    }
}

