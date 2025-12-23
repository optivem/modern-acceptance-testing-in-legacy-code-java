package com.optivem.eshop.systemtest.core.tax.driver;

import com.optivem.eshop.systemtest.core.commons.driver.Driver;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.error.TaxErrorResponse;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.GetTaxRequest;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.ReturnsTaxRateRequest;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.GetTaxResponse;
import com.optivem.lang.Result;

public interface TaxDriver extends Driver {
    Result<Void, TaxErrorResponse> goToTax();
    Result<Void, TaxErrorResponse> returnsTaxRate(ReturnsTaxRateRequest request);
    Result<GetTaxResponse, TaxErrorResponse> getTaxRate(GetTaxRequest request);
}

