package com.optivem.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optivem.lang.Error;
import com.optivem.lang.Result;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class JsonHttpClient {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final HttpClient httpClient;
    private final String baseUrl;

    public JsonHttpClient(HttpClient httpClient, String baseUrl) {
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

    // Result-based methods for fluent API
    
    public <T> Result<T, ProblemDetailResponse> get(String path, Class<T> responseType) {
        var httpResponse = get(path);
        return getResultOrFailure(httpResponse, responseType);
    }

    public <T> Result<T, ProblemDetailResponse> post(String path, Object requestBody, Class<T> responseType) {
        var httpResponse = post(path, requestBody);
        return getResultOrFailure(httpResponse, responseType);
    }

    public Result<Void, ProblemDetailResponse> post(String path, Class<Void> responseType) {
        var httpResponse = post(path);
        return getResultOrFailure(httpResponse, responseType);
    }

    // Public utility for error conversion
    
    public static Error convertProblemDetailToError(ProblemDetailResponse problemDetail) {
        var message = problemDetail.getDetail() != null ? problemDetail.getDetail() : "Request failed";

        if (problemDetail.getErrors() != null && !problemDetail.getErrors().isEmpty()) {
            var fieldErrors = problemDetail.getErrors().stream()
                    .map(e -> new Error.FieldError(e.getField(), e.getMessage(), e.getCode()))
                    .toList();
            return Error.of(message, fieldErrors);
        }

        return Error.of(message);
    }

    // Private helper methods

    private URI getUri(String path) {
        try {
            return new URI(baseUrl + path);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create URI for path: " + path, ex);
        }
    }

    private HttpResponse<String> sendRequest(HttpRequest httpRequest) {
        try {
            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            throw new RuntimeException("Failed to send HTTP request", ex);
        }
    }

    private String serializeRequest(Object request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to serialize request object", ex);
        }
    }

    private <T> T readResponse(HttpResponse<String> httpResponse, Class<T> responseType) {
        try {
            var responseBody = httpResponse.body();
            return objectMapper.readValue(responseBody, responseType);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to deserialize response to " + responseType.getSimpleName(), ex);
        }
    }

    private boolean isSuccessStatusCode(HttpResponse<String> httpResponse) {
        int statusCode = httpResponse.statusCode();
        return statusCode >= 200 && statusCode < 300;
    }

    private <T> Result<T, ProblemDetailResponse> getResultOrFailure(HttpResponse<String> httpResponse, Class<T> responseType) {
        if (!isSuccessStatusCode(httpResponse)) {
            var problemDetail = readResponse(httpResponse, ProblemDetailResponse.class);
            return Result.failure(problemDetail);
        }

        if (responseType == Void.class || httpResponse.statusCode() == 204) {
            return Result.success(null);
        }

        var response = readResponse(httpResponse, responseType);
        return Result.success(response);
    }
}
