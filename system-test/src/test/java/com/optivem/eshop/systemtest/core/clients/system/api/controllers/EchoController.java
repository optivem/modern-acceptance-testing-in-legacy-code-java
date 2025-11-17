package com.optivem.eshop.systemtest.core.clients.system.api.controllers;

import com.optivem.eshop.systemtest.core.clients.commons.BaseController;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class EchoController extends BaseController {

    public EchoController(HttpClient client, String baseUrl) {
        super(client, baseUrl);
    }

    public HttpResponse<String> echo() {
        return get("api/echo");
    }

    public void assertEchoSuccessful(HttpResponse<String> httpResponse) {
        assertOk(httpResponse);
    }
}

