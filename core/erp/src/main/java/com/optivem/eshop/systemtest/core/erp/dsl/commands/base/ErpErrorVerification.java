package com.optivem.eshop.systemtest.core.erp.dsl.commands.base;

import com.optivem.eshop.systemtest.core.erp.driver.dtos.error.ErpErrorResponse;
import com.optivem.test.dsl.ResponseVerification;
import com.optivem.test.dsl.UseCaseContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ErpErrorVerification extends ResponseVerification<ErpErrorResponse, UseCaseContext> {

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