package com.optivem.eshop.systemtest.core.dsl.erp.commands;

import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.CommandResult;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.VoidVerification;
import com.optivem.eshop.systemtest.core.dsl.commons.context.Context;
import com.optivem.eshop.systemtest.core.dsl.erp.commands.base.BaseErpCommand;

public class CreateProduct extends BaseErpCommand<Void, VoidVerification> {
    private static final String DEFAULT_UNIT_PRICE = "20.00";

    private String skuParamAlias;
    private String unitPrice;

    public CreateProduct(ErpApiDriver driver, Context context) {
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

    @Override
    public CommandResult<Void, VoidVerification> execute() {
        var sku = context.getParamValue(skuParamAlias);

        var result = driver.createProduct(sku, unitPrice);

        return new CommandResult<>(result, context, VoidVerification::new);
    }
}

