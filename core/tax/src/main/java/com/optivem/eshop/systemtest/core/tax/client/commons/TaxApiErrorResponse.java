package com.optivem.eshop.systemtest.core.tax.client.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxApiErrorResponse {
    private String message;
}
