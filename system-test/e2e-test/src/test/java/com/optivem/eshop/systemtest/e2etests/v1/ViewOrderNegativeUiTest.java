package com.optivem.eshop.systemtest.e2etests.v1;

import com.optivem.eshop.systemtest.e2etests.v1.base.BaseE2eTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled("V1 tests disabled for now")
class ViewOrderNegativeUiTest extends BaseE2eTest {

    @Override
    protected void setShopDriver() {
        setUpShopBrowser();
    }

    @Test
    void shouldNotBeAbleToViewNonExistentOrder() {
        var orderNumber = "NON-EXISTENT-ORDER-99999";

        shopUiPage.navigate(getShopUiBaseUrl());
        shopUiPage.locator("a[href='/order-history']").click();
        shopUiPage.locator("[aria-label='Order Number']").fill(orderNumber);
        shopUiPage.locator("[aria-label='Refresh Order List']").click();

        var rowSelector = String.format("//tr[contains(., '%s')]", orderNumber);
        assertThat(shopUiPage.locator(rowSelector).isVisible()).isFalse();
    }
}
