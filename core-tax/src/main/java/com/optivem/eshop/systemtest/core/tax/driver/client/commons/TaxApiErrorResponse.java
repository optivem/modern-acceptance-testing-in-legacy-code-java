package com.optivem.eshop.systemtest.core.tax.driver.client.commons;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaxApiErrorResponse {
    private String message;
}
