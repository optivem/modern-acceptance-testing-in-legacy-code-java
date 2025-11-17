package com.optivem.eshop.systemtest.core.clients.commons;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHttpClient {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    private final HttpClient httpClient;
    private final String baseUrl;

    public TestHttpClient(HttpClient httpClient, String baseUrl) {
        this.httpClient = httpClient;
        this.baseUrl = baseUrl;
    }

    public HttpResponse<String> get(String path) {
        var uri = getUri(path);
        var request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        return sendRequest(request);
    }

    public HttpResponse<String> post(String path, Object requestBody) {
        var uri = getUri(path);
        var jsonBody = serializeRequest(requestBody);

        var request = HttpRequest.newBuilder()
                .uri(uri)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        return sendRequest(request);
    }

    public HttpResponse<String> post(String path) {
        var uri = getUri(path);

        var request = HttpRequest.newBuilder()
                .uri(uri)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        return sendRequest(request);
    }

    public void assertOk(HttpResponse<String> httpResponse) {
        assertStatus(httpResponse, HttpStatus.OK);
    }

    public void assertCreated(HttpResponse<String> httpResponse) {
        assertStatus(httpResponse, HttpStatus.CREATED);
    }

    public void assertNoContent(HttpResponse<String> httpResponse) {
        assertStatus(httpResponse, HttpStatus.NO_CONTENT);
    }

    public void assertUnprocessableEntity(HttpResponse<String> httpResponse) {
        assertStatus(httpResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private void assertStatus(HttpResponse<String> httpResponse, HttpStatus expectedStatus) {
        assertEquals(expectedStatus.value(), httpResponse.statusCode(),
            "Expected status " + expectedStatus.value() + " but got " + httpResponse.statusCode() +
            ". Response body: " + httpResponse.body());
    }

    private URI getUri(String path) {
        try {
            return new URI(baseUrl + path);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create URI for path: " + path, ex);
        }
    }

    public <T> T readBody(HttpResponse<String> httpResponse, Class<T> responseType) {
        try {
            var responseBody = httpResponse.body();
            return objectMapper.readValue(responseBody, responseType);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to deserialize response to " + responseType.getSimpleName(), ex);
        }
    }

    private String serializeRequest(Object request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to serialize request object", ex);
        }
    }

    private HttpResponse<String> sendRequest(HttpRequest httpRequest) {
        try {
            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            throw new RuntimeException("Failed to send HTTP request", ex);
        }
    }
}
