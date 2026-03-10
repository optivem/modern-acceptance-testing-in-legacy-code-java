# ATDD Rules

## Phase Progression

Proceed to the next phase automatically **unless** the current phase ends with **STOP**. When a phase ends with STOP, wait for the user to explicitly approve before continuing. If the user says something other than approval after a STOP, ask clarifying questions — do not execute the next phase.

---

## Suite Selection

Each acceptance test is annotated with a channel. Use the matching suite placeholder throughout all phases:
- `<acceptance-api>` — for tests annotated with `@Channel(API)`
- `<acceptance-ui>` — for tests annotated with `@Channel(UI)`

If a test covers both channels, run both suites.

## RED 1 - Acceptance Tests (WRITE)

1. Write the acceptance tests, following these rules:
   - Write acceptance tests only — do not implement anything.
   - Each Gherkin scenario maps directly to one test method — one-to-one, no interpretation needed.
   - Only specify the minimum data needed — inputs directly relevant to what is being tested, and assertions directly relevant to the expected outcome. Do not add extra fields, extra assertions, or noise. If a field is not relevant to the scenario being tested, omit it entirely and let the DSL use its default value.
   - If the DSL needs to be extended with new methods, call them directly in the test as if they exist — do not add them to the DSL interface yet. Compile errors are expected and intentional.
   - **Single-scenario focus (new DSL needed):** Before writing any tests, determine whether new DSL methods are needed. If yes, write **only the first scenario** — one test method and nothing else. For every remaining scenario add a single line `// TODO: <Scenario Name>` — no method body, no placeholder code. Do NOT write more than one test method when new DSL is needed. This is a hard rule. Subsequent cycles will add the remaining scenarios after this one reaches GREEN.
   - **Batch (existing DSL only):** If all scenarios can be written using only existing DSL methods (no new methods needed), implement all of them together in one cycle.
   - After writing each test, verify it matches the acceptance criteria exactly — Given maps to Given, When maps to When, Then maps to Then. Every precondition stated in the scenario must appear in the test. If anything is unclear, ask before proceeding.
2. Run the tests and verify they fail (compile error is expected if new DSL methods are needed):
   ```
   .\Run-SystemTests.ps1 -Suite <acceptance-api> -Test <TestMethodName>
   .\Run-SystemTests.ps1 -Suite <acceptance-ui> -Test <TestMethodName>
   ```
3. STOP. Present the tests to the user and ask for approval. Do NOT continue.

## RED 1 - Acceptance Tests (COMMIT)

1. If there were compile-time errors in RED 1 (WRITE):
   a. Extend the DSL interfaces with the new methods.
   b. Implement the new methods by throwing `UnsupportedOperationException("TODO: DSL")` — do not implement DSL.
   c. Run the tests and verify they fail with a runtime error:
      ```
      .\Run-SystemTests.ps1 -Suite <acceptance-api> -Test <TestMethodName>
      .\Run-SystemTests.ps1 -Suite <acceptance-ui> -Test <TestMethodName>
      ```
2. Mark the tests as `@Disabled("RED 1 - Tests")`.
3. COMMIT with message `<Scenario> | RED 1 - Tests`.
4. STOP. Do not proceed further. Phase progression is controlled by the orchestrator, not by this agent.

## RED 2 - DSL (WRITE)

1. Enable the tests marked `@Disabled("RED 1 - Tests")`.
2. Implement the DSL for real — replace `UnsupportedOperationException("TODO: DSL")` with actual logic.
3. Update the Driver interfaces as needed.
4. Check whether any new or changed driver interfaces are in an `external/` package (e.g. `driver-port/.../external/clock`). Set a flag: **external system interfaces changed = yes/no**.
5. STOP. Present the DSL implementation, Driver interface changes, and the external system interfaces flag to the user and ask for approval. Do NOT continue.

## RED 2 - DSL (COMMIT)

1. Implement the Drivers by throwing `UnsupportedOperationException("TODO: Driver")`.
2. Run the tests and verify they fail with a runtime error:
   ```
   .\Run-SystemTests.ps1 -Suite <acceptance-api> -Test <TestMethodName>
   .\Run-SystemTests.ps1 -Suite <acceptance-ui> -Test <TestMethodName>
   ```
3. Mark the tests as `@Disabled("RED 2 - DSL")`.
4. Ensure that there are no test files in the list of changed files.
5. COMMIT with message `<Scenario> | RED 2 - DSL`.
6. Automatically proceed to RED 3 (WRITE).

## RED 3 - Driver (WRITE)

1. Enable the tests marked `@Disabled("RED 2 - DSL")`.
2. Implement the Drivers — replace `UnsupportedOperationException("TODO: Driver")` with actual logic.
   - Only look at files in the `driver-adapter` and `driver-port` directories.
   - Do NOT read or search backend/frontend source code. Model the new method on existing driver methods in the same file.
3. Run the tests and verify they fail with a runtime error.
4. STOP. Present the Driver implementation to the user and ask for approval. Do NOT continue.

## RED 3 - Driver (COMMIT)

1. Mark the tests as `@Disabled("RED 3 - Driver")`.
2. Ensure no test files are in the list of changed files.
3. COMMIT with message `<Scenario> | RED 3 - Driver`.
4. STOP. Do not proceed further. Phase progression is controlled by the orchestrator, not by this agent.

_See `contract-tests.md` for the RED 1 - Contract Tests and GREEN - External System Stubs phases (triggered by the orchestrator when RED 2 reported external system interface changes)._

## GREEN 2 - System (WRITE)

1. Implement the backend:
   a. Implement the backend changes.
   b. Run acceptance tests for the API channel:
      ```
      .\Run-SystemTests.ps1 -Suite <acceptance-api> -Test <TestMethodName> -Rebuild
      ```
   c. If tests fail, fix the backend until the tests pass.
   d. If you have challenges making the tests pass, ask the user.
   e. Do NOT change the tests/dsl/drivers — change only the backend code.
2. Implement the frontend:
   a. Implement the frontend changes.
   b. Run acceptance tests for the UI channel:
      ```
      .\Run-SystemTests.ps1 -Suite <acceptance-ui> -Test <TestMethodName> -Rebuild
      ```
   c. If tests fail, fix the frontend until the tests pass.
   d. If you have challenges making the tests pass, ask the user.
   e. Do NOT change the tests/dsl/drivers — change only the frontend code.
3. By now, all acceptance tests should be passing.
4. STOP. Present the implementation to the user and ask for approval. Do NOT continue.

## GREEN 2 - System (COMMIT)

1. Remove the `@Disabled("RED 3 - Driver")` annotation from the tests.
2. Run the tests and verify they all pass:
   ```
   .\Run-SystemTests.ps1 -Suite <acceptance-api> -Test <TestMethodName>
   .\Run-SystemTests.ps1 -Suite <acceptance-ui> -Test <TestMethodName>
   ```
3. Ensure that there are no non-test files in the list of changed files.
4. COMMIT with message `<Scenario> | GREEN - System`.
5. If there are remaining `// TODO:` scenarios in the test file, return to RED 1 (WRITE) for the next scenario.
