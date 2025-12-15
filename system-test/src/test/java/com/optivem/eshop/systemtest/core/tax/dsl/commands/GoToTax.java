package com.optivem.eshop.systemtest.core.tax.dsl.commands;

import com.optivem.eshop.systemtest.core.tax.driver.TaxDriver;
import com.optivem.testing.dsl.UseCaseResult;
import com.optivem.testing.dsl.UseCaseVoidSuccessVerification;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.eshop.systemtest.core.tax.dsl.commands.base.BaseTaxCommand;

public class GoToTax extends BaseTaxCommand<Void, UseCaseVoidSuccessVerification> {
    public GoToTax(TaxDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    @Override
    public UseCaseResult<Void, UseCaseVoidSuccessVerification> execute() {
        var result = driver.goToTax();
        return new UseCaseResult<>(result, context, UseCaseVoidSuccessVerification::new);
    }
}

