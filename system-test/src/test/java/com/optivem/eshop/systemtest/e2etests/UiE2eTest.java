package com.optivem.eshop.systemtest.e2etests;

import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.drivers.system.ShopUiDriver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UiE2eTest extends BaseE2eTest {

    @Override
    protected ShopDriver createDriver(DriverFactory driverFactory) {
        return driverFactory.createShopUiDriver();
    }




//
//    private static Stream<Arguments> provideEmptySkuValues() {
//        return Stream.of(
//                Arguments.of(""),      // Empty string
//                Arguments.of("   ")    // Whitespace string
//        );
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideEmptySkuValues")
//    void shouldRejectOrderWithEmptySku(String skuValue) {
//        var homePage = shopUiDriver.openHomePage();
//        var newOrderPage = homePage.clickNewOrder();
//
//        newOrderPage.inputProductId(skuValue);
//        newOrderPage.inputQuantity("5");
//        newOrderPage.clickPlaceOrder();
//
//        var errorMessageText = newOrderPage.readConfirmationMessageText();
//
//        assertTrue(errorMessageText.contains("SKU must not be empty"),
//                "Error message should be 'SKU must not be empty' for SKU: '" + skuValue + "'. Actual: " + errorMessageText);
//    }
//
//    private static Stream<Arguments> provideEmptyQuantityValues() {
//        return Stream.of(
//                Arguments.of(""),      // Empty string
//                Arguments.of("   ")    // Whitespace string
//        );
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideEmptyQuantityValues")
//    void shouldRejectOrderWithEmptyQuantity(String quantityValue) {
//        var baseSku = "AUTO-EQ-500";
//        var unitPrice = new BigDecimal("175.00");
//
//        var sku = erpApiDriver.createProduct(baseSku, unitPrice);
//
//        var homePage = shopUiDriver.openHomePage();
//        var newOrderPage = homePage.clickNewOrder();
//
//        newOrderPage.inputProductId(sku);
//        newOrderPage.inputQuantity(quantityValue);
//        newOrderPage.clickPlaceOrder();
//
//        var errorMessageText = newOrderPage.readConfirmationMessageText();
//
//        assertTrue(errorMessageText.contains("Quantity must be an integer") || errorMessageText.contains("Quantity must be greater than 0"),
//                "Error message should indicate quantity validation error for quantity: '" + quantityValue + "'. Actual: " + errorMessageText);
//    }
//
//    private static Stream<Arguments> provideInvalidQuantityValues() {
//        return Stream.of(
//                Arguments.of("3.5"),   // Decimal value
//                Arguments.of("lala")   // String value
//        );
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideInvalidQuantityValues")
//    void shouldRejectOrderWithNonIntegerQuantity(String quantityValue) {
//        var homePage = shopUiDriver.openHomePage();
//        var newOrderPage = homePage.clickNewOrder();
//
//        newOrderPage.inputProductId("HP-15");
//        newOrderPage.inputQuantity(quantityValue);
//        newOrderPage.clickPlaceOrder();
//
//        var errorMessageText = newOrderPage.readConfirmationMessageText();
//
//        assertTrue(errorMessageText.contains("Quantity must be an integer"),
//                "Error message should be 'Quantity must be an integer' for quantity: " + quantityValue + ". Actual: " + errorMessageText);
//    }
//
//    private static Stream<Arguments> provideEmptyCountryValues() {
//        return Stream.of(
//                Arguments.of(""),      // Empty string
//                Arguments.of("   ")    // Whitespace string
//        );
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideEmptyCountryValues")
//    void shouldRejectOrderWithEmptyCountry(String countryValue) {
//        var baseSku = "AUTO-EC-700";
//        var unitPrice = new BigDecimal("245.50");
//
//        var sku = erpApiDriver.createProduct(baseSku, unitPrice);
//
//        var homePage = shopUiDriver.openHomePage();
//        var newOrderPage = homePage.clickNewOrder();
//
//        newOrderPage.inputProductId(sku);
//        newOrderPage.inputQuantity("5");
//        newOrderPage.inputCountry(countryValue);
//        newOrderPage.clickPlaceOrder();
//
//        var errorMessageText = newOrderPage.readConfirmationMessageText();
//
//        assertTrue(errorMessageText.contains("Country must not be empty"),
//                "Error message should be 'Country must not be empty' for country: '" + countryValue + "'. Actual: " + errorMessageText);
//    }
}

