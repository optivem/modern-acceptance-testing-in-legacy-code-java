package com.optivem.eshop.systemtest.core.tax.client.commons;

import com.optivem.http.JsonHttpClient;

import java.net.http.HttpClient;

public class TaxHttpClient extends JsonHttpClient<TaxApiErrorResponse> {
    public TaxHttpClient(HttpClient httpClient, String baseUrl) {
        super(httpClient, baseUrl, TaxApiErrorResponse.class);
    }
}
