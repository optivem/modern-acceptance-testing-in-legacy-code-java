package com.optivem.eshop.systemtest.e2etests;

import com.optivem.eshop.systemtest.core.dsl.commons.context.Context;
import com.optivem.eshop.systemtest.core.dsl.erp.ErpDsl;
import com.optivem.eshop.systemtest.core.dsl.shop.ShopDsl;
import com.optivem.eshop.systemtest.e2etests.providers.EmptyQuantityArgumentsProvider;
import com.optivem.testing.channels.Channel;
import com.optivem.testing.channels.ChannelExtension;
import com.optivem.eshop.systemtest.core.channels.ChannelType;
import com.optivem.testing.channels.TestDataSource;
import com.optivem.lang.Closer;
import com.optivem.eshop.systemtest.core.drivers.system.commons.enums.OrderStatus;
import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.eshop.systemtest.core.drivers.system.shop.ShopDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


@ExtendWith(ChannelExtension.class)
public class E2eTest {

    private ShopDriver shopDriver;
    private ErpApiDriver erpApiDriver;

    private ShopDsl shop;
    private ErpDsl erp;

    @BeforeEach
    void setUp() {
        shopDriver = DriverFactory.createShopDriver();
        erpApiDriver = DriverFactory.createErpApiDriver();

        var context = new Context();
        shop = new ShopDsl(shopDriver, context);
        erp = new ErpDsl(erpApiDriver, context);
    }

    @AfterEach
    void tearDown() {
        Closer.close(shopDriver);
        Closer.close(erpApiDriver);
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
                .orderNumber(ORDER_NUMBER)
                .orderNumberStartingWith("ORD-");

        shop.viewOrder()
                .orderNumber(ORDER_NUMBER)
                .execute()
                .shouldSucceed()
                .orderNumber(ORDER_NUMBER)
                .sku(SKU)
                .quantity(5)
                .country("US")
                .unitPrice("20.00")
                .originalPrice("100.00")
                .status(OrderStatus.PLACED)
                .discountRateGreaterThanOrEqualToZero()
                .discountAmountGreaterThanOrEqualToZero()
                .subtotalPriceGreaterThanZero()
                .taxRateGreaterThanOrEqualToZero()
                .taxAmountGreaterThanOrEqualToZero()
                .totalPriceGreaterThanZero();
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldCancelOrder() {
        erp.createProduct()
                .sku(SKU)
                .execute()
                .shouldSucceed();

        shop.placeOrder()
                .orderNumber(ORDER_NUMBER)
                .sku(SKU)
                .execute()
                .shouldSucceed();

        shop.cancelOrder()
                .orderNumber(ORDER_NUMBER)
                .execute()
                .shouldSucceed();

        shop.viewOrder()
                .orderNumber(ORDER_NUMBER)
                .execute()
                .shouldSucceed()
                .orderNumber(ORDER_NUMBER)
                .sku(SKU)
                .status(OrderStatus.CANCELLED);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldRejectOrderWithNonExistentSku() {
        shop.placeOrder()
                .sku("NON-EXISTENT-SKU-12345")
                .execute()
                .shouldFail()
                .errorMessage("Product does not exist for SKU: NON-EXISTENT-SKU-12345");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldNotBeAbleToViewNonExistentOrder() {
        shop.viewOrder()
                .orderNumber("NON-EXISTENT-ORDER-12345")
                .execute()
                .shouldFail()
                .errorMessage("Order NON-EXISTENT-ORDER-12345 does not exist.");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldRejectOrderWithNegativeQuantity() {
        shop.placeOrder()
                .quantity("-10")
                .execute()
                .shouldFail()
                .errorMessage("Quantity must be positive");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldRejectOrderWithZeroQuantity() {
        shop.placeOrder()
                .sku("ANOTHER-SKU-67890")
                .quantity("0")
                .execute()
                .shouldFail()
                .errorMessage("Quantity must be positive");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @TestDataSource("")
    @TestDataSource("   ")
    void shouldRejectOrderWithEmptySku(String sku) {
        shop.placeOrder()
                .sku(sku)
                .execute()
                .shouldFail()
                .errorMessage("SKU must not be empty");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @ArgumentsSource(EmptyQuantityArgumentsProvider.class)
    void shouldRejectOrderWithEmptyQuantity(String emptyQuantity, String expectedErrorMessage) {
        shop.placeOrder()
                .quantity(emptyQuantity)
                .execute()
                .shouldFail()
                .errorMessage(expectedErrorMessage);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @TestDataSource("3.5")
    @TestDataSource("lala")
    void shouldRejectOrderWithNonIntegerQuantity(String nonIntegerQuantity) {
        shop.placeOrder()
                .quantity(nonIntegerQuantity)
                .execute()
                .shouldFail()
                .errorMessage("Quantity must be an integer");
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
        shop.placeOrder()
                .country(emptyCountry)
                .execute()
                .shouldFail()
                .errorMessage(expectedErrorMessage);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldRejectOrderWithUnsupportedCountry() {
        erp.createProduct()
                .sku(SKU)
                .execute()
                .shouldSucceed();

        shop.placeOrder()
                .sku(SKU)
                .country("XX")
                .execute()
                .shouldFail()
                .errorMessage("Country does not exist: XX");
    }

    @TestTemplate
    @Channel({ChannelType.API})
    void shouldRejectOrderWithNullQuantity() {
        shop.placeOrder()
                .quantity(null)
                .execute()
                .shouldFail()
                .errorMessage("Quantity must not be empty");
    }

    @TestTemplate
    @Channel({ChannelType.API})
    void shouldRejectOrderWithNullSku() {
        shop.placeOrder()
                .sku(null)
                .execute()
                .shouldFail()
                .errorMessage("SKU must not be empty");
    }

    @TestTemplate
    @Channel({ChannelType.API})
    void shouldRejectOrderWithNullCountry() {
        shop.placeOrder()
                .country(null)
                .execute()
                .shouldFail()
                .errorMessage("Country must not be empty");
    }

    @TestTemplate
    @Channel({ChannelType.API})
    @TestDataSource({"NON-EXISTENT-ORDER-99999", "Order NON-EXISTENT-ORDER-99999 does not exist."})
    @TestDataSource({"INVALID-ORDER-12345", "Order INVALID-ORDER-12345 does not exist."})
    void shouldNotCancelNonExistentOrder(String orderNumber, String expectedErrorMessage) {
        shop.cancelOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldFail()
                .errorMessage(expectedErrorMessage);
    }

    @TestTemplate
    @Channel({ChannelType.API})
    void shouldNotCancelAlreadyCancelledOrder() {
        erp.createProduct()
                .sku(SKU)
                .execute()
                .shouldSucceed();

        shop.placeOrder()
                .orderNumber(ORDER_NUMBER)
                .sku(SKU)
                .execute()
                .shouldSucceed();

        shop.cancelOrder()
                .orderNumber(ORDER_NUMBER)
                .execute()
                .shouldSucceed();

        shop.cancelOrder()
                .orderNumber(ORDER_NUMBER)
                .execute()
                .shouldFail()
                .errorMessage("Order has already been cancelled");
    }
}
