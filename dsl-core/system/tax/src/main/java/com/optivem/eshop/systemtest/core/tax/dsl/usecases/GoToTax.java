package com.optivem.eshop.systemtest.core.tax.dsl.usecases;

import com.optivem.eshop.systemtest.driver.api.tax.driver.TaxDriver;
import com.optivem.eshop.systemtest.core.tax.dsl.usecases.base.BaseTaxCommand;
import com.optivem.eshop.systemtest.core.tax.dsl.usecases.base.TaxUseCaseResult;
import com.optivem.commons.dsl.UseCaseContext;
import com.optivem.commons.dsl.VoidVerification;

public class GoToTax extends BaseTaxCommand<Void, VoidVerification> {
    public GoToTax(TaxDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    @Override
    public TaxUseCaseResult<Void, VoidVerification> execute() {
        var result = driver.goToTax();
        return new TaxUseCaseResult<>(result, context, VoidVerification::new);
    }
}
