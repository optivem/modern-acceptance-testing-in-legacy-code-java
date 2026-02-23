package com.optivem.eshop.systemtest.core.erp.driver.dtos.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErpErrorResponse {
    private String message;
}

