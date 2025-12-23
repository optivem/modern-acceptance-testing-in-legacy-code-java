package com.optivem.eshop.systemtest.core.tax.driver.dtos.error;

import com.optivem.eshop.systemtest.core.tax.client.dtos.error.ExtTaxErrorResponse;
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

    public static TaxErrorResponse from(ExtTaxErrorResponse errorResponse) {
        return TaxErrorResponse.builder().message(errorResponse.getMessage()).build();
    }
}
