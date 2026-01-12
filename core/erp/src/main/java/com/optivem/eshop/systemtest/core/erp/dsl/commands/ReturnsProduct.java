package com.optivem.eshop.systemtest.core.erp.dsl.commands;

import com.optivem.eshop.systemtest.core.erp.driver.ErpDriver;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.ReturnsProductRequest;
import com.optivem.eshop.systemtest.core.erp.dsl.commands.base.BaseErpCommand;
import com.optivem.eshop.systemtest.core.erp.dsl.commands.base.ErpUseCaseResult;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.testing.dsl.VoidVerification;

public class ReturnsProduct extends BaseErpCommand<Void, VoidVerification<UseCaseContext>> {
    private String skuParamAlias;
    private String unitPrice;

    public ReturnsProduct(ErpDriver driver, UseCaseContext context) {
        super(driver, context);
    }


    public ReturnsProduct sku(String skuParamAlias) {
        this.skuParamAlias = skuParamAlias;
        return this;
    }

    public ReturnsProduct unitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public ReturnsProduct unitPrice(double unitPrice) {
        return unitPrice(String.valueOf(unitPrice));
    }

    @Override
    public ErpUseCaseResult<Void, VoidVerification<UseCaseContext>> execute() {
        var sku = context.getParamValue(skuParamAlias);

        var request = ReturnsProductRequest.builder()
                .sku(sku)
                .price(unitPrice)
                .build();

        var result = driver.returnsProduct(request);

        return new ErpUseCaseResult<>(result, context, VoidVerification::new);
    }
}

