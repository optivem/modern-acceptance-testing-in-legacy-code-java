# ATDD Rules

## Phase Triggers

Each ATDD phase is a separate, explicit step. **Never proceed to the next phase automatically.** Wait for the user to explicitly say `"proceed to RED 2"`, `"proceed to RED 3"`, etc. before starting the next phase.

If the user says anything other than a phase trigger after a STOP, ask them clarifying questions — do not execute the next phase.

---

When the user provides acceptance criteria, translate each scenario directly into an acceptance test.

## Writing Acceptance Tests

- Write acceptance tests only — do not implement anything.
- Each Gherkin scenario maps directly to one test method — one-to-one, no interpretation needed.
- Only specify the minimum data needed for each scenario — inputs directly relevant to what is being tested, and assertions directly relevant to the expected outcome. Do not add extra fields, extra assertions, or noise. If a field is not relevant to the scenario being tested, omit it entirely and let the DSL use its default value.
- If the DSL needs to be extended with new methods, call them directly in the test as if they exist — do not add them to the DSL interface yet. Compile errors are expected and intentional.
- After writing each test, verify it matches the acceptance criteria exactly — Given maps to Given, When maps to When, Then maps to Then. Every precondition stated in the scenario must appear in the test. If anything is unclear, ask before proceeding.

## RED 1 - Acceptance Tests (DRAFT)

1. Write the acceptance tests.
2. Run the tests and verify they fail (compile error is expected if new DSL methods are needed).
3. STOP. Present the tests to the user and ask for approval. Do NOT continue.

## RED 1 - Acceptance Tests (COMMIT)

1. If there were compile-time errors in RED 1 (DRAFT):
   a. Extend the DSL interfaces with the new methods.
   b. Implement the new methods by throwing `UnsupportedOperationException("TODO: DSL")` — do not implement DSL.
   c. Run the tests and verify they fail with a runtime error.
2. Mark the tests as `@Disabled("RED 1 - Tests")`.
3. COMMIT with message `<Scenario> | RED 1 - Tests`.
4. If there were compile-time errors in RED 1 (DRAFT), automatically proceed to RED 2 (DRAFT).
   Otherwise, STOP and wait for the user to indicate what to do next.

## RED 2 - DSL (DRAFT)

1. Enable the tests marked `@Disabled("RED 1 - Tests")`.
2. Implement the DSL for real — replace `UnsupportedOperationException("TODO: DSL")` with actual logic.
3. Update the Driver interfaces as needed.
4. STOP. Present the DSL implementation and Driver interface changes to the user and ask for approval. Do NOT continue.

## RED 2 - DSL (COMMIT)

1. Implement the Drivers by throwing `UnsupportedOperationException("TODO: Driver")`.
2. Run the tests and verify they fail with a runtime error.
3. Mark the tests as `@Disabled("RED 2 - DSL")`.
4. Ensure that there are no test files in the list of changed files.
5. COMMIT with message `<Scenario> | RED 2 - DSL`.
6. Automatically proceed to RED 3 (DRAFT).

## RED 3 - Driver (DRAFT)

1. Enable the tests marked `@Disabled("RED 2 - DSL")`.
2. Implement the Drivers — replace `UnsupportedOperationException("TODO: Driver")` with actual logic.
   - Only look at files in the `driver-adapter` and `driver-port` directories.
   - Do NOT read or search backend/frontend source code. Model the new method on existing driver methods in the same file.
3. Run the tests and verify they fail with a runtime error.
4. STOP. Present the Driver implementation to the user and ask for approval. Do NOT continue.

## RED 3 - Driver (COMMIT)

1. Mark the tests as `@Disabled("RED")`.
2. COMMIT with message `<Scenario> | RED 3 - Driver`.
3. If the test failure was due to an External System Stub (i.e. the stub does not yet support the new operation), automatically proceed to RED 4 - Contract Tests (DRAFT).
   Otherwise, automatically proceed to GREEN 2.

## RED 4 - Contract Tests (DRAFT)

_Only needed if there are runtime failures due to External System Stubs._

_If the External System does not even exist yet, make Smoke Tests pass first._

1. Write External System Contract Tests.
2. Verify that they pass when executed against the Real External System. 
   If they don't pass, then ask the user for support. STOP. Do NOT continue.
3. Verify that they fail when executed against the Stub External System.
4. Mark the tests `@Disabled("RED 4 - Contract Tests")`.
5. STOP. Present the contract tests to the user and ask for approval. Do NOT continue.

## RED 4 - Contract Tests (COMMIT)

1. COMMIT the changes with message `<Scenario> | RED 4 - Contract Tests`.
2. Automatically proceed to GREEN 1 (DRAFT).

## GREEN 1 - External System Stubs (DRAFT)

1. Enable the tests marked `@Disabled("RED 4 - Contract Tests")`.
2. Implement the External System Stubs.
3. Execute `./Run-SystemTests.ps1 -Rebuild -SkipTests`.
4. Verify that the External System Contract Tests pass. If they fail, fix and repeat.
5. STOP. Present the stub implementation to the user and ask for approval. Do NOT continue.

## GREEN 1 - External System Stubs (COMMIT)

1. COMMIT with message `<Scenario> | GREEN - External System Stubs`.
2. Automatically proceed to GREEN 2 (DRAFT).

## GREEN 2 - System (DRAFT)

1. Implement the backend:
   a. Implement the backend changes.
   b. Run acceptance tests for the API channel.
   c. If tests fail, fix the backend until the tests pass.
   d. If you have challenges making the tests pass, ask the user.
   e. Do NOT change the tests/dsl/drivers — change only the backend code.
2. Implement the frontend:
   a. Implement the frontend changes.
   b. Run acceptance tests for the UI channel.
   c. If tests fail, fix the frontend until the tests pass.
   d. If you have challenges making the tests pass, ask the user.
   e. Do NOT change the tests/dsl/drivers — change only the frontend code.
3. By now, all acceptance tests should be passing.
4. STOP. Present the implementation to the user and ask for approval. Do NOT continue.

## GREEN 2 - System (COMMIT)

1. Remove the `@Disabled` annotation from the tests.
2. Run the tests and verify they all pass.
3. Ensure that there are no non-test files in the list of changed files.
4. COMMIT with message `<Scenario> | GREEN - System`.
