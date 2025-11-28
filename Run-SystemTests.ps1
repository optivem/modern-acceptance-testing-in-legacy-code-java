param(
    [Parameter(Position=0)]
    [ValidateSet("local", "pipeline")]
    [string]$Mode = "local",

    [switch]$TestOnly
)

# Import build scripts
. .\backend\Build-Backend.ps1
. .\frontend\Build-Frontend.ps1

# Load configuration
$Config = . .\Run-SystemTests.Config.ps1

# Script Configuration
$ErrorActionPreference = "Continue"
$ComposeFile = if ($Mode -eq "pipeline") { "docker-compose.pipeline.yml" } else { "docker-compose.local.yml" }

# Extract configuration values
$ContainerName = $Config.ContainerName
$TestCommand = $Config.TestCommand
$TestReportPath = $Config.TestReportPath

# Extract component arrays
$SystemComponents = $Config.SystemComponents
$ExternalSystems = $Config.ExternalSystems

function Execute-Command {
    param(
        [string]$Command,
        [string]$SubFolder = $null
    )

    if ($SubFolder) {
        Set-Location $SubFolder
    }

    Write-Host "$Command" -ForegroundColor Cyan
    $Result = Invoke-Expression $Command

    if ($SubFolder) {
        Set-Location ..
    }

    if ($LASTEXITCODE -ne 0) {
        $ErrorMessage = "Failed to execute command: $Command"
        throw $ErrorMessage
    }

    return $Result
}

function Wait-ForService {
    param(
        [string]$Url,
        [string]$ServiceName,
        [string]$ContainerName,
        [int]$MaxAttempts = 10,
        [int]$LogLines = 50
    )

    $attempt = 0
    $isReady = $false

    while (-not $isReady -and $attempt -lt $MaxAttempts) {
        try {
            $response = Invoke-WebRequest -Uri $Url -UseBasicParsing -TimeoutSec 2 -ErrorAction SilentlyContinue
            if ($response.StatusCode -eq 200) {
                $isReady = $true
            }
        } catch {
            $attempt++
            Start-Sleep -Seconds 1
        }
    }

    if (-not $isReady) {
        Execute-Command -Command "docker compose -f $ComposeFile logs $ContainerName --tail=$LogLines"
        throw "$ServiceName failed to become ready after $MaxAttempts attempts"
    }
}

function Wait-ForServices {
    Write-Host "Waiting for external systems..." -ForegroundColor Yellow
    foreach ($system in $ExternalSystems) {
        Wait-ForService -Url $system.Url -ServiceName $system.Name -ContainerName $system.ContainerName -LogLines $system.LogLines
    }

    Write-Host "Waiting for system components..." -ForegroundColor Yellow
    foreach ($component in $SystemComponents) {
        Wait-ForService -Url $component.Url -ServiceName $component.Name -ContainerName $component.ContainerName -LogLines $component.LogLines
    }

    Write-Host ""
    Write-Host "All services are ready!" -ForegroundColor Green
}

function Build-System {
    if ($Mode -eq "local") {
        Build-Backend
        Build-Frontend
    } else {
        Write-Host "Pipeline mode: Skipping build (using pre-built Docker images)" -ForegroundColor Cyan
    }
}

function Stop-System {
    Execute-Command -Command "docker compose -f docker-compose.local.yml down 2>`$null"
    Execute-Command -Command "docker compose -f docker-compose.pipeline.yml down 2>`$null"

    $ProjectContainers = Execute-Command -Command "docker ps -aq --filter 'name=$ContainerName' 2>`$null"
    if ($ProjectContainers) {
        Execute-Command -Command "docker stop $ProjectContainers 2>`$null"
        Execute-Command -Command "docker rm -f $ProjectContainers 2>`$null"
    }

    # Wait to ensure containers are fully stopped and ports are released
    Start-Sleep -Seconds 2
}

function Start-System {
    Execute-Command -Command "docker compose -f $ComposeFile up -d --build"

    Write-Host ""
    Write-Host "System Components:" -ForegroundColor Cyan
    foreach ($component in $SystemComponents) {
        Write-Host "- $($component.Name): " -NoNewline
        Write-Host $component.Url -ForegroundColor Yellow
    }

    Write-Host ""
    Write-Host "External Systems:" -ForegroundColor Cyan
    foreach ($system in $ExternalSystems) {
        Write-Host "- $($system.Name): " -NoNewline
        Write-Host $system.Url -ForegroundColor Yellow
    }
}

function Test-System {
    Execute-Command -Command $TestCommand -SubFolder "system-test"

    Write-Host ""
    Write-Host "All tests passed!" -ForegroundColor Green
    Write-Host "Test report: " -NoNewline
    Write-Host $TestReportPath -ForegroundColor Yellow
}

function Write-Heading {
    param(
        [string]$Text,
        [string]$Color = "Cyan"
    )
    Write-Host ""
    Write-Host "================================================" -ForegroundColor $Color
    Write-Host $Text -ForegroundColor $Color
    Write-Host "================================================" -ForegroundColor $Color
    Write-Host ""
}

# Main execution
try {

    if (-not $TestOnly) {
        Write-Heading -Text "Build System"
        Build-System

        Write-Heading -Text "Stop System"
        Stop-System

        Write-Heading -Text "Start System"
        Start-System

        Write-Heading -Text "Wait for System"
        Wait-ForServices
    }

    Write-Heading -Text "Test System"
    Test-System

    Write-Heading -Text "DONE" -Color Green
} catch {
    Write-Host ""
    Write-Host "ERROR: $_" -ForegroundColor Red
    exit 1
}

# Explicit exit with code 0 on success
exit 0