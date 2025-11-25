package com.optivem.eshop.systemtest.core.clients.external.tax.controllers;

import com.optivem.eshop.systemtest.core.clients.commons.TestHttpClient;

import java.net.http.HttpResponse;

public class HomeController {

    private static final String ENDPOINT = "/";

    private final TestHttpClient httpClient;

    public HomeController(TestHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public HttpResponse<String> home() {
        return httpClient.get(ENDPOINT);
    }
}

