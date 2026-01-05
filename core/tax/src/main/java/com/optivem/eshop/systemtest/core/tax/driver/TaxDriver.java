package com.optivem.eshop.systemtest.core.tax.driver;

import com.optivem.eshop.systemtest.core.tax.driver.dtos.GetTaxResponse;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.ReturnsTaxRateRequest;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.error.TaxErrorResponse;
import com.optivem.lang.Result;

public interface TaxDriver extends AutoCloseable {
    Result<Void, TaxErrorResponse> goToTax();

    Result<Void, TaxErrorResponse> returnsTaxRate(ReturnsTaxRateRequest request);

    Result<GetTaxResponse, TaxErrorResponse> getTaxRate(String country);
}

