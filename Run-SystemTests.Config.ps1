# System Test Configuration
# This file contains configuration values for Run-SystemTests.ps1

$Config = @{
    Tests = @(
        @{  Id = "smoke";
            Name = "Smoke Tests";
            Command = "& .\gradlew.bat :smoke-test:clean :smoke-test:test";
            Path = "system-test";
            TestReportPath = "system-test\smoke-test\build\reports\tests\test\index.html"
            TestInstallCommands = $null; },
        @{ 
            Id = "e2e";
            Name = "E2E Tests";
            Command = "& .\gradlew.bat :e2e-test:clean :e2e-test:test";
            Path = "system-test";
            TestReportPath = "system-test\e2e-test\build\reports\tests\test\index.html";
            TestInstallCommands = $null;  }
    )
}

# Export the configuration
return $Config

