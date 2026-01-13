package com.optivem.eshop.systemtest.core.tax.dsl.commands;

import com.optivem.eshop.systemtest.core.tax.driver.TaxDriver;
import com.optivem.eshop.systemtest.core.tax.dsl.commands.base.BaseTaxCommand;
import com.optivem.eshop.systemtest.core.tax.dsl.commands.base.TaxUseCaseResult;
import com.optivem.test.dsl.UseCaseContext;
import com.optivem.test.dsl.VoidVerification;

public class GoToTax extends BaseTaxCommand<Void, VoidVerification<UseCaseContext>> {
    public GoToTax(TaxDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    @Override
    public TaxUseCaseResult<Void, VoidVerification<UseCaseContext>> execute() {
        var result = driver.goToTax();
        return new TaxUseCaseResult<>(result, context, VoidVerification::new);
    }
}

