package com.optivem.eshop.systemtest.core.tax.dsl.commands.base;

import com.optivem.eshop.systemtest.core.tax.commons.TaxError;
import com.optivem.testing.dsl.ResponseVerification;
import com.optivem.testing.dsl.UseCaseContext;

import static org.assertj.core.api.Assertions.assertThat;

public class TaxErrorVerification extends ResponseVerification<TaxError, UseCaseContext> {

    public TaxErrorVerification(TaxError error, UseCaseContext context) {
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
