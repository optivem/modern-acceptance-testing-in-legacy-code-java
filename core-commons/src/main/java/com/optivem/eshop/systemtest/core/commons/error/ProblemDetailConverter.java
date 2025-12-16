package com.optivem.eshop.systemtest.core.commons.error;

public final class ProblemDetailConverter {
    
    private ProblemDetailConverter() {
        // Utility class
    }

    public static Error toError(ProblemDetailResponse problemDetail) {
        var message = problemDetail.getDetail() != null ? problemDetail.getDetail() : "Request failed";

        if (problemDetail.getErrors() != null && !problemDetail.getErrors().isEmpty()) {
            var fieldErrors = problemDetail.getErrors().stream()
                    .map(e -> new Error.FieldError(e.getField(), e.getMessage(), e.getCode()))
                    .toList();
            return Error.of(message, fieldErrors);
        }

        return Error.of(message);
    }
}
