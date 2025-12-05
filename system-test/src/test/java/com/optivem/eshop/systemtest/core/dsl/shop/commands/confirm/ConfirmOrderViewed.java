package com.optivem.eshop.systemtest.core.dsl.shop.commands.confirm;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.GetOrderResponse;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.BaseShopCommand;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.execute.ViewOrder;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;

public class ConfirmOrderViewed extends BaseShopCommand<Void> {
    private String orderNumberAlias;

    public ConfirmOrderViewed(ShopDriver driver, DslContext context) {
        super(driver, context);
    }

    public ConfirmOrderViewed orderNumber(String orderNumberAlias) {
        this.orderNumberAlias = orderNumberAlias;
        return this;
    }

    @Override
    public Void execute() {
        var result = context.results().getResult(ViewOrder.COMMAND_NAME, orderNumberAlias, GetOrderResponse.class);
        assertThatResult(result).isSuccess();
        return null;
    }
}

