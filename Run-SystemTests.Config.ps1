# System Test Configuration
# This file contains configuration values for Run-SystemTests.ps1

$Config = @{

    BuildCommands = @(
        @{  Name = "Clean";
            Command = ".\gradlew.bat clean"
        },
        @{  Name = "Build";
            Command = ".\gradlew.bat build"
        }
    )

    Tests = @(

        # Smoke Tests
        @{  Id = "smoke-local-stub";
            Name = "Smoke Tests (Local - Stub)";
            Command = "& .\gradlew.bat :smoke-test:test -Denvironment=local -DexternalSystemMode=stub";
            Path = "system-test";
            TestReportPath = "system-test\smoke-test\build\reports\tests\test\index.html"
            TestInstallCommands = $null; },
        @{  Id = "smoke-local-real";
            Name = "Smoke Tests (Local - Real)";
            Command = "& .\gradlew.bat :smoke-test:test -Denvironment=local -DexternalSystemMode=real";
            Path = "system-test";
            TestReportPath = "system-test\smoke-test\build\reports\tests\test\index.html"
            TestInstallCommands = $null; },

        # Acceptance Tests
        @{  Id = "acceptance-local-stub";
            Name = "Acceptance Tests (Local - Stub)";
            Command = "& .\gradlew.bat :acceptance-test:test -Denvironment=local";
            Path = "system-test";
            TestReportPath = "system-test\acceptance-test\build\reports\tests\test\index.html"
            TestInstallCommands = $null; },

        # External System Contract Tests
        @{  Id = "contract-local-stub";
            Name = "Contract Tests (Local - Stub)";
            Command = "& .\gradlew.bat :external-system-contract-test:test -Denvironment=local";
            Path = "system-test";
            TestReportPath = "system-test\external-system-contract-test\build\reports\tests\test\index.html"
            TestInstallCommands = $null; },

        # E2E Tests
        @{ 
            Id = "e2e-local-real";
            Name = "E2E Tests (Local - Real)";
            Command = "& .\gradlew.bat :e2e-test:test -Denvironment=local";
            Path = "system-test";
            TestReportPath = "system-test\e2e-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; }

    )
}

# Export the configuration
return $Config

