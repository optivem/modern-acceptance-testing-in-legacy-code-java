package com.optivem.eshop.systemtest.dsl.core.system.tax.dsl.usecases;

import com.optivem.eshop.systemtest.driver.api.tax.TaxDriver;
import com.optivem.eshop.systemtest.driver.api.tax.dtos.ReturnsTaxRateRequest;
import com.optivem.eshop.systemtest.dsl.core.system.tax.dsl.usecases.base.BaseTaxCommand;
import com.optivem.eshop.systemtest.dsl.core.system.tax.dsl.usecases.base.TaxUseCaseResult;
import com.optivem.commons.util.Converter;
import com.optivem.eshop.systemtest.dsl.core.system.shared.UseCaseContext;
import com.optivem.eshop.systemtest.dsl.core.system.shared.VoidVerification;

public class ReturnsTaxRate extends BaseTaxCommand<Void, VoidVerification> {
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
    public TaxUseCaseResult<Void, VoidVerification> execute() {
        var country = context.getParamValueOrLiteral(countryAlias);

        var request = ReturnsTaxRateRequest.builder()
                .country(country)
                .taxRate(taxRate)
                .build();

        var result = driver.returnsTaxRate(request);

        return new TaxUseCaseResult<>(result, context, VoidVerification::new);
    }
}

