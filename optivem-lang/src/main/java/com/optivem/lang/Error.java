package com.optivem.lang;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
public class Error {
    private final String message;
    private final List<FieldError> fields;

    @Getter
    public static class FieldError {
        private final String field;
        private final String message;
        private final String code;

        public FieldError(String field, String message, String code) {
            this.field = field;
            this.message = message;
            this.code = code;
        }

        public FieldError(String field, String message) {
            this(field, message, null);
        }
    }

    public static Error of(String message) {
        return Error.builder()
                .message(message)
                .build();
    }

    public static Error of(String message, FieldError... fieldErrors) {
        return Error.builder()
                .message(message)
                .fields(Arrays.asList(fieldErrors))
                .build();
    }

    public static Error of(String message, List<FieldError> fieldErrors) {
        return Error.builder()
                .message(message)
                .fields(fieldErrors)
                .build();
    }
}
