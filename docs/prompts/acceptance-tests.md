# Acceptance Test Rules

When the user provides acceptance criteria, translate each scenario directly into an acceptance test.

## Writing Acceptance Tests

- Write acceptance tests only — do not implement anything.
- Each Gherkin scenario maps directly to one test method — one-to-one, no interpretation needed.
- Only specify the minimum data needed for each scenario — inputs directly relevant to what is being tested, and assertions directly relevant to the expected outcome. Do not add extra fields, extra assertions, or noise.
- If the DSL needs to be extended with new methods, call them directly in the test as if they exist — do not add them to the DSL interface yet. Compile errors are expected and intentional.

## Process

1. Write the acceptance tests.
2. Run the tests and verify they fail.
3. If there is a compile-time error (due to missing DSL interface methods):
   a. Stop and ask for approval to review the tests.
   b. After approval: extend the DSL interface with the new methods.
   c. Implement the DSL by throwing `UnsupportedOperationException("Not implemented yet")` — do not implement drivers.
   d. Run the tests and verify they fail due to `UnsupportedOperationException` (runtime, not compile-time).
4. Commit the changes.
