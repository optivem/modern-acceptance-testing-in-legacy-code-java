package com.optivem.eshop.systemtest.core.dsl.erp.commands.given;

import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.eshop.systemtest.core.dsl.erp.commands.BaseErpCommand;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;

public class GivenProduct extends BaseErpCommand<Void> {
    private String skuParamAlias;
    private String unitPrice;

    public GivenProduct(ErpApiDriver driver, DslContext context) {
        super(driver, context);
    }

    public GivenProduct sku(String skuParamAlias) {
        this.skuParamAlias = skuParamAlias;
        return this;
    }

    public GivenProduct unitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    @Override
    public Void execute() {
        var sku = context.params().getOrGenerateAliasValue(skuParamAlias);

        var result = driver.createProduct(sku, unitPrice);
        context.results().registerResult("CreateProduct", skuParamAlias, result);

        // Automatically confirm the product was created
        assertThatResult(result).isSuccess();

        return null;
    }
}

