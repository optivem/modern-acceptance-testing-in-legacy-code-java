package com.optivem.testing.time;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark tests that require controlled time setup.
 * Tests with this annotation should be executed separately as they depend on specific time configurations
 * (e.g., for discount rates, time-based calculations, etc.).
 *
 * Use this to filter/group tests that need time control vs those that don't.
 * 
 * This annotation is also tagged with JUnit's @Tag("time") for filtering support:
 * - Run only time tests: gradle test -DincludeTags=time
 * - Exclude time tests: gradle test -DexcludeTags=time
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Tag("time")
public @interface Time {
    /**
     * Whether this test requires controlled time setup.
     * Default is true.
     */
    boolean required() default true;
}

