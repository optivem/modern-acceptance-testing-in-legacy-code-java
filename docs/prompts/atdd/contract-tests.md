# Contract Tests Process

_This process is only triggered when RED 3 - Driver (WRITE) reports a stub failure (the external system stub does not yet support the new operation). It is initiated by the orchestrator after RED 3 - Driver (COMMIT)._

_If the External System does not even exist yet, make Smoke Tests pass first._

## RED 3.1 - Contract Tests (WRITE)

1. Write External System Contract Tests.
2. Verify that they pass when executed against the Real External System:
   ```
   .\Run-SystemTests.ps1 -Suite <suite-contract-real> -Test <TestMethodName>
   ```
   If they don't pass, ask the user for support. STOP. Do NOT continue.
3. Verify that they fail when executed against the Stub External System:
   ```
   .\Run-SystemTests.ps1 -Suite <suite-contract-stub> -Test <TestMethodName>
   ```
4. Mark the tests `@Disabled("RED 3.1 - Contract Tests")`.
5. STOP. Present the contract tests to the user and ask for approval. Do NOT continue.

## RED 3.1 - Contract Tests (COMMIT)

1. COMMIT with message `<Scenario> | RED 3.1 - Contract Tests`.
2. STOP. Do not proceed further. Phase progression is controlled by the orchestrator, not by this agent.

## RED 3.2 - External System Stubs (WRITE)

1. Enable the tests marked `@Disabled("RED 3.1 - Contract Tests")`.
2. Implement the External System Stubs.
3. Run the External System Contract Tests:
   ```
   .\Run-SystemTests.ps1 -Suite <suite-contract-stub> -Test <TestMethodName> -Rebuild
   ```
4. Verify that the tests pass. If they fail, ask the user. STOP. Do NOT continue.
5. STOP. Present the stub implementation to the user and ask for approval. Do NOT continue.

## RED 3.2 - External System Stubs (COMMIT)

1. COMMIT with message `<Scenario> | RED 3.2 - External System Stubs`.
2. STOP. Do not proceed further. Phase progression is controlled by the orchestrator, not by this agent.
