package com.optivem.eshop.systemtest.core.clients.external.erp.controllers;

import com.optivem.eshop.systemtest.core.clients.commons.TestHttpClient;
import com.optivem.eshop.systemtest.core.clients.commons.TestHttpUtils;
import com.optivem.eshop.systemtest.core.commons.results.Result;

public class HomeController {

    private static final String ENDPOINT = "/";

    private final TestHttpClient httpClient;

    public HomeController(TestHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Result<Void> home() {
        var httpResponse = httpClient.get(ENDPOINT);
        return TestHttpUtils.getOkResultOrFailure(httpResponse);
    }
}

