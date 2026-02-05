package com.optivem.eshop.systemtest.e2etests.v2;

import com.optivem.eshop.systemtest.base.v2.BaseClientTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.optivem.commons.util.ResultAssert.assertThatResult;
import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.COUNTRY;
import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.SKU;
import static org.assertj.core.api.Assertions.assertThat;

class PlaceOrderNegativeUiTest extends BaseClientTest {

    @BeforeEach
    void setUp() {
        setUpShopUiClient();
        setUpExternalClients();
    }

    @Test
    void shouldNotPlaceOrderWhenQuantityIsZero() {
        // Given
        var homePage = shopUiClient.openHomePage();
        var newOrderPage = homePage.clickNewOrder();

        // When
        newOrderPage.inputSku(SKU);
        newOrderPage.inputQuantity("0");
        newOrderPage.inputCountry(COUNTRY);
        newOrderPage.clickPlaceOrder();

        var result = newOrderPage.getResult();

        // Then
        assertThatResult(result).isFailure();
        var error = result.getError();
        assertThat(error.getMessage()).contains("quantity");
    }

    @Test
    void shouldNotPlaceOrderWhenSKUDoesNotExist() {
        // Given
        var homePage = shopUiClient.openHomePage();
        var newOrderPage = homePage.clickNewOrder();

        // When
        newOrderPage.inputSku("INVALID-SKU");
        newOrderPage.inputQuantity("5");
        newOrderPage.inputCountry(COUNTRY);
        newOrderPage.clickPlaceOrder();

        var result = newOrderPage.getResult();

        // Then
        assertThatResult(result).isFailure();
        var error = result.getError();
        assertThat(error.getMessage()).contains("SKU");
    }

    @Test
    void shouldNotPlaceOrderWhenSKUIsMissing() {
        // Given
        var homePage = shopUiClient.openHomePage();
        var newOrderPage = homePage.clickNewOrder();

        // When
        newOrderPage.inputSku("");
        newOrderPage.inputQuantity("5");
        newOrderPage.inputCountry(COUNTRY);
        newOrderPage.clickPlaceOrder();

        var result = newOrderPage.getResult();

        // Then
        assertThatResult(result).isFailure();
        var error = result.getError();
        assertThat(error.getMessage()).contains("SKU");
    }
}
