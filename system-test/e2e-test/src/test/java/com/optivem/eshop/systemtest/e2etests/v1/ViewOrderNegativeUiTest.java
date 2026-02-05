package com.optivem.eshop.systemtest.e2etests.v1;

import com.optivem.eshop.systemtest.base.v1.BaseRawTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled("V1 tests disabled for now")
class ViewOrderNegativeUiTest extends BaseRawTest {

    @BeforeEach
    void setUp() {
        setUpShopBrowser();
        setUpExternalHttpClients();
    }

    @Test
    void shouldNotViewOrderWhenOrderNumberDoesNotExist() {
        // Given
        var orderNumber = "ORD-99999999";

        // When
        shopUiPage.navigate(getShopUiBaseUrl());
        shopUiPage.locator("a[href='/order-history']").click();
        shopUiPage.locator("[aria-label='Order Number']").fill(orderNumber);
        shopUiPage.locator("[aria-label='Refresh Order List']").click();

        // Then - Verify order is not listed
        var rowSelector = String.format("//tr[contains(., '%s')]", orderNumber);
        assertThat(shopUiPage.locator(rowSelector).isVisible()).isFalse();
    }
}
