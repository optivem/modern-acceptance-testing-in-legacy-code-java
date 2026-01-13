package com.optivem.eshop.systemtest.core.tax.client;

import com.optivem.eshop.systemtest.core.tax.client.dtos.ExtCountryDetailsResponse;
import com.optivem.eshop.systemtest.core.tax.client.dtos.error.ExtTaxErrorResponse;
import com.optivem.commons.http.JsonHttpClient;
import com.optivem.commons.util.Closer;
import com.optivem.commons.util.Result;

import java.net.http.HttpClient;

public abstract class BaseTaxClient implements AutoCloseable {

    protected final JsonHttpClient<ExtTaxErrorResponse> httpClient;
    private final HttpClient rawHttpClient;

    protected BaseTaxClient(String baseUrl) {
        this.rawHttpClient = HttpClient.newHttpClient();
        // TODO: VJ: Actually, this is not the response, it's instead some Error
        this.httpClient = new JsonHttpClient<>(rawHttpClient, baseUrl, ExtTaxErrorResponse.class);
    }

    @Override
    public void close() {
        Closer.close(rawHttpClient);
    }

    public Result<Void, ExtTaxErrorResponse> checkHealth() {
        return httpClient.get("/health");
    }

    public Result<ExtCountryDetailsResponse, ExtTaxErrorResponse> getCountry(String country) {
        return httpClient.get("/api/countries/" + country, ExtCountryDetailsResponse.class);
    }
}

