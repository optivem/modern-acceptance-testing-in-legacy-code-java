# Contract Tests Process

_This process is only triggered when RED 3 - Driver fails due to an External System Stub that does not yet support the new operation._

_If the External System does not even exist yet, make Smoke Tests pass first._

## RED 4 - Contract Tests (DRAFT)

1. Write External System Contract Tests.
2. Verify that they pass when executed against the Real External System:
   ```
   .\Run-SystemTests.ps1 -Suite <suite-contract-real> -Test <TestMethodName>
   ```
   If they don't pass, then ask the user for support. STOP. Do NOT continue.
3. Verify that they fail when executed against the Stub External System:
   ```
   .\Run-SystemTests.ps1 -Suite <suite-contract-stub> -Test <TestMethodName>
   ```
4. Mark the tests `@Disabled("RED 4 - Contract Tests")`.
5. STOP. Present the contract tests to the user and ask for approval. Do NOT continue.

## RED 4 - Contract Tests (COMMIT)

1. COMMIT the changes with message `<Scenario> | RED 4 - Contract Tests`.
2. Automatically proceed to GREEN 1 (DRAFT).

## GREEN 1 - External System Stubs (DRAFT)

1. Enable the tests marked `@Disabled("RED 4 - Contract Tests")`.
2. Implement the External System Stubs.
3. Execute the External System Contract Tests:
   ```
   .\Run-SystemTests.ps1 -Suite <suite-contract-stub> -Test <TestMethodName> -Rebuild
   ```
4. Verify that the External System Contract Tests pass. If they fail, then ask the user.
5. STOP. Present the stub implementation to the user and ask for approval. Do NOT continue.

## GREEN 1 - External System Stubs (COMMIT)

1. COMMIT with message `<Scenario> | GREEN - External System Stubs`.
2. Automatically proceed to GREEN 2 (DRAFT).
