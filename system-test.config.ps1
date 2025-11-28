# System Test Configuration
# This file contains configuration values for Run-SystemTests.ps1

# System Configuration
$Config = @{
    # Docker Configuration
    ContainerName = "modern-acceptance-testing-in-legacy-code-java"

    # Service URLs
    FrontendUrl = "http://localhost:3001"
    BackendUrl = "http://localhost:8081/health"
    ErpApiUrl = "http://localhost:9001/erp/health"
    TaxApiUrl = "http://localhost:9001/tax/health"

    # Test Configuration
    TestCommand = "& .\gradlew.bat clean test"
    TestReportPath = "system-test\build\reports\tests\test\index.html"
}

# Export the configuration
return $Config

