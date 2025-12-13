package com.optivem.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optivem.lang.Error;
import com.optivem.lang.Result;
import com.optivem.lang.Results;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class HttpUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readResponse(HttpResponse<String> httpResponse, Class<T> responseType) {
        try {
            var responseBody = httpResponse.body();
            return objectMapper.readValue(responseBody, responseType);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to deserialize response to " + responseType.getSimpleName(), ex);
        }
    }

    public static String serializeRequest(Object request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to serialize request object", ex);
        }
    }

    public static <T> Result<T, Error> getOkResultOrFailure(HttpResponse<String> httpResponse, Class<T> responseType) {
        return getResultOrFailure(httpResponse, responseType, HttpStatus.OK);
    }

    public static Result<Void, Error> getOkResultOrFailure(HttpResponse<String> httpResponse) {
        return getResultOrFailure(httpResponse, HttpStatus.OK);
    }

    public static <T> Result<T, Error> getCreatedResultOrFailure(HttpResponse<String> httpResponse, Class<T> responseType) {
        return getResultOrFailure(httpResponse, responseType, HttpStatus.CREATED);
    }

    public static Result<Void, Error> getCreatedResultOrFailure(HttpResponse<String> httpResponse) {
        return getResultOrFailure(httpResponse, HttpStatus.CREATED);
    }

    public static Result<Void, Error> getNoContentResultOrFailure(HttpResponse<String> httpResponse) {
        return getResultOrFailure(httpResponse, HttpStatus.NO_CONTENT);
    }

    public static URI getUri(String baseUrl, String path) {
        try {
            return new URI(baseUrl + path);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create URI for path: " + path, ex);
        }
    }

    private static boolean hasStatusCode(HttpResponse<String> httpResponse, HttpStatus statusCode) {
        return httpResponse.statusCode() == statusCode.value();
    }

    private static <T> Result<T, Error> getResultOrFailure(HttpResponse<String> httpResponse, Class<T> responseType, HttpStatus successStatus) {
        var isSuccess = HttpUtils.hasStatusCode(httpResponse, successStatus);

        if(!isSuccess) {
            var error = getError(httpResponse);
            return Results.failure(error);
        }

        var response = HttpUtils.readResponse(httpResponse, responseType);
        return Results.success(response);
    }

    private static Result<Void, Error> getResultOrFailure(HttpResponse<String> httpResponse, HttpStatus successStatus) {
        var isSuccess = HttpUtils.hasStatusCode(httpResponse, successStatus);

        if(!isSuccess) {
            var error = getError(httpResponse);
            return Results.failure(error);
        }

        return Results.success();
    }

    private static Error getError(HttpResponse<String> httpResponse) {
        var problemDetail = readResponse(httpResponse, ProblemDetailResponse.class);

        var message = problemDetail.getDetail() != null ? problemDetail.getDetail() : "Request failed";

        if(problemDetail.getErrors() != null && !problemDetail.getErrors().isEmpty()) {
            var fieldErrors = problemDetail.getErrors().stream()
                    .map(e -> new Error.FieldError(e.getField(), e.getMessage(), e.getCode()))
                    .collect(Collectors.toList());
            return Error.of(message, fieldErrors);
        }

        return Error.of(message);
    }
}

