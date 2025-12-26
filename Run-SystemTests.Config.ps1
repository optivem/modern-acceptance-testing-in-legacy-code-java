# System Test Configuration
# This file contains configuration values for Run-SystemTests.ps1

$Config = @{

    BuildCommands = @(
        @{  Name = "Clean Build";
            Command = ".\gradlew.bat clean build -x test"
        }
    )

    Tests = @(

        # Smoke Tests
        @{  Id = "smoke-local-stub";
            Name = "Smoke Tests (Local - Stub)";
            Command = "& .\gradlew.bat :system-test:smoke-test:test -Denvironment=local -DexternalSystemMode=stub";
            Path = ".";
            TestReportPath = "system-test\smoke-test\build\reports\tests\test\index.html"
            TestInstallCommands = $null; },
        @{  Id = "smoke-local-real";
            Name = "Smoke Tests (Local - Real)";
            Command = "& .\gradlew.bat :system-test:smoke-test:test -Denvironment=local -DexternalSystemMode=real";
            Path = ".";
            TestReportPath = "system-test\smoke-test\build\reports\tests\test\index.html"
            TestInstallCommands = $null; },

        # Acceptance Tests
        @{  Id = "acceptance-local-stub";
            Name = "Acceptance Tests (Local - Stub)";
            Command = "& .\gradlew.bat :system-test:acceptance-test:test -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\acceptance-test\build\reports\tests\test\index.html"
            TestInstallCommands = $null; },

        # External System Contract Tests
        @{  Id = "contract-local-stub";
            Name = "Contract Tests (Local - Stub)";
            Command = "& .\gradlew.bat :system-test:external-system-contract-test:test -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\external-system-contract-test\build\reports\tests\test\index.html"
            TestInstallCommands = $null; },

        # E2E Tests
        @{ 
            Id = "e2e-local-real";
            Name = "E2E Tests (Local - Real)";
            Command = "& .\gradlew.bat :system-test:e2e-test:test -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\e2e-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; }

    )
}

# Export the configuration
return $Config

