package com.optivem.eshop.systemtest.infra.shop.driver.api;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.errors.SystemError;
import com.optivem.eshop.systemtest.infra.shop.client.api.dtos.errors.ProblemDetailResponse;

public class SystemErrorMapper {

    private SystemErrorMapper() {
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
}
