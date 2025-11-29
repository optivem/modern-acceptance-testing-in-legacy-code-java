# System Test Configuration
# This file contains configuration values for Run-SystemTests.ps1

$Config = @{
    # Test Configuration
    TestCommand = "& .\gradlew.bat clean test"
    TestReportPath = "system-test\build\reports\tests\test\index.html"
}

# Export the configuration
return $Config

