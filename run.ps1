param(
    [Parameter(Mandatory=$true, Position=0)]
    [ValidateSet("build", "start", "test", "stop", "logs", "all")]
    [string]$Command,

    [Parameter(Position=1)]
    [ValidateSet("local", "pipeline")]
    [string]$Mode = "local"
)

# Constants - Service URLs
$FrontendUrl = "http://localhost:3001"
$BackendUrl = "http://localhost:8081/health"
$ErpApiUrl = "http://localhost:9001/erp/health"
$TaxApiUrl = "http://localhost:9001/tax/health"
$PostgresHost = "localhost:5401"

# Constants - Configuration
$ContainerName = "modern-acceptance-testing-in-legacy-code-java"
$TestReportPath = "system-test\build\reports\tests\test\index.html"

# Script Configuration
$ErrorActionPreference = "Continue"
$ComposeFile = if ($Mode -eq "pipeline") { "docker-compose.pipeline.yml" } else { "docker-compose.local.yml" }

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
    Wait-ForService -Url $ErpApiUrl -ServiceName "ERP API" -ContainerName "external" -LogLines 20
    Wait-ForService -Url $TaxApiUrl -ServiceName "Tax API" -ContainerName "external" -LogLines 20
    Wait-ForService -Url $BackendUrl -ServiceName "Backend API" -ContainerName "backend" -LogLines 50
    Wait-ForService -Url $FrontendUrl -ServiceName "Frontend" -ContainerName "frontend" -LogLines 50
}

function Build-Backend {
    Execute-Command -Command "& .\gradlew.bat clean build" -SubFolder "backend"
}

function Build-Frontend {
    Set-Location frontend
    if (-not (Test-Path "node_modules")) {
        Execute-Command -Command "npm install"
    }
    Set-Location ..

    Execute-Command -Command "npm run build" -SubFolder "frontend"
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

    Write-Host "- Frontend UI: " -NoNewline
    Write-Host $FrontendUrl -ForegroundColor Yellow
    Write-Host "- Backend API Health: " -NoNewline
    Write-Host $BackendUrl -ForegroundColor Yellow
    Write-Host "- ERP API Health: " -NoNewline
    Write-Host $ErpApiUrl -ForegroundColor Yellow
    Write-Host "- Tax API Health: " -NoNewline
    Write-Host $TaxApiUrl -ForegroundColor Yellow
    Write-Host "- PostgreSQL Host: " -NoNewline
    Write-Host $PostgresHost -ForegroundColor Yellow
    Write-Host ""
    Write-Host "To view logs: " -NoNewline
    Write-Host ".\run.ps1 logs $Mode" -ForegroundColor Cyan
    Write-Host "To stop: " -NoNewline
    Write-Host ".\run.ps1 stop $Mode" -ForegroundColor Cyan
}

function Test-System {
    Execute-Command -Command "& .\gradlew.bat clean test" -SubFolder "system-test"

    Write-Host ""
    Write-Host "All tests passed!" -ForegroundColor Green
    Write-Host "Test report: " -NoNewline
    Write-Host $TestReportPath -ForegroundColor Yellow
}

function Show-Logs {
    Execute-Command -Command "docker compose -f $ComposeFile logs --tail=100 -f"
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

function Run-All {
    Write-Heading -Text "Build System"
    Build-System

    Write-Heading -Text "Stop System"
    Stop-System

    Write-Heading -Text "Start System"
    Start-System

    Write-Heading -Text "Wait for System"
    Wait-ForServices

    Write-Heading -Text "Test System"
    Test-System

    Write-Heading -Text "DONE" -Color Green
}

# Main execution
try {
    switch ($Command) {
        "build" { Build-System }
        "start" { Start-System }
        "test"  { Test-System }
        "stop"  { Stop-System }
        "logs"  { Show-Logs }
        "all"   { Run-All }
    }
} catch {
    Write-Host ""
    Write-Host "ERROR: $_" -ForegroundColor Red
    exit 1
}

# Explicit exit with code 0 on success
exit 0


