package com.optivem.eshop.systemtest.core.erp.commands;

import com.optivem.eshop.systemtest.core.erp.driver.ErpDriver;
import com.optivem.eshop.systemtest.core.erp.dtos.CreateProductRequest;
import com.optivem.testing.dsl.CommandResult;
import com.optivem.testing.dsl.VoidVerification;
import com.optivem.testing.dsl.Context;
import com.optivem.eshop.systemtest.core.erp.commands.base.BaseErpCommand;

public class CreateProduct extends BaseErpCommand<Void, VoidVerification> {
    private static final double DEFAULT_UNIT_PRICE = 20.00;
    private static final String DEFAULT_TITLE = "Test Product Title";
    private static final String DEFAULT_DESCRIPTION = "Test Product Description";
    private static final String DEFAULT_CATEGORY = "Test Category";
    private static final String DEFAULT_BRAND = "Test Brand";

    private String skuParamAlias;
    private String unitPrice;
    private String title;
    private String description;
    private String category;
    private String brand;

    public CreateProduct(ErpDriver driver, Context context) {
        super(driver, context);

        unitPrice(DEFAULT_UNIT_PRICE);
        title(DEFAULT_TITLE);
        description(DEFAULT_DESCRIPTION);
        category(DEFAULT_CATEGORY);
        brand(DEFAULT_BRAND);
    }

    public CreateProduct sku(String skuParamAlias) {
        this.skuParamAlias = skuParamAlias;
        return this;
    }

    public CreateProduct unitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public CreateProduct unitPrice(double unitPrice) {
        return unitPrice(String.valueOf(unitPrice));
    }

    public CreateProduct title(String title) {
        this.title = title;
        return this;
    }

    public CreateProduct description(String description) {
        this.description = description;
        return this;
    }

    public CreateProduct category(String category) {
        this.category = category;
        return this;
    }

    public CreateProduct brand(String brand) {
        this.brand = brand;
        return this;
    }

    @Override
    public CommandResult<Void, VoidVerification> execute() {
        var sku = context.getParamValue(skuParamAlias);

        var request = CreateProductRequest.builder()
                .id(sku)
                .title(title)
                .description(description)
                .price(unitPrice)
                .category(category)
                .brand(brand)
                .build();

        var result = driver.createProduct(request);

        return new CommandResult<>(result, context, VoidVerification::new);
    }
}

