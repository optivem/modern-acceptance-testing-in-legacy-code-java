package com.optivem.eshop.systemtest.core.tax.client;

import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailResponse;
import com.optivem.eshop.systemtest.core.tax.client.dtos.CountryDetailsDto;
import com.optivem.eshop.systemtest.core.tax.client.dtos.error.TaxApiErrorResponse;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.error.TaxErrorResponse;
import com.optivem.http.JsonHttpClient;
import com.optivem.lang.Closer;
import com.optivem.lang.Result;

import java.net.http.HttpClient;

public abstract class BaseTaxClient implements AutoCloseable {

    private final HttpClient rawHttpClient;
    protected final JsonHttpClient<TaxApiErrorResponse> httpClient;

    protected BaseTaxClient(String baseUrl) {
        this.rawHttpClient = HttpClient.newHttpClient();
        // TODO: VJ: Actually, this is not the response, it's instead some Error
        this.httpClient = new JsonHttpClient<>(rawHttpClient, baseUrl, TaxApiErrorResponse.class);
    }

    @Override
    public void close() {
        Closer.close(rawHttpClient);
    }

    public Result<Void, TaxApiErrorResponse> checkHealth() {
        return httpClient.get("/health");
    }

    public Result<CountryDetailsDto, TaxApiErrorResponse> getCountry(String country) {
        return httpClient.get("/api/countries/" + country, CountryDetailsDto.class);
    }
}

