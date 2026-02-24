package com.optivem.eshop.systemtest.core.erp.dsl.usecases;

import com.optivem.eshop.systemtest.driver.api.erp.driver.ErpDriver;
import com.optivem.eshop.systemtest.driver.api.erp.driver.dtos.ReturnsProductRequest;
import com.optivem.eshop.systemtest.core.erp.dsl.usecases.base.BaseErpCommand;
import com.optivem.eshop.systemtest.core.erp.dsl.usecases.base.ErpUseCaseResult;
import com.optivem.commons.util.Converter;
import com.optivem.commons.dsl.UseCaseContext;
import com.optivem.commons.dsl.VoidVerification;

public class ReturnsProduct extends BaseErpCommand<Void, VoidVerification> {
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
        return unitPrice(Converter.fromDouble(unitPrice));
    }

    @Override
    public ErpUseCaseResult<Void, VoidVerification> execute() {
        var sku = context.getParamValue(skuParamAlias);

        var request = ReturnsProductRequest.builder()
                .sku(sku)
                .price(unitPrice)
                .build();

        var result = driver.returnsProduct(request);

        return new ErpUseCaseResult<>(result, context, VoidVerification::new);
    }
}
