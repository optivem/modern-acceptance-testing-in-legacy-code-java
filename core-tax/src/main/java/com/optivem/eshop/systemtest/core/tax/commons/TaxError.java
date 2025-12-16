package com.optivem.eshop.systemtest.core.tax.commons;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaxError {
    private String message;
}
