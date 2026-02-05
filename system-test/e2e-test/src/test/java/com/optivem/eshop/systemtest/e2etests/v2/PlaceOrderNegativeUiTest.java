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
        assertThat(error.getMessage()).isEqualTo("The request contains one or more validation errors");
        assertThat(error.getFields()).anySatisfy(field -> {
            assertThat(field.getField()).isEqualTo("quantity");
            assertThat(field.getMessage()).isEqualTo("Quantity must be positive");
        });
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
        assertThat(error.getMessage()).isEqualTo("The request contains one or more validation errors");
        assertThat(error.getFields()).anySatisfy(field -> {
            assertThat(field.getField()).isEqualTo("sku");
            assertThat(field.getMessage()).isEqualTo("Product does not exist for SKU: INVALID-SKU");
        });
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
        assertThat(error.getMessage()).isEqualTo("The request contains one or more validation errors");
        assertThat(error.getFields()).anySatisfy(field -> {
            assertThat(field.getField()).isEqualTo("sku");
            assertThat(field.getMessage()).isEqualTo("SKU must not be empty");
        });
    }
}
