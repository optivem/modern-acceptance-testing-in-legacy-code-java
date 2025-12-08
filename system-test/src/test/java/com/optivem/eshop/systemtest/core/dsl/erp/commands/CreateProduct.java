package com.optivem.eshop.systemtest.core.dsl.erp.commands;

import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.CommandResult;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.VoidVerifications;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.eshop.systemtest.core.dsl.erp.commands.base.BaseErpCommand;

public class CreateProduct extends BaseErpCommand<CommandResult<Void, VoidVerifications>> {
    private String skuParamAlias;
    private String unitPrice;

    public CreateProduct(ErpApiDriver driver, DslContext context) {
        super(driver, context);
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
    public CommandResult<Void, VoidVerifications> execute() {
        var sku = context.params().getOrGenerateAliasValue(skuParamAlias);

        var result = driver.createProduct(sku, unitPrice);

        return new CommandResult<>(result, context, VoidVerifications::new);
    }
}

