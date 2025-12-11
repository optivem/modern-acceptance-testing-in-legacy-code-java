package com.optivem.eshop.systemtest.dsl.external.erp.commands;

import com.optivem.eshop.systemtest.dsl.external.erp.driver.ErpDriver;
import com.optivem.testing.dsl.CommandResult;
import com.optivem.testing.dsl.VoidVerification;
import com.optivem.testing.dsl.Context;
import com.optivem.eshop.systemtest.dsl.external.erp.commands.base.BaseErpCommand;

public class CreateProduct extends BaseErpCommand<Void, VoidVerification> {
    private static final double DEFAULT_UNIT_PRICE = 20.00;

    private String skuParamAlias;
    private String unitPrice;

    public CreateProduct(ErpDriver driver, Context context) {
        super(driver, context);

        unitPrice(DEFAULT_UNIT_PRICE);
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

    @Override
    public CommandResult<Void, VoidVerification> execute() {
        var sku = context.getParamValue(skuParamAlias);

        var result = driver.createProduct(sku, unitPrice);

        return new CommandResult<>(result, context, VoidVerification::new);
    }
}

