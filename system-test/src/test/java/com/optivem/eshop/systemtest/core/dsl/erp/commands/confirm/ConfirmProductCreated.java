package com.optivem.eshop.systemtest.core.dsl.erp.commands.confirm;

import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.eshop.systemtest.core.dsl.erp.commands.BaseErpCommand;
import com.optivem.eshop.systemtest.core.dsl.erp.commands.execute.CreateProduct;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;

public class ConfirmProductCreated extends BaseErpCommand<Void> {
    private String skuParamAlias;

    public ConfirmProductCreated(ErpApiDriver driver, DslContext context) {
        super(driver, context);
    }

    public ConfirmProductCreated sku(String skuParamAlias) {
        this.skuParamAlias = skuParamAlias;
        return this;
    }

    @Override
    public Void execute() {
        var result = context.results().getResult(CreateProduct.COMMAND_NAME, skuParamAlias, Void.class);
        assertThatResult(result).isSuccess();
        return null;
    }
}