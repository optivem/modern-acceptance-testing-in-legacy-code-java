package com.optivem.eshop.systemtest.dsl.core.app.erp.usecases;

import com.optivem.eshop.systemtest.driver.port.erp.ErpDriver;
import com.optivem.eshop.systemtest.driver.port.erp.dtos.ReturnsProductRequest;
import com.optivem.eshop.systemtest.dsl.core.app.erp.usecases.base.BaseErpUseCase;
import com.optivem.common.Converter;
import com.optivem.eshop.systemtest.dsl.core.shared.UseCaseResult;
import com.optivem.eshop.systemtest.dsl.core.shared.UseCaseContext;
import com.optivem.eshop.systemtest.dsl.core.shared.VoidVerification;

public class ReturnsProduct extends BaseErpUseCase<Void, VoidVerification> {
    private String skuParamAlias;
    private String unitPrice;
    private Boolean reviewable;

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

    public ReturnsProduct reviewable(Boolean reviewable) {
        this.reviewable = reviewable;
        return this;
    }

    @Override
    public UseCaseResult<Void, VoidVerification> execute() {
        var sku = context.getParamValue(skuParamAlias);

        var request = ReturnsProductRequest.builder()
                .sku(sku)
                .price(unitPrice)
                .reviewable(reviewable)
                .build();

        var result = driver.returnsProduct(request);

        return new UseCaseResult<>(result, context, VoidVerification::new);
    }
}



