package com.optivem.eshop.systemtest.core.shop.driver.dtos.error;

import com.optivem.eshop.systemtest.core.shop.client.dtos.error.ProblemDetailResponse;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
public class SystemError {
    private final String message;
    private final List<FieldError> fields;

    public static SystemError of(String message) {
        return SystemError.builder()
                .message(message)
                .build();
    }

    public static SystemError of(String message, FieldError... fieldErrors) {
        return SystemError.builder()
                .message(message)
                .fields(Arrays.asList(fieldErrors))
                .build();
    }

    public static SystemError of(String message, List<FieldError> fieldErrors) {
        return SystemError.builder()
                .message(message)
                .fields(fieldErrors)
                .build();
    }

    public static SystemError from(ProblemDetailResponse problemDetail) {
        var message = problemDetail.getDetail() != null ? problemDetail.getDetail() : "Request failed";

        if (problemDetail.getErrors() != null && !problemDetail.getErrors().isEmpty()) {
            var fieldErrors = problemDetail.getErrors().stream()
                    .map(e -> new SystemError.FieldError(e.getField(), e.getMessage(), e.getCode()))
                    .toList();
            return SystemError.of(message, fieldErrors);
        }

        return SystemError.of(message);
    }

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
}
