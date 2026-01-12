# System Test Configuration
# This file contains configuration values for Run-SystemTests.ps1

$Config = @{

    BuildCommands = @(
        @{  Name = "Clean Build";
            Command = ".\gradlew.bat clean compileJava compileTestJava"
        }
    )

    Tests = @(

        # Smoke Tests
        @{  Id = "smoke-stub";
            Name = "Smoke Tests - Stubbed External Systems";
            Command = "& .\gradlew.bat :system-test:smoke-test:test -Denvironment=local -DexternalSystemMode=stub";
            Path = ".";
            TestReportPath = "system-test\smoke-test\build\reports\tests\test\index.html"
            TestInstallCommands = $null; },
        @{  Id = "smoke-real";
            Name = "Smoke Tests - Real External Systems";
            Command = "& .\gradlew.bat :system-test:smoke-test:test -Denvironment=local -DexternalSystemMode=real";
            Path = ".";
            TestReportPath = "system-test\smoke-test\build\reports\tests\test\index.html"
            TestInstallCommands = $null; },

        # Acceptance Tests
        @{  Id = "acceptance-api";
            Name = "Acceptance Tests - Channel: API";
            Command = "& .\gradlew.bat :system-test:acceptance-test:test -Denvironment=local -DexcludeTags=isolated -Dchannel=API";
            Path = ".";
            TestReportPath = "system-test\acceptance-test\build\reports\tests\test\index.html"
            TestInstallCommands = $null; },
        @{  Id = "acceptance-ui";
            Name = "Acceptance Tests - Channel: UI";
            Command = "& .\gradlew.bat :system-test:acceptance-test:test -Denvironment=local -DexcludeTags=isolated -Dchannel=UI";
            Path = ".";
            TestReportPath = "system-test\acceptance-test\build\reports\tests\test\index.html"
            TestInstallCommands = $null; },
        @{  Id = "acceptance-isolated-api";
            Name = "Acceptance Tests - Isolated - Channel: API";
            Command = "& .\gradlew.bat :system-test:acceptance-test:test -Denvironment=local -DincludeTags=isolated -Dchannel=API";
            Path = ".";
            TestReportPath = "system-test\acceptance-test\build\reports\tests\test\index.html"
            TestInstallCommands = $null; },
        @{  Id = "acceptance-isolated-ui";
            Name = "Acceptance Tests - Isolated - Channel: UI";
            Command = "& .\gradlew.bat :system-test:acceptance-test:test -Denvironment=local -DincludeTags=isolated -Dchannel=UI";
            Path = ".";
            TestReportPath = "system-test\acceptance-test\build\reports\tests\test\index.html"
            TestInstallCommands = $null; },

        # External System Contract Tests
        @{  Id = "contract-stub";
            Name = "Contract Tests - Stubbed External Systems";
            Command = "& .\gradlew.bat :system-test:external-system-contract-test:test -Denvironment=local -DexternalSystemMode=stub";
            Path = ".";
            TestReportPath = "system-test\external-system-contract-test\build\reports\tests\test\index.html"
            TestInstallCommands = $null; },
        @{  Id = "contract-real";
            Name = "Contract Tests - Real External Systems";
            Command = "& .\gradlew.bat :system-test:external-system-contract-test:test -Denvironment=local -DexternalSystemMode=real";
            Path = ".";
            TestReportPath = "system-test\external-system-contract-test\build\reports\tests\test\index.html"
            TestInstallCommands = $null; },

        # E2E Tests
        @{ 
            Id = "e2e-api";
            Name = "E2E Tests - Channel: API";
            Command = "& .\gradlew.bat :system-test:e2e-test:test -Denvironment=local -Dchannel=API";
            Path = ".";
            TestReportPath = "system-test\e2e-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },

        @{
            Id = "e2e-ui";
            Name = "E2E Tests - Channel: UI";
            Command = "& .\gradlew.bat :system-test:e2e-test:test -Denvironment=local -Dchannel=UI";
            Path = ".";
            TestReportPath = "system-test\e2e-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; }

    )
}

# Export the configuration
return $Config

