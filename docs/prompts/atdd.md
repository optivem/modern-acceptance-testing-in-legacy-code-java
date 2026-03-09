# ATDD Rules

When the user provides acceptance criteria, translate each scenario directly into an acceptance test.

## Writing Acceptance Tests

- Write acceptance tests only — do not implement anything.
- Each Gherkin scenario maps directly to one test method — one-to-one, no interpretation needed.
- Only specify the minimum data needed for each scenario — inputs directly relevant to what is being tested, and assertions directly relevant to the expected outcome. Do not add extra fields, extra assertions, or noise. If a field is not relevant to the scenario being tested, omit it entirely and let the DSL use its default value.
- If the DSL needs to be extended with new methods, call them directly in the test as if they exist — do not add them to the DSL interface yet. Compile errors are expected and intentional.
- After writing each test, verify it matches the acceptance criteria exactly — Given maps to Given, When maps to When, Then maps to Then. Every precondition stated in the scenario must appear in the test. If anything is unclear, ask before proceeding.

## RED 1 - Test

1. Write the acceptance tests.
2. Run the tests and verify they fail.
3. STOP and ask for approval to review the tests.
4. If there is a compile-time error (due to missing DSL interface methods):
   a. Extend the DSL interface with the new methods.
   b. Implement the DSL by throwing `UnsupportedOperationException("DSL not implemented yet")` — do not implement drivers.
   c. Run the tests and verify they fail due to `UnsupportedOperationException` (runtime, not compile-time).
5. Mark the tests as `@Disabled` with comment "In Progress - Test".
6. Commit the changes.

## RED 2 - DSL

If there were compilation errors in RED 1, then you'll need to implement the DSL in the following way.

### Steps

1. Enable the tests that were marked as `@Disabled` with comment "In Progress - Test".
2. Implement the DSL for real, which means you'll need to replace the `UnsupportedOperationException("DSL not implemented yet")` with actual implementation.
3. As you're implementing the DSL, you will probably need to change the Driver interfaces.
4. STOP and ask for approval to review the DSL implementation and Driver interfaces.
5. Implement the Drivers by throwing `UnsupportedOperationException("Driver not implemented yet")`.
6. Run the tests and verify they fail with `UnsupportedOperationException`.
7. Make the tests `@Disabled` again, with comment "In Progress - DSL".
8. Ensure that there are no test files in the list of changed files.
9. Commit the changes.

## RED 3 - Driver

If there were compilation errors in RED 1, then you'll need to implement the Driver in the following way.

1. Enable the tests that were marked as `@Disabled` with comment "In Progress - DSL".
2. Implement the Drivers.
3. Run the tests and verify they fail in the `then` stage.
4. STOP and ask for approval to review the Drivers.
5. Make the tests `@Disabled` again, with comment "In Progress - Implementation".
6. Ensure that there are no test files in the list of changed files.
7. Commit the changes.

## GREEN - Backend

1. Implement the backend:
   a. Implement the backend changes.
   b. Run acceptance tests for the API channel.
   c. If tests fail, fix the backend until the tests pass.
   d. If you have challenges in making the tests pass, ask the user.
   e. Do NOT change the tests/dsl/drivers, change only the backend code.
2. Implement the frontend:
   a. Implement the frontend changes.
   b. Run acceptance tests for the UI channel.
   c. If tests fail, fix the frontend until the tests pass.
   d. If you have challenges in making the tests pass, ask the user.
   e. Do NOT change the tests/dsl/drivers, change only the frontend code.
3. By now, all acceptance tests should be passing.
4. STOP and ask for approval to review the implementation.
5. Remove the `@Disabled` annotation.
6. Ensure that there are no test files in the list of changed files.
7. Commit the changes.

// TODO: VJ: Add rules for external system contract tests.