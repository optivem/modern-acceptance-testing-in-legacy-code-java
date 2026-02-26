package com.optivem.eshop.systemtest.driver.api.tax;

import com.optivem.eshop.systemtest.driver.api.tax.dtos.GetTaxResponse;
import com.optivem.eshop.systemtest.driver.api.tax.dtos.ReturnsTaxRateRequest;
import com.optivem.eshop.systemtest.driver.api.tax.dtos.error.TaxErrorResponse;
import com.optivem.common.util.Result;

public interface TaxDriver extends AutoCloseable {
    Result<Void, TaxErrorResponse> goToTax();

    Result<GetTaxResponse, TaxErrorResponse> getTaxRate(String country);

    Result<Void, TaxErrorResponse> returnsTaxRate(ReturnsTaxRateRequest request);
}


