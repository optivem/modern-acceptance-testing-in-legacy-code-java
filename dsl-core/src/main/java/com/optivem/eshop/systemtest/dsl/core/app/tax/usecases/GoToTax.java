package com.optivem.eshop.systemtest.dsl.core.app.tax.usecases;

import com.optivem.eshop.systemtest.driver.port.tax.TaxDriver;
import com.optivem.eshop.systemtest.dsl.core.app.tax.usecases.base.BaseTaxUseCase;
import com.optivem.eshop.systemtest.dsl.core.app.UseCaseResult;
import com.optivem.eshop.systemtest.dsl.core.app.UseCaseContext;
import com.optivem.eshop.systemtest.dsl.core.app.VoidVerification;

public class GoToTax extends BaseTaxUseCase<Void, VoidVerification> {
    public GoToTax(TaxDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    @Override
    public UseCaseResult<Void, VoidVerification> execute() {
        var result = driver.goToTax();
        return new UseCaseResult<>(result, context, VoidVerification::new);
    }
}

