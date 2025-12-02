package com.optivem.eshop.systemtest.e2etests;

import com.optivem.atdd.commons.channels.Channel;
import com.optivem.atdd.commons.channels.ChannelExtension;
import com.optivem.eshop.systemtest.core.drivers.commons.clients.Closer;
import com.optivem.eshop.systemtest.core.drivers.system.commons.enums.OrderStatus;
import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.eshop.systemtest.core.drivers.external.tax.api.TaxApiDriver;
import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Stream;

import static com.optivem.eshop.systemtest.core.drivers.commons.ResultAssert.assertThatResult;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ChannelExtension.class)
public class E2eTest {

    protected ShopDriver shopDriver;
    protected ErpApiDriver erpApiDriver;
    protected TaxApiDriver taxApiDriver;

    @BeforeEach
    void setUp() {
        shopDriver = DriverFactory.createShopDriver();
        erpApiDriver = DriverFactory.createErpApiDriver();
        taxApiDriver = DriverFactory.createTaxApiDriver();
    }

    @AfterEach
    void tearDown() {
        Closer.close(shopDriver);
        Closer.close(erpApiDriver);
        Closer.close(taxApiDriver);
    }

    @Channel({"UI", "API"})
    @TestTemplate
    void shouldPlaceOrderAndCalculateOriginalPrice() {
        var sku = "ABC-" + UUID.randomUUID();
        var createProductResult = erpApiDriver.createProduct(sku, "20.00");
        assertThatResult(createProductResult).isSuccess();

        var placeOrderResult = shopDriver.placeOrder(sku, "5", "US");
        assertThatResult(placeOrderResult).isSuccess();

        var orderNumber = placeOrderResult.getValue().getOrderNumber();

        assertThat(orderNumber).startsWith("ORD-");

        var viewOrderResult = shopDriver.viewOrder(orderNumber);
        assertThatResult(viewOrderResult).isSuccess();

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


    @Channel({"UI", "API"})
    @TestTemplate
    void shouldCancelOrder() {
        var sku = "XYZ-" + UUID.randomUUID();
        var createProductResult = erpApiDriver.createProduct(sku, "50.00");
        assertThatResult(createProductResult).isSuccess();

        var placeOrderResult = shopDriver.placeOrder(sku, "2", "US");
        assertThatResult(placeOrderResult).isSuccess();

        var orderNumber = placeOrderResult.getValue().getOrderNumber();
        var cancelOrderResult = shopDriver.cancelOrder(orderNumber);
        assertThatResult(cancelOrderResult).isSuccess();

        var viewOrderResult = shopDriver.viewOrder(orderNumber);
        assertThatResult(viewOrderResult).isSuccess();

        var viewOrderResponse = viewOrderResult.getValue();
        assertThat(viewOrderResponse.getOrderNumber()).isEqualTo(orderNumber);
        assertThat(viewOrderResponse.getSku()).isEqualTo(sku);
        assertThat(viewOrderResponse.getQuantity()).isEqualTo(2);
        assertThat(viewOrderResponse.getCountry()).isEqualTo("US");
        assertThat(viewOrderResponse.getUnitPrice()).isEqualTo(new BigDecimal("50.00"));
        assertThat(viewOrderResponse.getOriginalPrice()).isEqualTo(new BigDecimal("100.00"));
        assertThat(viewOrderResponse.getStatus()).isEqualTo(OrderStatus.CANCELLED);
    }

    @Channel({"UI", "API"})
    @TestTemplate
    void shouldRejectOrderWithNonExistentSku() {
        var result = shopDriver.placeOrder("NON-EXISTENT-SKU-12345", "5", "US");
        assertThatResult(result).isFailure("Product does not exist for SKU: NON-EXISTENT-SKU-12345");
    }

    @Channel({"UI", "API"})
    @TestTemplate
    void shouldNotBeAbleToViewNonExistentOrder() {
        var result = shopDriver.viewOrder("NON-EXISTENT-ORDER-12345");
        assertThatResult(result).isFailure("Order NON-EXISTENT-ORDER-12345 does not exist.");
    }

    @Channel({"UI", "API"})
    @TestTemplate
    void shouldRejectOrderWithNegativeQuantity() {
        var sku = "DEF-" + UUID.randomUUID();
        var createProductResult = erpApiDriver.createProduct(sku, "30.00");
        assertThatResult(createProductResult).isSuccess();

        var result = shopDriver.placeOrder(sku, "-3", "US");
        assertThatResult(result).isFailure("Quantity must be positive");
    }

    @Channel({"UI", "API"})
    @TestTemplate
    void shouldRejectOrderWithZeroQuantity() {
        var sku = "GHI-" + UUID.randomUUID();
        var createProductResult = erpApiDriver.createProduct(sku, "40.00");
        assertThatResult(createProductResult).isSuccess();

        var result = shopDriver.placeOrder(sku, "0", "US");
        assertThatResult(result).isFailure("Quantity must be positive");
    }


    private static Stream<Arguments> provideEmptySkuValues() {
        return Stream.of(
                Arguments.of(""),      // Empty string
                Arguments.of("   ")    // Whitespace string
        );
    }

    @Channel({"UI", "API"})
    @TestTemplate
    @MethodSource("provideEmptySkuValues")
    void shouldRejectOrderWithEmptySku(String sku) {
        var result = shopDriver.placeOrder(sku, "5", "US");
        assertThatResult(result).isFailure("SKU must not be empty");
    }

    private static Stream<Arguments> provideEmptyQuantityValues() {
        return Stream.of(
                Arguments.of(""),      // Empty string
                Arguments.of("   ")    // Whitespace string
        );
    }

    @Channel({"UI", "API"})
    @TestTemplate
    @MethodSource("provideEmptyQuantityValues")
    void shouldRejectOrderWithEmptyQuantity(String emptyQuantity) {
        var result = shopDriver.placeOrder("some-sku", emptyQuantity, "US");
        assertThatResult(result).isFailure("Quantity must not be empty");
    }

    private static Stream<Arguments> provideNonIntegerQuantityValues() {
        return Stream.of(
                Arguments.of("3.5"),   // Decimal value
                Arguments.of("lala")   // String value
        );
    }

    @Channel({"UI", "API"})
    @TestTemplate
    @MethodSource("provideNonIntegerQuantityValues")
    void shouldRejectOrderWithNonIntegerQuantity(String nonIntegerQuantity) {
        var result = shopDriver.placeOrder("some-sku", nonIntegerQuantity, "US");
        assertThatResult(result).isFailure("Quantity must be an integer");
    }

    private static Stream<Arguments> provideEmptyCountryValues() {
        return Stream.of(
                Arguments.of(""),      // Empty string
                Arguments.of("   ")    // Whitespace string
        );
    }

    @Channel({"UI", "API"})
    @TestTemplate
    @MethodSource("provideEmptyCountryValues")
    void shouldRejectOrderWithEmptyCountry(String emptyCountry) {
        var result = shopDriver.placeOrder("some-sku", "5", emptyCountry);
        assertThatResult(result).isFailure("Country must not be empty");
    }

    @Channel({"UI", "API"})
    @TestTemplate
    void shouldRejectOrderWithUnsupportedCountry() {
        var sku = "JKL-" + UUID.randomUUID();
        var createProductResult = erpApiDriver.createProduct(sku, "25.00");
        assertThatResult(createProductResult).isSuccess();

        var result = shopDriver.placeOrder(sku, "3", "XX");
        assertThatResult(result).isFailure("Country does not exist: XX");
    }

    @Channel({"API"})
    @TestTemplate
    void shouldRejectOrderWithNullQuantity() {
        var result = shopDriver.placeOrder("some-sku", null, "US");
        assertThatResult(result).isFailure("Quantity must not be empty");
    }

    @Channel({"API"})
    @TestTemplate
    void shouldRejectOrderWithNullSku() {
        var result = shopDriver.placeOrder(null, "5", "US");
        assertThatResult(result).isFailure("SKU must not be empty");
    }

    @Channel({"API"})
    @TestTemplate
    void shouldRejectOrderWithNullCountry() {
        var result = shopDriver.placeOrder("some-sku", "5", null);
        assertThatResult(result).isFailure("Country must not be empty");
    }

    @Channel({"API"})
    @TestTemplate
    void shouldNotCancelNonExistentOrder() {
        var result = shopDriver.cancelOrder("NON-EXISTENT-ORDER-99999");
        assertThatResult(result).isFailure("Order NON-EXISTENT-ORDER-99999 does not exist.");
    }

    @Channel({"API"})
    @TestTemplate
    void shouldNotCancelAlreadyCancelledOrder() {
        var sku = "MNO-" + UUID.randomUUID();
        var createProductResult = erpApiDriver.createProduct(sku, "35.00");
        assertThatResult(createProductResult).isSuccess();

        var placeOrderResult = shopDriver.placeOrder(sku, "3", "US");
        assertThatResult(placeOrderResult).isSuccess();

        var orderNumber = placeOrderResult.getValue().getOrderNumber();

        // Cancel the order first time - should succeed
        var firstCancelResult = shopDriver.cancelOrder(orderNumber);
        assertThatResult(firstCancelResult).isSuccess();

        // Try to cancel the same order again - should fail
        var secondCancelResult = shopDriver.cancelOrder(orderNumber);
        assertThatResult(secondCancelResult).isFailure("Order has already been cancelled");
    }
}
