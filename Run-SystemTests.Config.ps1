# System Test Configuration
# This file contains configuration values for Run-SystemTests.ps1

$Config = @{

    BuildCommands = @(
        @{  Name = "Clean Build";
            Command = ".\gradlew.bat clean compileJava compileTestJava"
        }
    )

    Tests = @(

        # === v1: Raw ===
        @{  Id = "v1-smoke";
            Name = "v1 (raw) - Smoke (real)";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v1 -Dtype=smoke -DexternalSystemMode=real -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },
        @{  Id = "v1-e2e";
            Name = "v1 (raw) - E2E (real)";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v1 -Dtype=e2e -DexternalSystemMode=real -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },

        # === v2: Clients ===
        @{  Id = "v2-smoke";
            Name = "v2 (clients) - Smoke (real)";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v2 -Dtype=smoke -DexternalSystemMode=real -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },
        @{  Id = "v2-e2e";
            Name = "v2 (clients) - E2E (real)";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v2 -Dtype=e2e -DexternalSystemMode=real -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },

        # === v3: Drivers ===
        @{  Id = "v3-smoke";
            Name = "v3 (drivers) - Smoke (real)";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v3 -Dtype=smoke -DexternalSystemMode=real -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },
        @{  Id = "v3-e2e";
            Name = "v3 (drivers) - E2E (real)";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v3 -Dtype=e2e -DexternalSystemMode=real -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },

        # === v4: Channels ===
        @{  Id = "v4-smoke";
            Name = "v4 (channels) - Smoke (real)";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v4 -Dtype=smoke -DexternalSystemMode=real -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },
        @{  Id = "v4-e2e-api";
            Name = "v4 (channels) - E2E (real) - API";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v4 -Dtype=e2e -DexternalSystemMode=real -Dchannel=API -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },
        @{  Id = "v4-e2e-ui";
            Name = "v4 (channels) - E2E (real) - UI";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v4 -Dtype=e2e -DexternalSystemMode=real -Dchannel=UI -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },

        # === v5: App DSL ===
        @{  Id = "v5-smoke";
            Name = "v5 (app dsl) - Smoke (real)";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v5 -Dtype=smoke -DexternalSystemMode=real -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },
        @{  Id = "v5-e2e-api";
            Name = "v5 (app dsl) - E2E (real) - API";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v5 -Dtype=e2e -DexternalSystemMode=real -Dchannel=API -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },
        @{  Id = "v5-e2e-ui";
            Name = "v5 (app dsl) - E2E (real) - UI";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v5 -Dtype=e2e -DexternalSystemMode=real -Dchannel=UI -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },

        # === v6: Scenario DSL ===
        @{  Id = "v6-smoke";
            Name = "v6 (scenario dsl) - Smoke (real)";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v6 -Dtype=smoke -DexternalSystemMode=real -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },
        @{  Id = "v6-e2e-api";
            Name = "v6 (scenario dsl) - E2E (real) - API";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v6 -Dtype=e2e -DexternalSystemMode=real -Dchannel=API -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },
        @{  Id = "v6-e2e-ui";
            Name = "v6 (scenario dsl) - E2E (real) - UI";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v6 -Dtype=e2e -DexternalSystemMode=real -Dchannel=UI -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },

        # === v7: Scenario DSL (full) ===
        @{  Id = "v7-smoke-stub";
            Name = "v7 (scenario dsl) - Smoke (stub)";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v7 -Dtype=smoke -DexternalSystemMode=stub -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },
        @{  Id = "v7-smoke-real";
            Name = "v7 (scenario dsl) - Smoke (real)";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v7 -Dtype=smoke -DexternalSystemMode=real -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },
        @{  Id = "v7-acceptance-api";
            Name = "v7 (scenario dsl) - Acceptance (stub) - API";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v7 -Dtype=acceptance -DexcludeTags=isolated -Dchannel=API -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },
        @{  Id = "v7-acceptance-ui";
            Name = "v7 (scenario dsl) - Acceptance (stub) - UI";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v7 -Dtype=acceptance -DexcludeTags=isolated -Dchannel=UI -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },
        @{  Id = "v7-acceptance-isolated-api";
            Name = "v7 (scenario dsl) - Acceptance Isolated (stub) - API";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v7 -Dtype=acceptance -DincludeTags=isolated -Dchannel=API -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },
        @{  Id = "v7-acceptance-isolated-ui";
            Name = "v7 (scenario dsl) - Acceptance Isolated (stub) - UI";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v7 -Dtype=acceptance -DincludeTags=isolated -Dchannel=UI -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },
        @{  Id = "v7-contract-stub";
            Name = "v7 (scenario dsl) - Contract (stub)";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v7 -Dtype=contract -DexternalSystemMode=stub -Dmode=stub -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },
        @{  Id = "v7-contract-real";
            Name = "v7 (scenario dsl) - Contract (real)";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v7 -Dtype=contract -DexternalSystemMode=real -Dmode=real -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },
        @{  Id = "v7-e2e-real-api";
            Name = "v7 (scenario dsl) - E2E (real) - API";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v7 -Dtype=e2e -DexternalSystemMode=real -Dchannel=API -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; },
        @{  Id = "v7-e2e-real-ui";
            Name = "v7 (scenario dsl) - E2E (real) - UI";
            Command = "& .\gradlew.bat :system-test:test -Dversion=v7 -Dtype=e2e -DexternalSystemMode=real -Dchannel=UI -Denvironment=local";
            Path = ".";
            TestReportPath = "system-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null; }

    )
}

# Export the configuration
return $Config
