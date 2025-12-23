package com.optivem.eshop.systemtest.core.tax.driver;

import com.optivem.eshop.systemtest.core.commons.driver.Driver;
import com.optivem.eshop.systemtest.core.tax.commons.TaxError;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.GetTaxRequest;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.ReturnsTaxRateRequest;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.GetTaxResponse;
import com.optivem.lang.Result;

public interface TaxDriver extends Driver {
    Result<Void, TaxError> goToTax();
    Result<Void, TaxError> returnsTaxRate(ReturnsTaxRateRequest request);
    Result<GetTaxResponse, TaxError> getTax(GetTaxRequest request);
}

