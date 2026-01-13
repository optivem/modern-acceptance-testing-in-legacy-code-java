package com.optivem.eshop.systemtest.core.erp.dsl.commands;

import com.optivem.eshop.systemtest.core.erp.driver.ErpDriver;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.GetProductRequest;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.GetProductResponse;
import com.optivem.eshop.systemtest.core.erp.dsl.commands.base.BaseErpCommand;
import com.optivem.eshop.systemtest.core.erp.dsl.commands.base.ErpUseCaseResult;
import com.optivem.eshop.systemtest.core.erp.dsl.verifications.GetProductVerification;
import com.optivem.commons.dsl.UseCaseContext;

public class GetProduct extends BaseErpCommand<GetProductResponse, GetProductVerification> {
    private String skuParamAlias;

    public GetProduct(ErpDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    public GetProduct sku(String skuParamAlias) {
        this.skuParamAlias = skuParamAlias;
        return this;
    }

    @Override
    public ErpUseCaseResult<GetProductResponse, GetProductVerification> execute() {
        var sku = context.getParamValue(skuParamAlias);

        var request = GetProductRequest.builder()
                .sku(sku)
                .build();

        var result = driver.getProduct(request);

        return new ErpUseCaseResult<>(result, context, GetProductVerification::new);
    }
}

