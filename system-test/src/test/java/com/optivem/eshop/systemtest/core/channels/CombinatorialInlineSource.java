package com.optivem.eshop.systemtest.core.channels;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Repeatable annotation to provide inline test data that will be combined with channel types.
 * Each @CombinatorialInlineSource annotation represents one row of test data that will be
 * executed against all specified channels.
 *
 * Example:
 * <pre>
 * @TestTemplate
 * @Channel({ChannelType.UI, ChannelType.API})
 * @CombinatorialInlineSource("3.5")
 * @CombinatorialInlineSource("lala")
 * void shouldRejectOrderWithNonIntegerQuantity(String nonIntegerQuantity) {
 *     // This test will run 4 times: UI with "3.5", UI with "lala", API with "3.5", API with "lala"
 * }
 * </pre>
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CombinatorialInlineSource.Container.class)
public @interface CombinatorialInlineSource {
    /**
     * The test data values for this row.
     */
    String[] value();

    /**
     * Container annotation for repeated @CombinatorialInlineSource annotations.
     */
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Container {
        CombinatorialInlineSource[] value();
    }
}


