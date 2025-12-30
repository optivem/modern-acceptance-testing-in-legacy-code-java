package com.optivem.eshop.systemtest.core.shop.client.api.dtos.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemDetailResponse {
    private String type;
    private String title;
    private Integer status;
    private String detail;
    private String instance;
    private String timestamp;
    private List<ProblemDetailsFieldErrorResponse> errors;
}
