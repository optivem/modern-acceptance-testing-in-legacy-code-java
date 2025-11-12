package com.optivem.eshop.monolith.api.exception;

import com.optivem.eshop.monolith.core.exceptions.NotExistValidationException;
import com.optivem.eshop.monolith.core.exceptions.ValidationException;
import com.optivem.eshop.monolith.core.validation.TypeValidationMessageExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final Pattern CLASS_NAME_PATTERN = Pattern.compile("(com\\.optivem\\.eshop\\.monolith\\.core\\.dtos\\.[^\\[\\]\"\\s\\)]+)");

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
        var errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    @ExceptionHandler(NotExistValidationException.class)
    public ResponseEntity<ErrorResponse> handleNotExistValidationException(NotExistValidationException ex) {
        return ResponseEntity.notFound().build();
    }

    public record ErrorResponse(String message) {}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.debug("HttpMessageNotReadableException: {}", ex.getMessage());

        ResponseEntity<ErrorResponse> response = tryParseFieldError(ex.getMessage());
        if (response != null) {
            return response;
        }

        if (ex.getCause() != null) {
            log.debug("Root cause: {}", ex.getCause().getMessage());
            response = tryParseFieldError(ex.getCause().getMessage());
            if (response != null) {
                return response;
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Invalid request format"));
    }

    private ResponseEntity<ErrorResponse> tryParseFieldError(String message) {
        if (message == null) {
            return null;
        }

        // Try to extract the DTO class name from the exception message
        Class<?> dtoClass = extractDtoClass(message);
        if (dtoClass == null) {
            return null;
        }

        // Extract field validation messages from the DTO class annotations
        Map<String, String> fieldErrorPatterns = TypeValidationMessageExtractor.extractFieldMessages(dtoClass);

        String lowerMessage = message.toLowerCase();

        return fieldErrorPatterns.entrySet().stream()
                .filter(entry -> lowerMessage.contains(entry.getKey()))
                .findFirst()
                .map(entry -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(entry.getValue())))
                .orElse(null);
    }

    private Class<?> extractDtoClass(String message) {
        // Try to find the DTO class name in the exception message
        Matcher matcher = CLASS_NAME_PATTERN.matcher(message);
        if (matcher.find()) {
            String className = matcher.group(1);
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                log.debug("Could not load class: {}", className);
            }
        }
        return null;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        log.error("Unexpected error occurred", ex);

        // Log the full cause chain
        Throwable cause = ex.getCause();
        int depth = 1;
        while (cause != null && depth < 10) {
            log.error("  Caused by [{}]: {}", depth, cause.getMessage(), cause);
            cause = cause.getCause();
            depth++;
        }

        // Build detailed error message with root cause
        String rootCauseMessage = getRootCauseMessage(ex);
        String fullMessage = "Internal server error: " + ex.getMessage();
        if (rootCauseMessage != null && !rootCauseMessage.equals(ex.getMessage())) {
            fullMessage += " | Root cause: " + rootCauseMessage;
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(fullMessage));
    }

    private String getRootCauseMessage(Throwable ex) {
        Throwable cause = ex;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause.getMessage();
    }
}

