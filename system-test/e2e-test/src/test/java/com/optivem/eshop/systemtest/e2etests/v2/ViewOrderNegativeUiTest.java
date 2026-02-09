package com.optivem.eshop.systemtest.e2etests.v2;

import com.optivem.eshop.systemtest.e2etests.v2.base.BaseE2eTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ViewOrderNegativeUiTest extends BaseE2eTest {

    @Override
    protected void setShopDriver() {
        setUpShopUiClient();
    }

    @Test
    void shouldNotBeAbleToViewNonExistentOrder() {
        var orderNumber = "NON-EXISTENT-ORDER-99999";
        var homePage = shopUiClient.openHomePage();
        var orderHistoryPage = homePage.clickOrderHistory();

        orderHistoryPage.inputOrderNumber(orderNumber);
        orderHistoryPage.clickSearch();

        assertThat(orderHistoryPage.isOrderListed(orderNumber)).isFalse();
    }
}
