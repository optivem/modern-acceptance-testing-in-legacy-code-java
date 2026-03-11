package com.optivem.eshop.systemtest.dsl.core.scenario.given.steps;

import com.optivem.common.Converter;
import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.given.GivenImpl;
import com.optivem.eshop.systemtest.dsl.port.given.steps.GivenProduct;

import static com.optivem.eshop.systemtest.dsl.core.scenario.ScenarioDefaults.*;

public class GivenProductImpl extends BaseGivenStep implements GivenProduct {
    private String sku;
    private String unitPrice;
    private String reviewable;
    private String stockQuantity;

    public GivenProductImpl(GivenImpl given) {
        super(given);
        withSku(DEFAULT_SKU);
        withUnitPrice(DEFAULT_UNIT_PRICE);
        withReviewable("false");
        withStockQuantity(DEFAULT_STOCK_QUANTITY);
    }

    public GivenProductImpl withSku(String sku) {
        this.sku = sku;
        return this;
    }

    public GivenProductImpl withUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public GivenProductImpl withUnitPrice(double unitPrice) {
        withUnitPrice(Converter.fromDouble(unitPrice));
        return this;
    }

    public GivenProductImpl withReviewable(String reviewable) {
        this.reviewable = reviewable;
        return this;
    }

    public GivenProductImpl withStockQuantity(String stockQuantity) {
        this.stockQuantity = stockQuantity;
        return this;
    }

    @Override
    public void execute(AppDsl app) {
        app.erp().returnsProduct()
                .sku(sku)
                .unitPrice(unitPrice)
                .reviewable(reviewable)
                .stockQuantity(stockQuantity)
                .execute()
                .shouldSucceed();
    }
}


