package com.optivem.eshop.systemtest.driver.port.external.tax;

import com.optivem.eshop.systemtest.driver.port.external.tax.dtos.GetTaxResponse;
import com.optivem.eshop.systemtest.driver.port.external.tax.dtos.ReturnsTaxRateRequest;
import com.optivem.eshop.systemtest.driver.port.shared.dtos.ErrorResponse;
import com.optivem.common.Result;

public interface TaxDriver extends AutoCloseable {
    Result<Void, ErrorResponse> goToTax();

    Result<GetTaxResponse, ErrorResponse> getTaxRate(String country);

    Result<Void, ErrorResponse> returnsTaxRate(ReturnsTaxRateRequest request);
}
