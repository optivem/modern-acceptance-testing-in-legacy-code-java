package com.optivem.eshop.systemtest.core.tax.client.commons;

import com.optivem.eshop.systemtest.core.tax.client.dtos.error.ExtTaxErrorResponse;
import com.optivem.http.JsonHttpClient;

import java.net.http.HttpClient;

public class TaxHttpClient extends JsonHttpClient<ExtTaxErrorResponse> {
    public TaxHttpClient(HttpClient httpClient, String baseUrl) {
        super(httpClient, baseUrl, ExtTaxErrorResponse.class);
    }
}
