package com.optivem.eshop.systemtest.core.dsl.shop.commands.confirm;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.BaseShopCommand;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.execute.GoToShop;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;

public class ConfirmShopOpened extends BaseShopCommand<Void> {
    public ConfirmShopOpened(ShopDriver driver, DslContext context) {
        super(driver, context);
    }

    @Override
    public Void execute() {
        var result = context.results().getResult(GoToShop.COMMAND_NAME, Void.class);
        assertThatResult(result).isSuccess();
        return null;
    }
}
