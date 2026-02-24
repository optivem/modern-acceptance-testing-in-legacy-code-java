package com.optivem.eshop.systemtest.core.erp.dsl.usecases;

import com.optivem.eshop.systemtest.driver.api.erp.ErpDriver;
import com.optivem.eshop.systemtest.driver.api.erp.dtos.GetProductRequest;
import com.optivem.eshop.systemtest.driver.api.erp.dtos.GetProductResponse;
import com.optivem.eshop.systemtest.core.erp.dsl.usecases.base.BaseErpCommand;
import com.optivem.eshop.systemtest.core.erp.dsl.usecases.base.ErpUseCaseResult;
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
