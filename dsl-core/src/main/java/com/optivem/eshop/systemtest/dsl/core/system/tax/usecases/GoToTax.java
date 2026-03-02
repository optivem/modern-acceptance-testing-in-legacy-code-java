package com.optivem.eshop.systemtest.dsl.core.system.tax.usecases;

import com.optivem.eshop.systemtest.driver.port.tax.TaxDriver;
import com.optivem.eshop.systemtest.dsl.core.system.tax.usecases.base.BaseTaxCommand;
import com.optivem.eshop.systemtest.dsl.core.system.tax.usecases.base.TaxUseCaseResult;
import com.optivem.eshop.systemtest.dsl.core.system.shared.UseCaseContext;
import com.optivem.eshop.systemtest.dsl.core.system.shared.VoidVerification;

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

