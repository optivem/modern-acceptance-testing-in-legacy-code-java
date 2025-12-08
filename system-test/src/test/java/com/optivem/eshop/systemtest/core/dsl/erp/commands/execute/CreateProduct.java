package com.optivem.eshop.systemtest.core.dsl.erp.commands.execute;

import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.CommandResult;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.VoidSuccessResult;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.eshop.systemtest.core.dsl.erp.commands.BaseErpCommand;

public class CreateProduct extends BaseErpCommand<CommandResult<Void, VoidSuccessResult>> {
    public static final String COMMAND_NAME = "CreateProduct";

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
    public CommandResult<Void, VoidSuccessResult> execute() {
        var sku = context.params().getOrGenerateAliasValue(skuParamAlias);

        var result = driver.createProduct(sku, unitPrice);
        context.results().registerResult(COMMAND_NAME, skuParamAlias, result);
        return new CommandResult<>(result, context, VoidSuccessResult::new);
    }
}

