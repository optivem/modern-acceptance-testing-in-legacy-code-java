package com.optivem.eshop.systemtest.core.tax.driver.client.commons;

import com.optivem.eshop.systemtest.core.tax.commons.TaxError;

public class TaxErrorConverter {
    public static TaxError from(TaxApiErrorResponse errorResponse) {
        return TaxError.builder().message(errorResponse.getMessage()).build();
    }
}
