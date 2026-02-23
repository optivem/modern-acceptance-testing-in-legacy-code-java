package com.optivem.eshop.systemtest.infra.erp.client.dtos.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtErpErrorResponse {
    private String message;
}

