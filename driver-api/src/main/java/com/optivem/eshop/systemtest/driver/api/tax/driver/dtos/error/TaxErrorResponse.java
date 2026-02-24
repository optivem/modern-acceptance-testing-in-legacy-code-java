package com.optivem.eshop.systemtest.driver.api.tax.driver.dtos.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxErrorResponse {
    private String message;
}
