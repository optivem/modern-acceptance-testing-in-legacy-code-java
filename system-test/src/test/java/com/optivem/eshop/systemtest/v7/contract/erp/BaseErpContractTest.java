package com.optivem.eshop.systemtest.v7.contract.erp;

import com.optivem.eshop.systemtest.v7.contract.base.BaseExternalSystemContractTest;
import org.junit.jupiter.api.Test;

public abstract class BaseErpContractTest extends BaseExternalSystemContractTest {
    @Test
    void shouldBeAbleToGetProduct() {
        scenario
                .given().product().withSku("SKU-123").withUnitPrice(12.0)
                .then().product("SKU-123").hasSku("SKU-123").hasPrice(12.0);
    }

    @Test
    void shouldReturnReviewableField() {
        scenario
                .given().product().withSku("SKU-456").withUnitPrice("15.00").withReviewable("true")
                .then().product("SKU-456").hasReviewable("true");
    }

}
