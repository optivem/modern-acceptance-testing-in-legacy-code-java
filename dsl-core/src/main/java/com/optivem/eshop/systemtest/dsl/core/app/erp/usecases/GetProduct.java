package com.optivem.eshop.systemtest.dsl.core.app.erp.usecases;

import com.optivem.eshop.systemtest.driver.port.erp.ErpDriver;
import com.optivem.eshop.systemtest.driver.port.erp.dtos.GetProductRequest;
import com.optivem.eshop.systemtest.driver.port.erp.dtos.GetProductResponse;
import com.optivem.eshop.systemtest.dsl.core.app.erp.usecases.base.BaseErpCommand;
import com.optivem.eshop.systemtest.dsl.core.app.shared.UseCaseResult;
import com.optivem.eshop.systemtest.dsl.core.app.shared.UseCaseContext;

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
    public UseCaseResult<GetProductResponse, GetProductVerification> execute() {
        var sku = context.getParamValue(skuParamAlias);

        var request = GetProductRequest.builder()
                .sku(sku)
                .build();

        var result = driver.getProduct(request);

        return new UseCaseResult<>(result, context, GetProductVerification::new);
    }
}

