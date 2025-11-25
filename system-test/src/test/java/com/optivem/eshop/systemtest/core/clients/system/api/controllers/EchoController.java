package com.optivem.eshop.systemtest.core.clients.system.api.controllers;

import com.optivem.eshop.systemtest.core.clients.commons.TestHttpClient;

import java.net.http.HttpResponse;

public class EchoController {

    private static final String ENDPOINT = "/echo";

    private final TestHttpClient httpClient;

    public EchoController(TestHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public HttpResponse<String> echo() {
        return httpClient.get(ENDPOINT);
    }
}

