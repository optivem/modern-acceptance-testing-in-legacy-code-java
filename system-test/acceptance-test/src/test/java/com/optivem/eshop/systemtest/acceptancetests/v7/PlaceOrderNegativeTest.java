package com.optivem.eshop.systemtest.acceptancetests.v7;

import com.optivem.eshop.systemtest.acceptancetests.commons.providers.EmptyArgumentsProvider;
import com.optivem.eshop.systemtest.acceptancetests.v7.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.testing.channels.Channel;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.optivem.eshop.systemtest.acceptancetests.commons.constants.Defaults.SKU;

public class PlaceOrderNegativeTest extends BaseAcceptanceTest {

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
}

