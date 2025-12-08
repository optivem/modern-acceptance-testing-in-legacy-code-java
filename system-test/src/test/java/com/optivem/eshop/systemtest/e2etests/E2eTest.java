package com.optivem.eshop.systemtest.e2etests;

import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.eshop.systemtest.core.dsl.erp.ErpDsl;
import com.optivem.eshop.systemtest.core.dsl.shop.ShopDsl;
import com.optivem.eshop.systemtest.core.dsl.tax.TaxDsl;
import com.optivem.eshop.systemtest.e2etests.providers.EmptyQuantityArgumentsProvider;
import com.optivem.testing.channels.Channel;
import com.optivem.testing.channels.ChannelExtension;
import com.optivem.eshop.systemtest.core.channels.ChannelType;
import com.optivem.testing.channels.TestDataSource;
import com.optivem.lang.Closer;
import com.optivem.eshop.systemtest.core.drivers.system.commons.enums.OrderStatus;
import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.eshop.systemtest.core.drivers.external.tax.api.TaxApiDriver;
import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Stream;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ChannelExtension.class)
public class E2eTest {

    private ShopDriver shopDriver;
    private ErpApiDriver erpApiDriver;
    private TaxApiDriver taxApiDriver;

    private ShopDsl shop;
    private ErpDsl erp;
    private TaxDsl tax;

    @BeforeEach
    void setUp() {
        shopDriver = DriverFactory.createShopDriver();
        erpApiDriver = DriverFactory.createErpApiDriver();
        taxApiDriver = DriverFactory.createTaxApiDriver();

        var context = new DslContext();
        shop = new ShopDsl(shopDriver, context);
        erp = new ErpDsl(erpApiDriver, context);
        tax = new TaxDsl(taxApiDriver, context);
    }

    @AfterEach
    void tearDown() {
        Closer.close(shopDriver);
        Closer.close(erpApiDriver);
        Closer.close(taxApiDriver);
    }

    private static final String ORDER_NUMBER = "order-number";
    private static final String SKU = "sku";

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldPlaceOrderAndCalculateOriginalPrice() {
        erp.createProduct()
                .sku(SKU)
                .unitPrice("20.00")
                .execute()
                .shouldSucceed();

        shop.placeOrder()
                .orderNumber(ORDER_NUMBER)
                .sku(SKU)
                .quantity("5")
                .country("US")
                .execute()
                .shouldSucceed()
                .shouldHaveOrderNumber(ORDER_NUMBER)
                .shouldHaveOrderNumberStartingWith("ORD-");

        shop.viewOrder()
                .orderNumber(ORDER_NUMBER)
                .execute()
                .shouldSucceed()
                .shouldHaveOrderNumber(ORDER_NUMBER)
                .shouldHaveSku(SKU)
                .shouldHaveQuantity(5)
                .shouldHaveCountry("US")
                .shouldHaveUnitPrice("20.00")
                .shouldHaveOriginalPrice("100.00")
                .shouldHaveStatus(OrderStatus.PLACED)
                .shouldHaveDiscountRateGreaterThanOrEqualToZero()
                .shouldHaveDiscountAmountGreaterThanOrEqualToZero()
                .shouldHaveSubtotalPriceGreaterThanZero()
                .shouldHaveTaxRateGreaterThanOrEqualToZero()
                .shouldHaveTaxAmountGreaterThanOrEqualToZero()
                .shouldHaveTotalPriceGreaterThanZero();
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldCancelOrder() {
        var sku = "XYZ-" + UUID.randomUUID();
        var createProductResult = erpApiDriver.createProduct(sku, "50.00");
        assertThatResult(createProductResult).isSuccess();

        var placeOrderRequest = PlaceOrderRequest.builder()
                .sku(sku)
                .quantity("2")
                .country("US")
                .build();

        var placeOrderResult = shopDriver.placeOrder(placeOrderRequest);
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

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldRejectOrderWithNonExistentSku() {
        var request = PlaceOrderRequest.builder()
                .sku("NON-EXISTENT-SKU-12345")
                .quantity("5")
                .country("US")
                .build();

        var result = shopDriver.placeOrder(request);
        assertThatResult(result).isFailure("Product does not exist for SKU: NON-EXISTENT-SKU-12345");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldRejectOrderWithNonExistentSkuWithDsl() {

        shop.placeOrder()
                .sku("NON-EXISTENT-SKU-12345")
                .quantity("5")
                .country("US")
                .execute()
                .shouldFail()
                .shouldHaveErrorMessage("Product does not exist for SKU: NON-EXISTENT-SKU-12345");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldNotBeAbleToViewNonExistentOrder() {
        var result = shopDriver.viewOrder("NON-EXISTENT-ORDER-12345");
        assertThatResult(result).isFailure("Order NON-EXISTENT-ORDER-12345 does not exist.");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldRejectOrderWithNegativeQuantity() {
        var sku = "DEF-" + UUID.randomUUID();
        var createProductResult = erpApiDriver.createProduct(sku, "30.00");
        assertThatResult(createProductResult).isSuccess();

        var request = PlaceOrderRequest.builder()
                .sku(sku)
                .quantity("-3")
                .country("US")
                .build();

        var result = shopDriver.placeOrder(request);
        assertThatResult(result).isFailure("Quantity must be positive");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldRejectOrderWithZeroQuantity() {
        var sku = "GHI-" + UUID.randomUUID();
        var createProductResult = erpApiDriver.createProduct(sku, "40.00");
        assertThatResult(createProductResult).isSuccess();

        var request = PlaceOrderRequest.builder()
                .sku(sku)
                .quantity("0")
                .country("US")
                .build();

        var result = shopDriver.placeOrder(request);
        assertThatResult(result).isFailure("Quantity must be positive");
    }


    private static Stream<Arguments> provideEmptySkuValues() {
        return Stream.of(
                Arguments.of(""),      // Empty string
                Arguments.of("   ")    // Whitespace string
        );
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @TestDataSource("")
    @TestDataSource("   ")
    void shouldRejectOrderWithEmptySku(String sku) {
        var request = PlaceOrderRequest.builder()
                .sku(sku)
                .quantity("5")
                .country("US")
                .build();

        var result = shopDriver.placeOrder(request);
        assertThatResult(result).isFailure("SKU must not be empty");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @ArgumentsSource(EmptyQuantityArgumentsProvider.class)
    void shouldRejectOrderWithEmptyQuantity(String emptyQuantity, String expectedErrorMessage) {
        var request = PlaceOrderRequest.builder()
                .sku("some-sku")
                .quantity(emptyQuantity)
                .country("US")
                .build();

        var result = shopDriver.placeOrder(request);
        assertThatResult(result).isFailure(expectedErrorMessage);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @TestDataSource("3.5")
    @TestDataSource("lala")
    void shouldRejectOrderWithNonIntegerQuantity(String nonIntegerQuantity) {
        var request = PlaceOrderRequest.builder()
                .sku("some-sku")
                .quantity(nonIntegerQuantity)
                .country("US")
                .build();

        var result = shopDriver.placeOrder(request);
        assertThatResult(result).isFailure("Quantity must be an integer");
    }

    private static Stream<Arguments> provideEmptyCountryValues() {
        return Stream.of(
                Arguments.of("", "Country must not be empty"),      // Empty string
                Arguments.of("   ", "Country must not be empty")    // Whitespace string
        );
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @MethodSource("provideEmptyCountryValues")
    void shouldRejectOrderWithEmptyCountry(String emptyCountry, String expectedErrorMessage) {
        var request = PlaceOrderRequest.builder()
                .sku("some-sku")
                .quantity("5")
                .country(emptyCountry)
                .build();

        var result = shopDriver.placeOrder(request);
        assertThatResult(result).isFailure(expectedErrorMessage);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldRejectOrderWithUnsupportedCountry() {
        var sku = "JKL-" + UUID.randomUUID();
        var createProductResult = erpApiDriver.createProduct(sku, "25.00");
        assertThatResult(createProductResult).isSuccess();

        var request = PlaceOrderRequest.builder()
                .sku(sku)
                .quantity("3")
                .country("XX")
                .build();

        var result = shopDriver.placeOrder(request);
        assertThatResult(result).isFailure("Country does not exist: XX");
    }

    @TestTemplate
    @Channel({ChannelType.API})
    void shouldRejectOrderWithNullQuantity() {
        var request = PlaceOrderRequest.builder()
                .sku("some-sku")
                .quantity(null)
                .country("US")
                .build();

        var result = shopDriver.placeOrder(request);
        assertThatResult(result).isFailure("Quantity must not be empty");
    }

    @TestTemplate
    @Channel({ChannelType.API})
    void shouldRejectOrderWithNullSku() {
        var request = PlaceOrderRequest.builder()
                .sku(null)
                .quantity("5")
                .country("US")
                .build();

        var result = shopDriver.placeOrder(request);
        assertThatResult(result).isFailure("SKU must not be empty");
    }

    @TestTemplate
    @Channel({ChannelType.API})
    void shouldRejectOrderWithNullCountry() {
        var request = PlaceOrderRequest.builder()
                .sku("some-sku")
                .quantity("5")
                .country(null)
                .build();

        var result = shopDriver.placeOrder(request);
        assertThatResult(result).isFailure("Country must not be empty");
    }

    @TestTemplate
    @Channel({ChannelType.API})
    @TestDataSource({"NON-EXISTENT-ORDER-99999", "Order NON-EXISTENT-ORDER-99999 does not exist."})
    @TestDataSource({"INVALID-ORDER-12345", "Order INVALID-ORDER-12345 does not exist."})
    void shouldNotCancelNonExistentOrder(String orderNumber, String expectedErrorMessage) {
        var result = shopDriver.cancelOrder(orderNumber);
        assertThatResult(result).isFailure(expectedErrorMessage);
    }

    @TestTemplate
    @Channel({ChannelType.API})
    void shouldNotCancelAlreadyCancelledOrder() {
        var sku = "MNO-" + UUID.randomUUID();
        var createProductResult = erpApiDriver.createProduct(sku, "35.00");
        assertThatResult(createProductResult).isSuccess();

        var request = PlaceOrderRequest.builder()
                .sku(sku)
                .quantity("3")
                .country("US")
                .build();

        var placeOrderResult = shopDriver.placeOrder(request);
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
