package com.optivem.eshop.systemtest.core.tax.dsl.usecases;

import com.optivem.eshop.systemtest.driver.api.tax.driver.dtos.GetTaxResponse;
import com.optivem.commons.util.Converter;
import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.commons.dsl.UseCaseContext;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class GetTaxVerification extends ResponseVerification<GetTaxResponse> {

    public GetTaxVerification(GetTaxResponse response, UseCaseContext context) {
        super(response, context);
    }

    public GetTaxVerification country(String expectedCountryAlias) {
        var expectedCountry = getContext().getParamValueOrLiteral(expectedCountryAlias);
        var actualCountry = getResponse().getCountry();
        assertThat(actualCountry)
                .withFailMessage("Expected country to be '%s', but was '%s'", expectedCountryAlias, actualCountry)
                .isEqualTo(expectedCountry);
        return this;
    }

    public GetTaxVerification taxRate(BigDecimal expectedTaxRate) {
        var actualTaxRate = getResponse().getTaxRate();
        assertThat(actualTaxRate)
                .withFailMessage("Expected tax rate to be %s, but was %s", expectedTaxRate, actualTaxRate)
                .isEqualTo(expectedTaxRate);
        return this;
    }

    public GetTaxVerification taxRate(double expectedTaxRate) {
        return taxRate(Converter.toBigDecimal(expectedTaxRate));
    }

    public GetTaxVerification taxRate(String expectedTaxRate) {
        return taxRate(Converter.toBigDecimal(expectedTaxRate));
    }

    public GetTaxVerification taxRateIsPositive() {
        var actualTaxRate = getResponse().getTaxRate();
        assertThat(actualTaxRate)
                .withFailMessage("Expected tax rate to be positive, but was %s", actualTaxRate)
                .isPositive();
        return this;
    }
}
