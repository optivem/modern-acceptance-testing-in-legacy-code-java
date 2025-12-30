package com.optivem.eshop.systemtest.core.shop.client.api.dtos.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemDetailsFieldErrorResponse {
    private String field;
    private String message;
    private String code;
    private Object rejectedValue;
}
