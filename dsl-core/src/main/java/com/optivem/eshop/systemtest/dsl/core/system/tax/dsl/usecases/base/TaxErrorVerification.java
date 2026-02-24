package com.optivem.eshop.systemtest.dsl.core.system.tax.dsl.usecases.base;

import com.optivem.eshop.systemtest.driver.api.tax.dtos.error.TaxErrorResponse;
import com.optivem.eshop.systemtest.dsl.core.system.shared.ResponseVerification;
import com.optivem.eshop.systemtest.dsl.core.system.shared.UseCaseContext;

import static org.assertj.core.api.Assertions.assertThat;

public class TaxErrorVerification extends ResponseVerification<TaxErrorResponse> {

    public TaxErrorVerification(TaxErrorResponse error, UseCaseContext context) {
        super(error, context);
    }

    public TaxErrorVerification errorMessage(String expectedMessage) {
        var expandedExpectedMessage = getContext().expandAliases(expectedMessage);
        var error = getResponse();
        var errorMessage = error.getMessage();
        assertThat(errorMessage)
                .withFailMessage("Expected error message: '%s', but got: '%s'", expandedExpectedMessage, errorMessage)
                .isEqualTo(expandedExpectedMessage);
        return this;
    }
}

