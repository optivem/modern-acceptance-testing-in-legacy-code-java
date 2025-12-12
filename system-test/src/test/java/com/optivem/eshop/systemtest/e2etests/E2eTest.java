package com.optivem.eshop.systemtest.e2etests;

import com.optivem.eshop.systemtest.SystemDslFactory;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.e2etests.providers.EmptyArgumentsProvider;
import com.optivem.testing.channels.Channel;
import com.optivem.testing.channels.ChannelExtension;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.testing.channels.DataSource;
import com.optivem.lang.Closer;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.enums.OrderStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;


@ExtendWith(ChannelExtension.class)
public class E2eTest {

    private SystemDsl app;

    @BeforeEach
    void setUp() {
        app = SystemDslFactory.create();
    }

    @AfterEach
    void tearDown() {
        Closer.close(app);
    }

    private static final String ORDER_NUMBER = "order-number";
    private static final String SKU = "sku";

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldPlaceOrderWithCorrectOriginalPrice() {
        app.erp().createProduct()
                .sku("ABC")
                .unitPrice(20.00)
                .execute()
                .shouldSucceed();

        app.shop().placeOrder().orderNumber("ORDER-1001")
                .sku("ABC")
                .quantity(5)
                .execute()
                .shouldSucceed();

        app.shop().viewOrder().orderNumber("ORDER-1001").execute()
                .shouldSucceed()
                .originalPrice(100.00);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @DataSource({"20.00", "5", "100.00"})
    @DataSource({"10.00", "3", "30.00"})
    @DataSource({"15.50", "4", "62.00"})
    @DataSource({"99.99", "1", "99.99"})
    void shouldPlaceOrderWithCorrectOriginalPriceParameterized(String unitPrice, String quantity, String originalPrice) {
        app.erp().createProduct()
                .sku("ABC")
                .unitPrice(unitPrice)
                .execute()
                .shouldSucceed();

        app.shop().placeOrder()
                .orderNumber("ORDER-1001")
                .sku("ABC")
                .quantity(quantity)
                .execute()
                .shouldSucceed();

        app.shop().viewOrder()
                .orderNumber("ORDER-1001")
                .execute()
                .shouldSucceed()
                .originalPrice(originalPrice);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldRejectOrderWithInvalidQuantity() {
        app.shop().placeOrder()
                .quantity("invalid-quantity")
                .execute()
                .shouldFail()
                .errorMessage("Quantity must be an integer");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldPlaceOrder() {
        app.erp().createProduct()
                .sku(SKU)
                .unitPrice(20.00)
                .execute()
                .shouldSucceed();

        app.shop().placeOrder()
                .orderNumber(ORDER_NUMBER)
                .sku(SKU)
                .quantity(5)
                .country("US")
                .execute()
                .shouldSucceed()
                .orderNumber(ORDER_NUMBER)
                .orderNumberStartsWith("ORD-");

        app.shop().viewOrder()
                .orderNumber(ORDER_NUMBER)
                .execute()
                .shouldSucceed()
                .orderNumber(ORDER_NUMBER)
                .sku(SKU)
                .quantity(5)
                .country("US")
                .unitPrice(20.00)
                .originalPrice(100.00)
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
        app.erp().createProduct()
                .sku(SKU)
                .execute()
                .shouldSucceed();

        app.shop().placeOrder()
                .orderNumber(ORDER_NUMBER)
                .sku(SKU)
                .execute()
                .shouldSucceed();

        app.shop().cancelOrder()
                .orderNumber(ORDER_NUMBER)
                .execute()
                .shouldSucceed();

        app.shop().viewOrder()
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
        app.shop().placeOrder()
                .sku("NON-EXISTENT-SKU-12345")
                .execute()
                .shouldFail()
                .errorMessage("Product does not exist for SKU: NON-EXISTENT-SKU-12345");
    }

    private static Stream<Arguments> provideNonExistentOrderValues() {
        return Stream.of(
                Arguments.of("NON-EXISTENT-ORDER-99999", "Order NON-EXISTENT-ORDER-99999 does not exist."),
                Arguments.of("NON-EXISTENT-ORDER-88888", "Order NON-EXISTENT-ORDER-88888 does not exist."),
                Arguments.of("NON-EXISTENT-ORDER-77777", "Order NON-EXISTENT-ORDER-77777 does not exist.")
        );
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @MethodSource("provideNonExistentOrderValues")
    void shouldNotBeAbleToViewNonExistentOrder(String orderNumber, String expectedErrorMessage) {
        app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldFail()
                .errorMessage(expectedErrorMessage);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldRejectOrderWithNegativeQuantity() {
        app.shop().placeOrder()
                .quantity(-10)
                .execute()
                .shouldFail()
                .errorMessage("Quantity must be positive");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldRejectOrderWithZeroQuantity() {
        app.shop().placeOrder()
                .sku("ANOTHER-SKU-67890")
                .quantity(0)
                .execute()
                .shouldFail()
                .errorMessage("Quantity must be positive");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @ArgumentsSource(EmptyArgumentsProvider.class)
    void shouldRejectOrderWithEmptySku(String sku) {
        app.shop().placeOrder()
                .sku(sku)
                .execute()
                .shouldFail()
                .errorMessage("SKU must not be empty");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @ArgumentsSource(EmptyArgumentsProvider.class)
    void shouldRejectOrderWithEmptyQuantity(String emptyQuantity) {
        app.shop().placeOrder()
                .quantity(emptyQuantity)
                .execute()
                .shouldFail()
                .errorMessage("Quantity must not be empty");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @ValueSource(strings = {"3.5", "lala"})
    void shouldRejectOrderWithNonIntegerQuantity(String nonIntegerQuantity) {
        app.shop().placeOrder()
                .quantity(nonIntegerQuantity)
                .execute()
                .shouldFail()
                .errorMessage("Quantity must be an integer");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @ArgumentsSource(EmptyArgumentsProvider.class)
    void shouldRejectOrderWithEmptyCountry(String emptyCountry) {
        app.shop().placeOrder()
                .country(emptyCountry)
                .execute()
                .shouldFail()
                .errorMessage("Country must not be empty");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldRejectOrderWithUnsupportedCountry() {
        app.erp().createProduct()
                .sku(SKU)
                .execute()
                .shouldSucceed();

        app.shop().placeOrder()
                .sku(SKU)
                .country("XX")
                .execute()
                .shouldFail()
                .errorMessage("Country does not exist: XX");
    }

    @TestTemplate
    @Channel({ChannelType.API})
    void shouldRejectOrderWithNullQuantity() {
        app.shop().placeOrder()
                .quantity(null)
                .execute()
                .shouldFail()
                .errorMessage("Quantity must not be empty");
    }

    @TestTemplate
    @Channel({ChannelType.API})
    void shouldRejectOrderWithNullSku() {
        app.shop().placeOrder()
                .sku(null)
                .execute()
                .shouldFail()
                .errorMessage("SKU must not be empty");
    }

    @TestTemplate
    @Channel({ChannelType.API})
    void shouldRejectOrderWithNullCountry() {
        app.shop().placeOrder()
                .country(null)
                .execute()
                .shouldFail()
                .errorMessage("Country must not be empty");
    }

    @TestTemplate
    @Channel({ChannelType.API})
    @DataSource({"NON-EXISTENT-ORDER-99999", "Order NON-EXISTENT-ORDER-99999 does not exist."})
    @DataSource({"NON-EXISTENT-ORDER-88888", "Order NON-EXISTENT-ORDER-88888 does not exist."})
    @DataSource({"NON-EXISTENT-ORDER-77777", "Order NON-EXISTENT-ORDER-77777 does not exist."})
    void shouldNotCancelNonExistentOrder(String orderNumber, String expectedErrorMessage) {
        app.shop().cancelOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldFail()
                .errorMessage(expectedErrorMessage);
    }

    @TestTemplate
    @Channel({ChannelType.API})
    void shouldNotCancelAlreadyCancelledOrder() {
        app.erp().createProduct()
                .sku(SKU)
                .execute()
                .shouldSucceed();

        app.shop().placeOrder()
                .orderNumber(ORDER_NUMBER)
                .sku(SKU)
                .execute()
                .shouldSucceed();

        app.shop().cancelOrder()
                .orderNumber(ORDER_NUMBER)
                .execute()
                .shouldSucceed();

        app.shop().cancelOrder()
                .orderNumber(ORDER_NUMBER)
                .execute()
                .shouldFail()
                .errorMessage("Order has already been cancelled");
    }
}
