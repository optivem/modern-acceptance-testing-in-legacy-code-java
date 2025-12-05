package com.optivem.eshop.systemtest.core.dsl.shop.commands.confirm;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.BaseShopCommand;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.execute.PlaceOrder;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;

public class ConfirmOrderPlaced extends BaseShopCommand<Void> {
    private String orderNumberResultAlias;

    public ConfirmOrderPlaced(ShopDriver driver, DslContext context) {
        super(driver, context);
    }

    public ConfirmOrderPlaced orderNumber(String orderNumberResultAlias) {
        this.orderNumberResultAlias = orderNumberResultAlias;
        return this;
    }

    @Override
    public Void execute() {
        var result = context.results().getResult(PlaceOrder.COMMAND_NAME, orderNumberResultAlias, PlaceOrderResponse.class);
        assertThatResult(result).isSuccess();
        return null;
    }
}
