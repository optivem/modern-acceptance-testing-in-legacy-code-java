package com.optivem.eshop.systemtest.core.clients.api.controllers;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class EchoControllerClient extends BaseControllerClient {

    public EchoControllerClient(HttpClient client, String baseUrl) {
        super(client, baseUrl);
    }

    public HttpResponse<String> echo() {
        return get("api/echo");
    }

    public void confirmEchoSuccessful(HttpResponse<String> httpResponse) {
        assertOk(httpResponse);
    }
}

