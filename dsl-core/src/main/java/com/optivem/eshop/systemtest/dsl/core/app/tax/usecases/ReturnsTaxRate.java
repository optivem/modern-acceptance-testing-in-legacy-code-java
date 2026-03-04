package com.optivem.eshop.systemtest.dsl.core.app.tax.usecases;

import com.optivem.eshop.systemtest.driver.port.tax.TaxDriver;
import com.optivem.eshop.systemtest.driver.port.tax.dtos.ReturnsTaxRateRequest;
import com.optivem.eshop.systemtest.dsl.core.app.tax.usecases.base.BaseTaxUseCase;
import com.optivem.common.Converter;
import com.optivem.eshop.systemtest.dsl.common.UseCaseResult;
import com.optivem.eshop.systemtest.dsl.common.UseCaseContext;
import com.optivem.eshop.systemtest.dsl.common.VoidVerification;

public class ReturnsTaxRate extends BaseTaxUseCase<Void, VoidVerification> {
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
    public UseCaseResult<Void, VoidVerification> execute() {
        var country = context.getParamValueOrLiteral(countryAlias);

        var request = ReturnsTaxRateRequest.builder()
                .country(country)
                .taxRate(taxRate)
                .build();

        var result = driver.returnsTaxRate(request);

        return new UseCaseResult<>(result, context, VoidVerification::new);
    }
}


