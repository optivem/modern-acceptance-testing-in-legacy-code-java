package com.optivem.eshop.systemtest.core.clients.system.api;

import com.optivem.eshop.systemtest.core.clients.commons.TestHttpClient;
import com.optivem.eshop.systemtest.core.clients.system.api.controllers.EchoController;
import com.optivem.eshop.systemtest.core.clients.system.api.controllers.OrderController;
import com.optivem.eshop.systemtest.core.clients.system.api.dtos.ProblemDetailResponse;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.Map;

public class ShopApiClient implements AutoCloseable {

    private final HttpClient httpClient;
    private final TestHttpClient testHttpClient;
    private final EchoController echoController;
    private final OrderController orderController;

    public ShopApiClient(String baseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        this.testHttpClient = new TestHttpClient(httpClient, baseUrl);
        this.echoController = new EchoController(testHttpClient);
        this.orderController = new OrderController(testHttpClient);
    }

    public EchoController echo() {
        return echoController;
    }

    public OrderController orders() {
        return orderController;
    }

    public String getErrorMessage(HttpResponse<String> httpResponse) {
        var responseBody = httpResponse.body();

        // Try to parse as ProblemDetail (RFC 7807 format)
        try {
            var problemDetail = testHttpClient.readBody(httpResponse, ProblemDetailResponse.class);

            // If there are field-level validation errors, extract the first one
            if (problemDetail.getErrors() != null && !problemDetail.getErrors().isEmpty()) {
                Map<String, Object> firstError = problemDetail.getErrors().get(0);
                Object message = firstError.get("message");
                if (message != null) {
                    return message.toString();
                }
            }

            // Otherwise return the detail field
            if (problemDetail.getDetail() != null) {
                return problemDetail.getDetail();
            }

            // Fallback to title if detail is null
            if (problemDetail.getTitle() != null) {
                return problemDetail.getTitle();
            }
        } catch (Exception e) {
            // Not a ProblemDetail format
        }

        // Fallback: return the raw response body
        return responseBody;
    }

    @Override
    public void close() {
        httpClient.close();
    }
}

