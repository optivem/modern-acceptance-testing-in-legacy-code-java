package com.optivem.commons.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.optivem.commons.util.Result;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;
import wiremock.com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

public class JsonWireMockClient {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final ExecutorService VIRTUAL_THREAD_EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();
    private final ObjectMapper objectMapper;

    private final WireMock wireMock;

    public JsonWireMockClient(String baseUrl) {
        var url = URI.create(baseUrl);
        this.wireMock = new WireMock(url.getHost(), url.getPort());
        this.objectMapper = createObjectMapper();
    }

    private static ObjectMapper createObjectMapper() {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    public <T> Result<Void, String> stubGet(String path, int statusCode, T response) {
        try {
            // Execute WireMock operations on virtual thread for non-blocking I/O
            return VIRTUAL_THREAD_EXECUTOR.submit(() -> {
                Result<Void, String> result = performStubRegistration(path, statusCode, response);
                return result;
            }).get();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to register stub", ex);
        }
    }

    private <T> Result<Void, String> performStubRegistration(String path, int statusCode, T response) {
        var responseBody = serialize(response);

        wireMock.register(WireMock.get(urlPathEqualTo(path))
                .willReturn(aResponse()
                        .withStatus(statusCode)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBody(responseBody)));

        var mappings = wireMock.allStubMappings();
        var registered = mappings.getMappings().stream()
                .anyMatch(m -> "GET".equals(m.getRequest().getMethod().getName())
                        && m.getRequest().getUrlPath().equals(path));

        if (!registered) {
            return Result.failure("Failed to register stub for GET " + path);
        }

        return Result.success();
    }

    private String serialize(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to serialize object", ex);
        }
    }
}
