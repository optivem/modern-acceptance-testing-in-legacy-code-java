package com.optivem.eshop.systemtest.core.clients.external.tax;

import com.optivem.eshop.systemtest.core.clients.commons.TestHttpClient;
import com.optivem.eshop.systemtest.core.clients.external.tax.controllers.HomeController;

import java.net.http.HttpClient;

public class TaxApiClient implements AutoCloseable {

    private final HttpClient client;
    private final TestHttpClient testHttpClient;
    private final HomeController homeController;

    public TaxApiClient(String baseUrl) {
        this.client = HttpClient.newHttpClient();
        this.testHttpClient = new TestHttpClient(client, baseUrl);
        this.homeController = new HomeController(testHttpClient);
    }

    public HomeController home() {
        return homeController;
    }

    @Override
    public void close() {
        client.close();
    }
}

