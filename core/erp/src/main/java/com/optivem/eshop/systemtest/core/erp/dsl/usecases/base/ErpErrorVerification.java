package com.optivem.eshop.systemtest.core.erp.dsl.usecases.base;

import com.optivem.eshop.systemtest.core.erp.driver.dtos.error.ErpErrorResponse;
import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.commons.dsl.UseCaseContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ErpErrorVerification extends ResponseVerification<ErpErrorResponse> {

    public ErpErrorVerification(ErpErrorResponse error, UseCaseContext context) {
        super(error, context);
    }

    public ErpErrorVerification errorMessage(String expectedMessage) {
        var expandedExpectedMessage = getContext().expandAliases(expectedMessage);
        var error = getResponse();
        var errorMessage = error.getMessage();
        assertThat(errorMessage)
                .withFailMessage("Expected error message: '%s', but got: '%s'", expandedExpectedMessage, errorMessage)
                .isEqualTo(expandedExpectedMessage);
        return this;
    }
}
