package com.optivem.eshop.systemtest.e2etests.v1;

import com.optivem.eshop.systemtest.base.v1.BaseRawTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.COUNTRY;
import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.SKU;
import static org.assertj.core.api.Assertions.assertThat;

@Disabled("V1 tests disabled for now")
class PlaceOrderNegativeUiTest extends BaseRawTest {

    @BeforeEach
    void setUp() {
        setUpShopBrowser();
        setUpExternalHttpClients();
    }

    @Test
    void shouldNotPlaceOrderWhenQuantityIsZero() {
        // Given
        shopUiPage.navigate(getShopUiBaseUrl());
        shopUiPage.locator("a[href='/shop']").click();

        // When
        shopUiPage.locator("[aria-label=\"SKU\"]").fill(SKU);
        shopUiPage.locator("[aria-label=\"Quantity\"]").fill("0");
        shopUiPage.locator("[aria-label=\"Country\"]").fill(COUNTRY);
        shopUiPage.locator("[aria-label=\"Place Order\"]").click();

        // Then - Verify error message is displayed
        var errorAlert = shopUiPage.locator("[role='alert']");
        assertThat(errorAlert.isVisible()).isTrue();
        var errorText = errorAlert.textContent();
        assertThat(errorText).contains("The request contains one or more validation errors");
        assertThat(errorText).contains("quantity");
        assertThat(errorText).contains("Quantity must be positive");
    }

    @Test
    void shouldNotPlaceOrderWhenSKUDoesNotExist() {
        // Given
        shopUiPage.navigate(getShopUiBaseUrl());
        shopUiPage.locator("a[href='/shop']").click();

        // When
        shopUiPage.locator("[aria-label=\"SKU\"]").fill("INVALID-SKU");
        shopUiPage.locator("[aria-label=\"Quantity\"]").fill("5");
        shopUiPage.locator("[aria-label=\"Country\"]").fill(COUNTRY);
        shopUiPage.locator("[aria-label=\"Place Order\"]").click();

        // Then - Verify error message is displayed
        var errorAlert = shopUiPage.locator("[role='alert']");
        assertThat(errorAlert.isVisible()).isTrue();
        var errorText = errorAlert.textContent();
        assertThat(errorText).contains("The request contains one or more validation errors");
        assertThat(errorText).contains("sku");
        assertThat(errorText).contains("Product does not exist for SKU: INVALID-SKU");
    }

    @Test
    void shouldNotPlaceOrderWhenSKUIsMissing() {
        // Given
        shopUiPage.navigate(getShopUiBaseUrl());
        shopUiPage.locator("a[href='/shop']").click();

        // When
        shopUiPage.locator("[aria-label=\"SKU\"]").fill("");
        shopUiPage.locator("[aria-label=\"Quantity\"]").fill("5");
        shopUiPage.locator("[aria-label=\"Country\"]").fill(COUNTRY);
        shopUiPage.locator("[aria-label=\"Place Order\"]").click();

        // Then - Verify error message is displayed
        var errorAlert = shopUiPage.locator("[role='alert']");
        assertThat(errorAlert.isVisible()).isTrue();
        var errorText = errorAlert.textContent();
        assertThat(errorText).contains("The request contains one or more validation errors");
        assertThat(errorText).contains("sku");
        assertThat(errorText).contains("SKU must not be empty");
    }
}
