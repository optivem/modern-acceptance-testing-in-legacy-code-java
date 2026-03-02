package com.optivem.eshop.systemtest.driver.port.tax;

import com.optivem.eshop.systemtest.driver.port.tax.dtos.GetTaxResponse;
import com.optivem.eshop.systemtest.driver.port.tax.dtos.ReturnsTaxRateRequest;
import com.optivem.eshop.systemtest.driver.port.tax.dtos.error.TaxErrorResponse;
import com.optivem.common.Result;

public interface TaxDriver extends AutoCloseable {
    Result<Void, TaxErrorResponse> goToTax();

    Result<GetTaxResponse, TaxErrorResponse> getTaxRate(String country);

    Result<Void, TaxErrorResponse> returnsTaxRate(ReturnsTaxRateRequest request);
}


