package com.optivem.test;

import java.lang.annotation.*;

/**
 * Repeatable annotation to provide inline test arguments that will be combined with channel types.
 * Each @DataSource annotation represents one row of test arguments that will be
 * executed against all specified channels.
 * <p>
 * Example with inline values:
 * <pre>
 * @TestTemplate
 * @Channel({ChannelType.UI, ChannelType.API})
 * @DataSource("3.5")
 * @DataSource("lala")
 * void shouldRejectOrderWithNonIntegerQuantity(String nonIntegerQuantity) {
 *     // This test will run 4 times: UI with "3.5", UI with "lala", API with "3.5", API with "lala"
 * }
 * </pre>
 * <p>
 * Example with multiple parameters:
 * <pre>
 * @TestTemplate
 * @Channel({ChannelType.UI, ChannelType.API})
 * @DataSource({"SKU123", "5", "US"})
 * @DataSource({"SKU456", "10", "UK"})
 * void testOrder(String sku, String quantity, String country) {
 *     // Each annotation provides all 3 parameters
 * }
 * </pre>
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(DataSource.Container.class)
public @interface DataSource {
    /**
     * The test argument values for this row.
     */
    String[] value();

    /**
     * Container annotation for repeated @DataSource annotations.
     */
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Container {
        DataSource[] value();
    }
}

