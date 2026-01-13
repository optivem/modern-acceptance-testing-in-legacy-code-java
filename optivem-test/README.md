# Testing Annotations

Common annotations for marking test characteristics and requirements.

## Annotations

### @Time

Marks tests that depend on specific time values. Automatically includes `@Isolated` since time-dependent tests require isolation.

```java
@TestTemplate
@Channel({ChannelType.API})
@Time("2024-01-15T17:30:00Z")
void discountRateShouldBe15percentWhenTimeAfter5pm() {
    // Test implementation
}
```

### @Isolated

The `@Isolated` annotation marks tests that require isolation from other tests due to side effects, shared state modifications, or environmental dependencies.

- **Destructive operations**: Tests that delete all orders, clear databases, etc.
- **Time-dependent tests**: Tests using `@Time` annotation
- **State-modifying tests**: Tests that change global configuration
- **Resource-exclusive tests**: Tests requiring exclusive access to external systems

## Usage

```java
@TestTemplate
@Channel({ChannelType.API})
@Isolated("Deletes all orders")
void shouldDeleteAllOrders() {
    scenario
        .when().deleteAllOrders()
        .then().shouldSucceed();
}

@TestTemplate
@Channel({ChannelType.API})
@Time("2024-01-15T17:30:00Z")
@Isolated("Time-dependent discount calculation")
void discountRateShouldBe15percentWhenTimeAfter5pm() {
    // Test implementation
}
```

## Filtering Tests

### Gradle Command Line

**Run ONLY isolated tests:**
```bash
.\gradlew.bat :system-test:acceptance-test:test -DincludeTags=isolated -Denvironment=local
```

**Run all tests EXCEPT isolated tests (for CI/CD):**
```bash
.\gradlew.bat :system-test:acceptance-test:test -DexcludeTags=isolated -Denvironment=ci
```

**Run multiple test categories:**
```bash
# Run only isolated AND time tests
.\gradlew.bat :system-test:acceptance-test:test -DincludeTags="isolated,time" -Denvironment=local

# Exclude both isolated and slow tests
.\gradlew.bat :system-test:acceptance-test:test -DexcludeTags="isolated,slow" -Denvironment=ci
```

### IDE Support

Most modern IDEs with JUnit 5 support can filter by tags:

- **IntelliJ IDEA**: Right-click test class/method → Run with tags
- **Eclipse**: Run Configurations → JUnit → Configure → Tags
- **VS Code**: Test Explorer recognizes JUnit tags

## Integration with CI/CD

For continuous integration, you might want to:

1. Run non-isolated tests in parallel
2. Run isolated tests sequentially or with cleanup between tests

Example GitHub Actions:
```yaml
- name: Run standard tests
  run: .\gradlew.bat :system-test:acceptance-test:test -DexcludeTags=isolated

- name: Run isolated tests
  run: .\gradlew.bat :system-test:acceptance-test:test -DincludeTags=isolated
```

## Relationship with @Time

The `@Time` annotation should also use `@Isolated` since time-dependent tests typically need isolation. You can use both annotations together:

```java
@TestTemplate
@Channel({ChannelType.API})
@Time("2024-01-15T17:30:00Z")
@Isolated
void timeBasedTest() {
    // Implementation
}
```
