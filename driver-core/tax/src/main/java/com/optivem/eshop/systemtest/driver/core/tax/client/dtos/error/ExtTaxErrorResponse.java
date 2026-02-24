package com.optivem.eshop.systemtest.driver.core.tax.client.dtos.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtTaxErrorResponse {
    private String message;
}
