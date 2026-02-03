package com.optivem.eshop.systemtest.e2etests.v6;

import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.e2etests.v6.base.BaseE2eTest;
import com.optivem.test.Channel;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ViewOrderNegativeTest extends BaseE2eTest {

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
                .when().viewOrder().withOrderNumber(orderNumber)
                .then().shouldFail()
                .errorMessage(expectedErrorMessage);
    }
}


