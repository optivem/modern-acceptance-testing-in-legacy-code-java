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
    void shouldCalculateOriginalOrderPriceAndViewOtherOrderDetails() {
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






//    @Test
//    void shouldRetrieveOrderHistory() {
//        var orderNumber = shopUiDriver.placeOrder("SAM-2020", "3", "US");
//
//        var orderHistoryPage = shopUiDriver.viewOrderDetails(orderNumber);
//
//        var displayOrderNumber = orderHistoryPage.getOrderNumber();
//        var displayProductId = orderHistoryPage.getProductId();
//        var displayCountry = orderHistoryPage.getCountry();
//        var displayQuantity = orderHistoryPage.getQuantity();
//        var displayUnitPrice = orderHistoryPage.getUnitPrice();
//        var displayOriginalPrice = orderHistoryPage.getOriginalPrice();
//        var displayDiscountRate = orderHistoryPage.getDiscountRate();
//        var displayDiscountAmount = orderHistoryPage.getDiscountAmount();
//        var displaySubtotalPrice = orderHistoryPage.getSubtotalPrice();
//        var displayTaxRate = orderHistoryPage.getTaxRate();
//        var displayTaxAmount = orderHistoryPage.getTaxAmount();
//        var displayTotalPrice = orderHistoryPage.getTotalPrice();
//

//        assertEquals("US", displayCountry, "Should display country US");
//        assertEquals("3", displayQuantity, "Should display quantity 3");
//        assertEquals("$499.99", displayUnitPrice, "Should display unit price $499.99");
//        assertEquals("$1499.97", displayOriginalPrice, "Should display original price $1499.97 (3 × $499.99)");
//
//        assertTrue(displayDiscountRate.endsWith("%"), "Should display discount rate with % symbol");
//        assertTrue(displayDiscountAmount.startsWith("$"), "Should display discount amount with $ symbol");
//        assertTrue(displaySubtotalPrice.startsWith("$"), "Should display subtotal price with $ symbol");
//        assertTrue(displayTaxRate.endsWith("%"), "Should display tax rate with % symbol");
//        assertTrue(displayTaxAmount.startsWith("$"), "Should display tax amount with $ symbol");
//        assertTrue(displayTotalPrice.startsWith("$"), "Should display total price with $ symbol");
//    }
}

