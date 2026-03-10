package com.optivem.eshop.systemtest.dsl.core.app.external.tax.usecases;

import com.optivem.eshop.systemtest.driver.port.external.tax.TaxDriver;
import com.optivem.eshop.systemtest.driver.port.external.tax.dtos.ReturnsTaxRateRequest;
import com.optivem.eshop.systemtest.dsl.core.app.external.tax.usecases.base.BaseTaxUseCase;
import com.optivem.common.Converter;
import com.optivem.eshop.systemtest.dsl.core.shared.UseCaseResult;
import com.optivem.eshop.systemtest.dsl.core.shared.UseCaseContext;
import com.optivem.eshop.systemtest.dsl.core.shared.VoidVerification;

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
