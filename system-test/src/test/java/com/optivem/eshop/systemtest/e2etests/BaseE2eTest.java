package com.optivem.eshop.systemtest.e2etests;

import com.optivem.eshop.systemtest.core.drivers.DriverCloser;
import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.external.ErpApiDriver;
import com.optivem.eshop.systemtest.core.drivers.external.TaxApiDriver;
import com.optivem.eshop.systemtest.core.drivers.system.ShopApiDriver;
import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.drivers.system.ShopUiDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class BaseE2eTest {

    private ShopDriver shopDriver;
    private ErpApiDriver erpApiDriver;
    private TaxApiDriver taxApiDriver;

    @BeforeEach
    void setUp() {
        var driverFactory = new DriverFactory();
        shopDriver = createDriver(driverFactory);
        erpApiDriver = driverFactory.createErpApiDriver();
        taxApiDriver = driverFactory.createTaxApiDriver();
    }

    protected abstract ShopDriver createDriver(DriverFactory driverFactory);

    @AfterEach
    void tearDown() {
        DriverCloser.close(shopDriver);
        DriverCloser.close(erpApiDriver);
        DriverCloser.close(taxApiDriver);
    }

    @Test
    void shouldPlaceOrderAndCalculateOriginalPrice() {
        var sku = "ABC-" + UUID.randomUUID();
        erpApiDriver.createProduct(sku, "20.00");
        var result = shopDriver.placeOrder(sku, "5", "US");
        assertTrue(result.isSuccess());

        var orderNumber = result.getValue();
        shopDriver.confirmOrderNumberGeneratedWithPrefix(orderNumber, "ORD-");
        shopDriver.confirmOrderDetails(orderNumber, Optional.of(sku), Optional.of("5"), Optional.of("US"),
                Optional.of("20.00"), Optional.of("100.00"), Optional.of("PLACED"));

        shopDriver.confirmSubtotalPricePositive(orderNumber);
        shopDriver.confirmTotalPricePositive(orderNumber);
    }

    @Test
    void shouldCancelOrder() {
        var sku = "XYZ-" + UUID.randomUUID();
        erpApiDriver.createProduct(sku, "50.00");
        var result = shopDriver.placeOrder(sku, "2", "US");
        assertTrue(result.isSuccess());

        var orderNumber = result.getValue();
        shopDriver.confirmOrderDetails(orderNumber, Optional.of(sku), Optional.of("2"), Optional.of("US"),
                Optional.of("50.00"), Optional.of("100.00"), Optional.of("PLACED"));

        shopDriver.cancelOrder(orderNumber);
        shopDriver.confirmOrderCancelled(orderNumber);

        shopDriver.confirmOrderDetails(orderNumber, Optional.of(sku), Optional.of("2"), Optional.of("US"),
                Optional.of("50.00"), Optional.of("100.00"), Optional.of("CANCELLED"));
    }

    @Test
    void shouldRejectOrderWithNonExistentSku() {
        var result = shopDriver.placeOrder("NON-EXISTENT-SKU-12345", "5", "US");
        assertTrue(result.isFailure());
        assertEquals("Product does not exist for SKU: NON-EXISTENT-SKU-12345", result.getError());
    }

    @Test
    void shouldRejectOrderWithNegativeQuantity() {
        var sku = "DEF-" + UUID.randomUUID();
        erpApiDriver.createProduct(sku, "30.00");
        var result = shopDriver.placeOrder(sku, "-3", "US");
        assertTrue(result.isFailure());
        assertEquals("Quantity must be positive", result.getError());
    }


//
//    @Test
//    void shouldRejectOrderWithNegativeQuantity() {
//        var homePage = shopUiDriver.openHomePage();
//        var newOrderPage = homePage.clickNewOrder();
//
//        newOrderPage.inputProductId("HP-15");
//        newOrderPage.inputQuantity("-5");
//        newOrderPage.clickPlaceOrder();
//
//        var errorMessageText = newOrderPage.readConfirmationMessageText();
//
//        assertTrue(errorMessageText.contains("Quantity must be positive"),
//                "Error message should indicate quantity must be positive. Actual: " + errorMessageText);
//    }
}

