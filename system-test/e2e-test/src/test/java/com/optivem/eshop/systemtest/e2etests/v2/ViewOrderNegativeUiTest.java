package com.optivem.eshop.systemtest.e2etests.v2;

import com.optivem.eshop.systemtest.base.v2.BaseClientTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class ViewOrderNegativeUiTest extends BaseClientTest {

    @BeforeEach
    void setUp() {
        setUpShopUiClient();
        setUpExternalClients();
    }

    @Test
    void shouldNotViewOrderWhenOrderNumberDoesNotExist() {
        // Given
        var orderNumber = "ORD-99999999";
        var homePage = shopUiClient.openHomePage();
        var orderHistoryPage = homePage.clickOrderHistory();

        // When
        orderHistoryPage.inputOrderNumber(orderNumber);
        orderHistoryPage.clickSearch();

        // Then
        assertThat(orderHistoryPage.isOrderListed(orderNumber)).isFalse();
    }
}
