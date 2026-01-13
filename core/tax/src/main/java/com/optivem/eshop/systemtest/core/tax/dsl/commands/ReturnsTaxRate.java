package com.optivem.eshop.systemtest.core.tax.dsl.commands;

import com.optivem.eshop.systemtest.core.tax.driver.TaxDriver;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.ReturnsTaxRateRequest;
import com.optivem.eshop.systemtest.core.tax.dsl.commands.base.BaseTaxCommand;
import com.optivem.eshop.systemtest.core.tax.dsl.commands.base.TaxUseCaseResult;
import com.optivem.lang.Converter;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.testing.dsl.VoidVerification;

public class ReturnsTaxRate extends BaseTaxCommand<Void, VoidVerification<UseCaseContext>> {
    private String countryAlias;
    private String taxRate;

    public ReturnsTaxRate(TaxDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    public ReturnsTaxRate country(String countryAlias) {
        this.countryAlias = countryAlias;
        return this;
    }

    public ReturnsTaxRate taxRate(String taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    public ReturnsTaxRate taxRate(double taxRate) {
        return taxRate(Converter.fromDouble(taxRate));
    }

    @Override
    public TaxUseCaseResult<Void, VoidVerification<UseCaseContext>> execute() {
        var country = context.getParamValueOrLiteral(countryAlias);

        var request = ReturnsTaxRateRequest.builder()
                .country(country)
                .taxRate(taxRate)
                .build();

        var result = driver.returnsTaxRate(request);

        return new TaxUseCaseResult<>(result, context, VoidVerification::new);
    }
}

