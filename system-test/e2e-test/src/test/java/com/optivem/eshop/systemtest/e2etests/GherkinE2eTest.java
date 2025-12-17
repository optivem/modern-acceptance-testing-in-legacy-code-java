package com.optivem.eshop.systemtest.e2etests;

import com.optivem.eshop.systemtest.SystemDslFactory;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.lang.Closer;
import com.optivem.testing.channels.Channel;
import com.optivem.testing.channels.ChannelExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ChannelExtension.class)
public class GherkinE2eTest {

    private SystemDsl app;
    private ScenarioDsl scenario;

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
    void shouldPlaceOrderWithMultipleProducts() {
        scenario
            .given()
                .product()
                    .withSku("SKU-001")
                    .withUnitPrice(20.00)
                .and().product()
                    .withSku("SKU-002")
                    .withUnitPrice(35.50)
            .when()
                .placeOrder()
                    .withOrderNumber("ORDER-2001")
                    .withSku("SKU-001")
                    .withQuantity(5)
            .then()
                .shouldSucceed()
                    .expectOrderNumberPrefix("ORD-")
                .and()
                    .order("ORDER-2001")
                        .hasSku("SKU-001")
                        .hasTotalPrice(100.00);
    }
}
