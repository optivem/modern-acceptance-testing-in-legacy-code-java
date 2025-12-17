package com.optivem.eshop.systemtest.e2etests;

import com.optivem.eshop.systemtest.SystemDslFactory;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.enums.OrderStatus;
import com.optivem.eshop.systemtest.e2etests.providers.EmptyArgumentsProvider;
import com.optivem.lang.Closer;
import com.optivem.testing.channels.Channel;
import com.optivem.testing.channels.ChannelExtension;
import com.optivem.testing.channels.DataSource;
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
public class GherkinMigratedE2eTest {

    private SystemDsl app;
    private ScenarioDsl scenario;

    private static final String ORDER_NUMBER = "order-number";
    private static final String SKU = "sku";

    @BeforeEach
    void setUp() {
        app = SystemDslFactory.create();
        scenario = new ScenarioDsl(app);
    }

    @AfterEach
    void tearDown() {
        Closer.close(app);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldPlaceOrderWithCorrectOriginalPrice() {
        scenario
            .given()
                .product()
                    .withSku("ABC")
                    .withUnitPrice(20.00)
            .when()
                .placeOrder()
                    .withOrderNumber("ORDER-1001")
                    .withSku("ABC")
                    .withQuantity(5)
            .then()
                .shouldSucceed()
                .and()
                    .order("ORDER-1001")
                        .hasOriginalPrice(100.00);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @DataSource({"20.00", "5", "100.00"})
    @DataSource({"10.00", "3", "30.00"})
    @DataSource({"15.50", "4", "62.00"})
    @DataSource({"99.99", "1", "99.99"})
    void shouldPlaceOrderWithCorrectOriginalPriceParameterized(String unitPrice, String quantity, String originalPrice) {
        scenario
            .given()
                .product()
                    .withSku("ABC")
                    .withUnitPrice(unitPrice)
            .when()
                .placeOrder()
                    .withOrderNumber("ORDER-1001")
                    .withSku("ABC")
                    .withQuantity(quantity)
            .then()
                .shouldSucceed()
                .and()
                    .order("ORDER-1001")
                        .hasOriginalPrice(originalPrice);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldRejectOrderWithInvalidQuantity() {
        scenario
            .given()
                .product()
                    .withSku("SKU-001")
            .when()
                .placeOrder()
                    .withOrderNumber("ORDER-4001")
                    .withSku("SKU-001")
                    .withQuantity("invalid-quantity")
            .then()
                .shouldFail()
                    .errorMessage("The request contains one or more validation errors")
                    .fieldErrorMessage("quantity", "Quantity must be an integer");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldPlaceOrder() {
        scenario
            .given()
                .product()
                    .withSku(SKU)
                    .withUnitPrice(20.00)
            .when()
                .placeOrder()
                    .withOrderNumber(ORDER_NUMBER)
                    .withSku(SKU)
                    .withQuantity(5)
                    .withCountry("US")
            .then()
                .shouldSucceed()
                    .expectOrderNumberPrefix("ORD-")
                .and()
                    .order(ORDER_NUMBER)
                        .hasSku(SKU)
                        .hasQuantity(5)
                        .hasCountry("US")
                        .hasUnitPrice(20.00)
                        .hasOriginalPrice(100.00)
                        .hasStatus(OrderStatus.PLACED)
                        .hasDiscountRateGreaterThanOrEqualToZero()
                        .hasDiscountAmountGreaterThanOrEqualToZero()
                        .hasSubtotalPriceGreaterThanZero()
                        .hasTaxRateGreaterThanOrEqualToZero()
                        .hasTaxAmountGreaterThanOrEqualToZero()
                        .hasTotalPriceGreaterThanZero();
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldCancelOrder() {
        scenario
            .given()
                .product()
                    .withSku(SKU)
                .and().order()
                    .withOrderNumber(ORDER_NUMBER)
                    .withSku(SKU)
            .when()
                .cancelOrder()
                    .withOrderNumber(ORDER_NUMBER)
            .then()
                .shouldSucceed()
                .and()
                    .order(ORDER_NUMBER)
                        .hasSku(SKU)
                        .hasStatus(OrderStatus.CANCELLED);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldRejectOrderWithNonExistentSku() {
        scenario
            .given()
                .noProducts()
            .when()
                .placeOrder()
                    .withSku("NON-EXISTENT-SKU-12345")
            .then()
                .shouldFail()
                    .errorMessage("The request contains one or more validation errors")
                    .fieldErrorMessage("sku", "Product does not exist for SKU: NON-EXISTENT-SKU-12345");
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
        scenario
            .given()
                .noProducts()
            .when()
                .viewOrder()
                    .withOrderNumber(orderNumber)
            .then()
                .shouldFail()
                    .errorMessage(expectedErrorMessage);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldRejectOrderWithNegativeQuantity() {
        scenario
            .given()
                .noProducts()
            .when()
                .placeOrder()
                    .withQuantity(-10)
            .then()
                .shouldFail()
                    .errorMessage("The request contains one or more validation errors")
                    .fieldErrorMessage("quantity", "Quantity must be positive");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldRejectOrderWithZeroQuantity() {
        scenario
            .given()
                .noProducts()
            .when()
                .placeOrder()
                    .withSku("ANOTHER-SKU-67890")
                    .withQuantity(0)
            .then()
                .shouldFail()
                    .errorMessage("The request contains one or more validation errors")
                    .fieldErrorMessage("quantity", "Quantity must be positive");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @ArgumentsSource(EmptyArgumentsProvider.class)
    void shouldRejectOrderWithEmptySku(String sku) {
        scenario
            .given()
                .noProducts()
            .when()
                .placeOrder()
                    .withSku(sku)
            .then()
                .shouldFail()
                    .errorMessage("The request contains one or more validation errors")
                    .fieldErrorMessage("sku", "SKU must not be empty");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @ArgumentsSource(EmptyArgumentsProvider.class)
    void shouldRejectOrderWithEmptyQuantity(String emptyQuantity) {
        scenario
            .given()
                .noProducts()
            .when()
                .placeOrder()
                    .withQuantity(emptyQuantity)
            .then()
                .shouldFail()
                    .errorMessage("The request contains one or more validation errors")
                    .fieldErrorMessage("quantity", "Quantity must not be empty");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @ValueSource(strings = {"3.5", "lala"})
    void shouldRejectOrderWithNonIntegerQuantity(String nonIntegerQuantity) {
        scenario
            .given()
                .noProducts()
            .when()
                .placeOrder()
                    .withQuantity(nonIntegerQuantity)
            .then()
                .shouldFail()
                    .errorMessage("The request contains one or more validation errors")
                    .fieldErrorMessage("quantity", "Quantity must be an integer");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @ArgumentsSource(EmptyArgumentsProvider.class)
    void shouldRejectOrderWithEmptyCountry(String emptyCountry) {
        scenario
            .given()
                .noProducts()
            .when()
                .placeOrder()
                    .withCountry(emptyCountry)
            .then()
                .shouldFail()
                    .errorMessage("The request contains one or more validation errors")
                    .fieldErrorMessage("country", "Country must not be empty");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldRejectOrderWithUnsupportedCountry() {
        scenario
            .given()
                .product()
                    .withSku(SKU)
            .when()
                .placeOrder()
                    .withSku(SKU)
                    .withCountry("XX")
            .then()
                .shouldFail()
                    .errorMessage("The request contains one or more validation errors")
                    .fieldErrorMessage("country", "Country does not exist: XX");
    }

    @TestTemplate
    @Channel({ChannelType.API})
    void shouldRejectOrderWithNullQuantity() {
        scenario
            .given()
                .noProducts()
            .when()
                .placeOrder()
                    .withQuantity((String) null)
            .then()
                .shouldFail()
                    .errorMessage("The request contains one or more validation errors")
                    .fieldErrorMessage("quantity", "Quantity must not be empty");
    }

    @TestTemplate
    @Channel({ChannelType.API})
    void shouldRejectOrderWithNullSku() {
        scenario
            .given()
                .noProducts()
            .when()
                .placeOrder()
                    .withSku(null)
            .then()
                .shouldFail()
                    .errorMessage("The request contains one or more validation errors")
                    .fieldErrorMessage("sku", "SKU must not be empty");
    }

    @TestTemplate
    @Channel({ChannelType.API})
    void shouldRejectOrderWithNullCountry() {
        scenario
            .given()
                .noProducts()
            .when()
                .placeOrder()
                    .withCountry(null)
            .then()
                .shouldFail()
                    .errorMessage("The request contains one or more validation errors")
                    .fieldErrorMessage("country", "Country must not be empty");
    }

    @TestTemplate
    @Channel({ChannelType.API})
    @DataSource({"NON-EXISTENT-ORDER-99999", "Order NON-EXISTENT-ORDER-99999 does not exist."})
    @DataSource({"NON-EXISTENT-ORDER-88888", "Order NON-EXISTENT-ORDER-88888 does not exist."})
    @DataSource({"NON-EXISTENT-ORDER-77777", "Order NON-EXISTENT-ORDER-77777 does not exist."})
    void shouldNotCancelNonExistentOrder(String orderNumber, String expectedErrorMessage) {
        scenario
            .given()
                .noProducts()
            .when()
                .cancelOrder()
                    .withOrderNumber(orderNumber)
            .then()
                .shouldFail()
                    .errorMessage(expectedErrorMessage);
    }

    @TestTemplate
    @Channel({ChannelType.API})
    void shouldNotCancelAlreadyCancelledOrder() {
        scenario
            .given()
                .product()
                    .withSku(SKU)
                .and().order()
                    .withOrderNumber(ORDER_NUMBER)
                    .withSku(SKU)
            .when()
                .cancelOrder()
                    .withOrderNumber(ORDER_NUMBER)
            .then()
                .shouldSucceed();

        // Second cancellation should fail
        scenario
            .given()
                .noProducts()
            .when()
                .cancelOrder()
                    .withOrderNumber(ORDER_NUMBER)
            .then()
                .shouldFail()
                    .errorMessage("Order has already been cancelled");
    }
}
