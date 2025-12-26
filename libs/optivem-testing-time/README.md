# optivem-testing-time

Module providing time-related testing annotations and utilities.

## Purpose

This module provides annotations and utilities to mark and manage tests that require controlled time setup (e.g., time-based business logic, discount rates, expiration logic).

## Key Components

### `@Time` Annotation

Marks tests that require controlled time setup. This allows you to:
- Filter/separate time-dependent tests from time-independent tests
- Run time-sensitive tests in a controlled environment
- Document which tests need time control

**Example usage:**

```java
@TestTemplate
@Channel({ChannelType.UI, ChannelType.API})
@Time
void discountRateShouldBe15percentWhenTimeAfter5pm() {
    scenario
            .given().clock().withTime(Instant.parse("2025-12-24T17:01:00Z"))
            .when().placeOrder()
            .then().order().hasDiscountRate(0.15);
}
```

## When to Use

Use the `@Time` annotation when your test:
- Sets up a specific time/date for the system under test
- Tests time-based business rules (e.g., discounts, expiration, time zones)
- Requires clock control or time mocking
- Has behavior that varies based on the current time

## Benefits

- **Clear Intent**: Tests are explicitly marked as time-dependent
- **Test Isolation**: Time-independent tests can run separately
- **Documentation**: Annotation serves as self-documentation
- **Filtering**: Can filter tests by time requirements in CI/CD pipelines

## Filtering Tests

The `@Time` annotation is integrated with JUnit 5's tagging system, allowing you to easily filter tests:

### Run ONLY time-dependent tests
```bash
# Gradle
./gradlew test -DincludeTags=time

# Windows
.\gradlew.bat test -DincludeTags=time
```

### Run everything EXCEPT time-dependent tests
```bash
# Gradle
./gradlew test -DexcludeTags=time

# Windows
.\gradlew.bat test -DexcludeTags=time
```

### Run all tests (default)
```bash
./gradlew test
```

### IDE Support
In IntelliJ IDEA and VS Code, you can filter tests by the "time" tag directly in the test runner UI.

