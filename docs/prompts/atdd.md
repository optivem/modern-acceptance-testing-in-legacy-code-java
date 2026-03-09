# ATDD Rules

## Commit Message Format

Commit messages must follow this format: `<Phase>: <Short description of what changed>`

Examples:
- `RED 1 - Tests: Add Submit Review acceptance tests`
- `RED 2 - Tests: Add DSL skeleton for Submit Review`
- `RED 3 - DSL: Implement Submit Review DSL and driver interfaces`
- `RED 4 - DSL: Add driver skeleton for Submit Review`
- `RED 5 - Driver: Implement Submit Review API and UI drivers`
- `GREEN 1 - Stubs: Implement Submit Review external system stubs`
- `GREEN 2 - System: Implement Submit Review backend and frontend`
- `GREEN 3 - Enable Tests: Enable Submit Review acceptance tests`

---

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

## RED 1 - Tests

1. Write the acceptance tests.
2. Run the tests and verify they fail (compile error is expected if new DSL methods are needed).
3. STOP. Present the tests to the user and ask for approval. Do NOT continue.

## RED 2 - Tests (COMMIT)

1. If there were compile-time errors in RED 1:
   a. Extend the DSL interfaces with the new methods.
   b. Implement the new methods by throwing `UnsupportedOperationException("TODO: DSL")` — do not implement DSL.
   c. Run the tests and verify they fail with `UnsupportedOperationException("TODO: DSL")` (runtime, not compile-time).
2. Mark the tests as `@Disabled("RED - Tests")`.
3. COMMIT the changes.
4. If there were compile-time errors in RED 1, automatically proceed to RED 3. 
   Otherwise, STOP and wait for the user to indicate what to do next.

## RED 3 - DSL

1. Enable the tests marked `@Disabled("RED - Tests")`.
2. Implement the DSL for real — replace `UnsupportedOperationException("TODO: DSL")` with actual logic.
3. Update the Driver interfaces as needed.
4. STOP. Present the DSL implementation and Driver interface changes to the user and ask for approval. Do NOT continue.

## RED 4 - DSL (COMMIT)

1. Implement the Drivers by throwing `UnsupportedOperationException("TODO: Driver")`.
2. Run the tests and verify they fail with `UnsupportedOperationException("TODO: Driver")`.
3. Mark the tests as `@Disabled("RED - DSL")`.
4. Ensure that there are no test files in the list of changed files.
5. COMMIT the changes.
6. Automatically proceed to RED 5.

## RED 5 - Driver

1. Enable the tests marked `@Disabled("RED - DSL")`.
2. Implement the Drivers — replace `UnsupportedOperationException("TODO: Driver")` with actual logic.
3. Run the tests and verify they fail in the `then` stage.
5. STOP. Present the Driver implementation to the user and ask for approval. Do NOT continue.

## RED 6 - Driver (COMMIT)

1. COMMIT the changes.










## RED 6 - Contract Tests

_Only needed if there are runtime failures due to External System Stubs._

Note: If the External System does not even exist yet, make Smoke Tests pass first.

1. Write External System Contract Tests.
2. Verify that they pass when executed against the Real External System.
3. Verify that they fail when executed against the Stub External System.
4. Mark the tests `@Disabled("RED 6 - Contract Tests")`.
5. STOP. Present the contract tests to the user and ask for approval. Do NOT continue.

## GREEN 1 - Stubs

1. Enable the tests marked `@Disabled("RED 6 - Contract Tests")`.
2. Implement the External System Stubs.
3. Execute `./Run-SystemTests.ps1 -Rebuild -SkipTests`.
4. Verify that the External System Contract Tests pass. If they fail, fix and repeat.
5. STOP. Present the stub implementation to the user and ask for approval. Do NOT continue.

## GREEN 2 - System

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

## GREEN 3 - Enable Tests

1. Remove the `@Disabled` annotation from the tests.
2. Run the tests and verify they all pass.
3. Ensure that there are no non-test files in the list of changed files.
4. Commit the changes.
