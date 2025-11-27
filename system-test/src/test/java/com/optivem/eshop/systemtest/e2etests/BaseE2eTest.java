package com.optivem.eshop.systemtest.e2etests;

import com.optivem.eshop.systemtest.core.clients.commons.Closer;
import com.optivem.eshop.systemtest.core.commons.dtos.enums.OrderStatus;
import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.external.ErpApiDriver;
import com.optivem.eshop.systemtest.core.drivers.external.TaxApiDriver;
import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BaseE2eTest {

    protected ShopDriver shopDriver;
    protected ErpApiDriver erpApiDriver;
    protected TaxApiDriver taxApiDriver;

    @BeforeEach
    void setUp() {
        shopDriver = createDriver();
        erpApiDriver = DriverFactory.createErpApiDriver();
        taxApiDriver = DriverFactory.createTaxApiDriver();
    }

    protected abstract ShopDriver createDriver();

    @AfterEach
    void tearDown() {
        Closer.close(shopDriver);
        Closer.close(erpApiDriver);
        Closer.close(taxApiDriver);
    }

    @Test
    void shouldPlaceOrderAndCalculateOriginalPrice() {
        var sku = "ABC-" + UUID.randomUUID();
        erpApiDriver.createProduct(sku, "20.00");
        var placeOrderResult = shopDriver.placeOrder(sku, "5", "US");
        assertThat(placeOrderResult.isSuccess()).isTrue();

        var orderNumber = placeOrderResult.getValue().getOrderNumber();

        assertThat(orderNumber).startsWith("ORD-");

        var viewOrderResult = shopDriver.viewOrder(orderNumber);
        assertThat(viewOrderResult.isSuccess()).isTrue();

        var viewOrderResponse = viewOrderResult.getValue();
        assertThat(viewOrderResponse.getOrderNumber()).isEqualTo(orderNumber);
        assertThat(viewOrderResponse.getSku()).isEqualTo(sku);
        assertThat(viewOrderResponse.getQuantity()).isEqualTo(5);
        assertThat(viewOrderResponse.getCountry()).isEqualTo("US");
        assertThat(viewOrderResponse.getUnitPrice()).isEqualTo(new BigDecimal("20.00"));
        assertThat(viewOrderResponse.getOriginalPrice()).isEqualTo(new BigDecimal("100.00"));
        assertThat(viewOrderResponse.getStatus()).isEqualTo(OrderStatus.PLACED);

        var discountRate = viewOrderResponse.getDiscountRate();
        var discountAmount = viewOrderResponse.getDiscountAmount();
        var subtotalPrice = viewOrderResponse.getSubtotalPrice();

        assertThat(discountRate)
                .isGreaterThanOrEqualTo(BigDecimal.ZERO);

        assertThat(discountAmount)
                .isGreaterThanOrEqualTo(BigDecimal.ZERO);

        assertThat(subtotalPrice)
                .isGreaterThan(BigDecimal.ZERO);


        var taxRate = viewOrderResponse.getTaxRate();
        var taxAmount = viewOrderResponse.getTaxAmount();
        var totalPrice = viewOrderResponse.getTotalPrice();

        assertThat(taxRate)
                .withFailMessage("Tax rate should be non-negative")
                .isGreaterThanOrEqualTo(BigDecimal.ZERO);

        assertThat(taxAmount)
                .withFailMessage("Tax amount should be non-negative")
                .isGreaterThanOrEqualTo(BigDecimal.ZERO);

        assertThat(totalPrice)
                .withFailMessage("Total price should be positive")
                .isGreaterThan(BigDecimal.ZERO);
    }


    @Test
    void shouldCancelOrder() {
        var sku = "XYZ-" + UUID.randomUUID();
        erpApiDriver.createProduct(sku, "50.00");
        var placeOrderResult = shopDriver.placeOrder(sku, "2", "US");
        assertThat(placeOrderResult.isSuccess()).isTrue();

        var orderNumber = placeOrderResult.getValue().getOrderNumber();
        var cancelOrderResult = shopDriver.cancelOrder(orderNumber);
        assertThat(cancelOrderResult.isSuccess()).isTrue();

        var viewOrderResult = shopDriver.viewOrder(orderNumber);
        assertThat(viewOrderResult.isSuccess()).isTrue();

        var viewOrderResponse = viewOrderResult.getValue();
        assertThat(viewOrderResponse.getOrderNumber()).isEqualTo(orderNumber);
        assertThat(viewOrderResponse.getSku()).isEqualTo(sku);
        assertThat(viewOrderResponse.getQuantity()).isEqualTo(2);
        assertThat(viewOrderResponse.getCountry()).isEqualTo("US");
        assertThat(viewOrderResponse.getUnitPrice()).isEqualTo(new BigDecimal("50.00"));
        assertThat(viewOrderResponse.getOriginalPrice()).isEqualTo(new BigDecimal("100.00"));
        assertThat(viewOrderResponse.getStatus()).isEqualTo(OrderStatus.CANCELLED);
    }

    @Test
    void shouldRejectOrderWithNonExistentSku() {
        var result = shopDriver.placeOrder("NON-EXISTENT-SKU-12345", "5", "US");
        assertThat(result.isFailure()).isTrue();
        assertThat(result.getErrors()).contains("Product does not exist for SKU: NON-EXISTENT-SKU-12345");
    }

    @Test
    void shouldNotBeAbleToViewNonExistentOrder() {
        var result = shopDriver.viewOrder("NON-EXISTENT-ORDER-12345");
        assertThat(result.isFailure()).isTrue();
        assertThat(result.getErrors()).contains("Order NON-EXISTENT-ORDER-12345 does not exist.");
    }

    @Test
    void shouldRejectOrderWithNegativeQuantity() {
        var sku = "DEF-" + UUID.randomUUID();
        erpApiDriver.createProduct(sku, "30.00");
        var result = shopDriver.placeOrder(sku, "-3", "US");
        assertThat(result.isFailure()).isTrue();
        assertThat(result.getErrors()).contains("Quantity must be positive");
    }

    @Test
    void shouldRejectOrderWithZeroQuantity() {
        var sku = "GHI-" + UUID.randomUUID();
        erpApiDriver.createProduct(sku, "40.00");
        var result = shopDriver.placeOrder(sku, "0", "US");
        assertThat(result.isFailure()).isTrue();
        assertThat(result.getErrors()).contains("Quantity must be positive");
    }


    private static Stream<Arguments> provideEmptySkuValues() {
        return Stream.of(
                Arguments.of(""),      // Empty string
                Arguments.of("   ")    // Whitespace string
        );
    }

    @ParameterizedTest
    @MethodSource("provideEmptySkuValues")
    void shouldRejectOrderWithEmptySku(String sku) {
        var result = shopDriver.placeOrder(sku, "5", "US");
        assertThat(result.isFailure()).isTrue();
        assertThat(result.getErrors()).contains("SKU must not be empty");
    }

    private static Stream<Arguments> provideEmptyQuantityValues() {
        return Stream.of(
                Arguments.of(""),      // Empty string
                Arguments.of("   ")    // Whitespace string
        );
    }

    @ParameterizedTest
    @MethodSource("provideEmptyQuantityValues")
    void shouldRejectOrderWithEmptyQuantity(String emptyQuantity) {
        var result = shopDriver.placeOrder("some-sku", emptyQuantity, "US");
        assertThat(result.isFailure()).isTrue();
        assertThat(result.getErrors()).contains("Quantity must not be empty");
    }

    private static Stream<Arguments> provideNonIntegerQuantityValues() {
        return Stream.of(
                Arguments.of("3.5"),   // Decimal value
                Arguments.of("lala")   // String value
        );
    }

    @ParameterizedTest
    @MethodSource("provideNonIntegerQuantityValues")
    void shouldRejectOrderWithNonIntegerQuantity(String nonIntegerQuantity) {
        var result = shopDriver.placeOrder("some-sku", nonIntegerQuantity, "US");
        assertThat(result.isFailure()).isTrue();
        assertThat(result.getErrors()).contains("Quantity must be an integer");
    }

    private static Stream<Arguments> provideEmptyCountryValues() {
        return Stream.of(
                Arguments.of(""),      // Empty string
                Arguments.of("   ")    // Whitespace string
        );
    }

    @ParameterizedTest
    @MethodSource("provideEmptyCountryValues")
    void shouldRejectOrderWithEmptyCountry(String emptyCountry) {
        var result = shopDriver.placeOrder("some-sku", "5", emptyCountry);
        assertThat(result.isFailure()).isTrue();
        assertThat(result.getErrors()).contains("Country must not be empty");
    }

    @Test
    void shouldRejectOrderWithUnsupportedCountry() {
        var sku = "JKL-" + UUID.randomUUID();
        erpApiDriver.createProduct(sku, "25.00");
        var result = shopDriver.placeOrder(sku, "3", "XX");
        assertThat(result.isFailure()).isTrue();
        assertThat(result.getErrors()).contains("Country does not exist: XX");
    }
}

