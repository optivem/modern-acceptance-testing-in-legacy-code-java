package com.optivem.eshop.systemtest.core.channels.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Repeatable annotation to provide inline test arguments that will be combined with channel types.
 * Each @ChannelArgumentsSource annotation represents one row of test arguments that will be
 * executed against all specified channels.
 *
 * Supports two modes:
 * 1. Inline values: @ChannelArgumentsSource({"value1", "value2"})
 * 2. Provider class: @ChannelArgumentsSource(provider = MyProvider.class)
 *
 * Example with inline values:
 * <pre>
 * @TestTemplate
 * @Channel({ChannelType.UI, ChannelType.API})
 * @ChannelArgumentsSource("3.5")
 * @ChannelArgumentsSource("lala")
 * void shouldRejectOrderWithNonIntegerQuantity(String nonIntegerQuantity) {
 *     // This test will run 4 times: UI with "3.5", UI with "lala", API with "3.5", API with "lala"
 * }
 * </pre>
 *
 * Example with provider:
 * <pre>
 * @TestTemplate
 * @Channel({ChannelType.UI, ChannelType.API})
 * @ChannelArgumentsSource(provider = OrderDataProvider.class)
 * void shouldPlaceOrder(String sku, int quantity, String country) {
 *     // Provider can return complex objects
 * }
 * </pre>
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(TestDataSource.Container.class)
public @interface TestDataSource {
    /**
     * The test argument values for this row (inline mode).
     * Mutually exclusive with provider().
     */
    String[] value() default {};

    /**
     * The provider class that supplies test arguments (provider mode).
     * Mutually exclusive with value().
     */
    Class<? extends ChannelArgumentsProvider> provider() default NullArgumentsProvider.class;

    /**
     * Container annotation for repeated @ChannelArgumentsSource annotations.
     */
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Container {
        TestDataSource[] value();
    }
}



