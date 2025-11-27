param(
    [Parameter(Mandatory=$true, Position=0)]
    [ValidateSet("build", "start", "test", "stop", "logs", "all")]
    [string]$Command,

    [Parameter(Position=1)]
    [ValidateSet("local", "pipeline")]
    [string]$Mode = "local",

    # Service URLs
    [string]$FrontendUrl = "http://localhost:3001",
    [string]$BackendUrl = "http://localhost:8081",
    [string]$ErpApiUrl = "http://localhost:9101",
    [string]$TaxApiUrl = "http://localhost:9201",
    [string]$PostgresHost = "localhost:5401"
)

# Ensure script stops on errors
$ErrorActionPreference = "Continue"

$ComposeFile = if ($Mode -eq "pipeline") { "docker-compose.pipeline.yml" } else { "docker-compose.local.yml" }

function Assert-Success {
    param(
        [string]$ErrorMessage
    )

    if ($LASTEXITCODE -ne 0) {
        Write-Host ""
        Write-Host $ErrorMessage -ForegroundColor Red
        throw $ErrorMessage
    }
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

    Write-Host "Waiting for $ServiceName on $Url..." -ForegroundColor Yellow

    while (-not $isReady -and $attempt -lt $MaxAttempts) {
        try {
            $response = Invoke-WebRequest -Uri $Url -UseBasicParsing -TimeoutSec 2 -ErrorAction SilentlyContinue
            if ($response.StatusCode -eq 200 -or $response.StatusCode -eq 404) {
                $isReady = $true
                Write-Host "[OK] $ServiceName is responding!" -ForegroundColor Green
            }
        } catch {
            $attempt++
            Write-Host "  Attempt $attempt/$MaxAttempts - $ServiceName not ready yet..." -ForegroundColor Gray
            Start-Sleep -Seconds 1
        }
    }

    if (-not $isReady) {
        Write-Host "[FAIL] $ServiceName failed to become ready" -ForegroundColor Red
        Write-Host "  Checking $ServiceName logs..." -ForegroundColor Yellow
        docker compose -f $ComposeFile logs $ContainerName --tail=$LogLines
        Write-Host ""
        throw "$ServiceName failed to become ready after $MaxAttempts attempts"
    }

    return $true
}

function Wait-ForServices {
    Write-Host "Checking if services are healthy..." -ForegroundColor Cyan

    Wait-ForService -Url $ErpApiUrl -ServiceName "ERP API" -ContainerName "erp-api" -LogLines 20
    Wait-ForService -Url $BackendUrl -ServiceName "Backend API" -ContainerName "backend" -LogLines 50
    Wait-ForService -Url $FrontendUrl -ServiceName "Frontend" -ContainerName "frontend" -LogLines 50

    Write-Host ""
    Write-Host "All services are healthy and ready for testing!" -ForegroundColor Green
    return $true
}

function Build-Backend {
    Write-Host "Building backend application..." -ForegroundColor Cyan
    Set-Location backend

    & .\gradlew.bat clean build
    Assert-Success "Backend build failed!"

    Write-Host ""
    Write-Host "Backend build completed successfully!" -ForegroundColor Green
    Set-Location ..
}

function Build-Frontend {
    Write-Host "Building frontend application..." -ForegroundColor Cyan
    Set-Location frontend

    & .\gradlew.bat clean build
    Assert-Success "Frontend build failed!"

    Write-Host ""
    Write-Host "Frontend build completed successfully!" -ForegroundColor Green
    Set-Location ..
}

function Build-System {
    if ($Mode -eq "local") {
        Build-Backend
        Build-Frontend
        Write-Host ""
    } else {
        Write-Host "Pipeline mode: Skipping build (using pre-built Docker images)" -ForegroundColor Cyan
    }
}

function Start-System {
    Write-Host "Cleaning up any existing containers..." -ForegroundColor Cyan

    # Stop all compose configurations
    docker compose -f docker-compose.yml down 2>$null
    docker compose -f docker-compose.local.yml down 2>$null
    docker compose -f docker-compose.pipeline.yml down 2>$null

    # Force stop and remove ALL containers from this project (even if stuck)
    Write-Host "Removing any stuck project containers..." -ForegroundColor Cyan
    $projectContainers = docker ps -aq --filter "name=modern-acceptance-testing-in-legacy-code-java" 2>$null
    if ($projectContainers) {
        Write-Host "  Found project containers, forcing removal..." -ForegroundColor Yellow
        docker stop $projectContainers 2>$null
        docker rm -f $projectContainers 2>$null
    }

    # Wait to ensure containers are fully stopped and ports are released
    Write-Host "Waiting for containers to fully stop..." -ForegroundColor Cyan
    Start-Sleep -Seconds 2

    Write-Host "Starting Docker containers (mode: $Mode)..." -ForegroundColor Cyan

    docker compose -f $ComposeFile up -d --build
    Assert-Success "Failed to start Docker containers!"

    Write-Host ""
    Write-Host "Done! Services are starting..." -ForegroundColor Green
    Write-Host "- Frontend UI: " -NoNewline
    Write-Host $FrontendUrl -ForegroundColor Yellow
    Write-Host "- Backend API: " -NoNewline
    Write-Host $BackendUrl -ForegroundColor Yellow
    Write-Host "- ERP API: " -NoNewline
    Write-Host $ErpApiUrl -ForegroundColor Yellow
    Write-Host "- Tax API: " -NoNewline
    Write-Host $TaxApiUrl -ForegroundColor Yellow
    Write-Host "- PostgreSQL: " -NoNewline
    Write-Host $PostgresHost -ForegroundColor Yellow
    Write-Host ""
    Write-Host "To view logs: " -NoNewline
    Write-Host ".\run.ps1 logs $Mode" -ForegroundColor Cyan
    Write-Host "To stop: " -NoNewline
    Write-Host ".\run.ps1 stop $Mode" -ForegroundColor Cyan
}

function Test-System {
    Write-Host "Running tests..." -ForegroundColor Cyan

    Set-Location system-test
    & .\gradlew.bat clean test
    $testResult = $LASTEXITCODE
    Set-Location ..

    if ($testResult -ne 0) {
        throw "Tests failed!"
    }

    Write-Host ""
    Write-Host "All tests passed!" -ForegroundColor Green
    Write-Host "Test report: " -NoNewline
    Write-Host "system-test\build\reports\tests\test\index.html" -ForegroundColor Yellow
}

function Stop-System {
    Write-Host "Stopping Docker containers (mode: $Mode)..." -ForegroundColor Cyan

    docker compose -f $ComposeFile down
    Assert-Success "Error stopping services"

    Write-Host ""
    Write-Host "Services stopped successfully!" -ForegroundColor Green
}

function Show-Logs {
    Write-Host "Viewing Docker container logs (mode: $Mode)..." -ForegroundColor Cyan
    Write-Host "Press Ctrl+C to exit" -ForegroundColor Yellow
    Write-Host ""

    docker compose -f $ComposeFile logs --tail=100 -f
}

function Run-All {
    Build-System
    Start-System

    Write-Host ""
    Wait-ForServices

    Test-System


    Write-Host ""
    Write-Host "================================================" -ForegroundColor Green
    Write-Host "All tasks completed successfully!" -ForegroundColor Green
    Write-Host "================================================" -ForegroundColor Green
    Write-Host ""
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
    Write-Host "Error: $_" -ForegroundColor Red
    exit 1
}

# Explicit exit with code 0 on success
exit 0

