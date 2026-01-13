package com.optivem.eshop.systemtest.core.erp.dsl.verifications;

import com.optivem.eshop.systemtest.core.erp.driver.dtos.GetProductResponse;
import com.optivem.commons.util.Converter;
import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.commons.dsl.UseCaseContext;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class GetProductVerification extends ResponseVerification<GetProductResponse, UseCaseContext> {

    public GetProductVerification(GetProductResponse response, UseCaseContext context) {
        super(response, context);
    }

    public GetProductVerification sku(String skuParamAlias) {
        var expectedSku = context.getParamValue(skuParamAlias);
        var actualSku = response.getSku();
        assertThat(actualSku)
                .withFailMessage("Expected SKU to be '%s', but was '%s'", expectedSku, actualSku)
                .isEqualTo(expectedSku);
        return this;
    }

    public GetProductVerification price(BigDecimal expectedPrice) {
        var actualPrice = response.getPrice();
        assertThat(actualPrice)
                .withFailMessage("Expected price to be %s, but was %s", expectedPrice, actualPrice)
                .isEqualTo(expectedPrice);
        return this;
    }

    public GetProductVerification price(double expectedPrice) {
        return price(Converter.toBigDecimal(expectedPrice));
    }

    public GetProductVerification price(String expectedPrice) {
        return price(Converter.toBigDecimal(expectedPrice));
    }
}
