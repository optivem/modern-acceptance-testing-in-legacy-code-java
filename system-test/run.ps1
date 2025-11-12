param(
    [Parameter(Mandatory=$true, Position=0)]
    [ValidateSet("start", "test", "stop", "logs", "all")]
    [string]$Command,

    [Parameter(Position=1)]
    [ValidateSet("local", "pipeline")]
    [string]$Mode = "local"
)

$ComposeFile = if ($Mode -eq "pipeline") { "docker-compose.pipeline.yml" } else { "docker-compose.local.yml" }

function Start-System {
    if ($Mode -eq "local") {
        Write-Host "Building monolith application..." -ForegroundColor Cyan
        Set-Location ..\monolith

        & .\build.ps1

        if ($LASTEXITCODE -ne 0) {
            Write-Host "Build failed!" -ForegroundColor Red
            exit $LASTEXITCODE
        }

        Write-Host ""
        Set-Location ..\system-test
    }

    Write-Host "Cleaning up any existing containers..." -ForegroundColor Cyan
    docker compose -f docker-compose.yml down 2>$null
    docker compose -f docker-compose.local.yml down 2>$null
    docker compose -f docker-compose.pipeline.yml down 2>$null

    # Wait to ensure containers are fully stopped and ports are released
    Start-Sleep -Seconds 2

    Write-Host "Starting Docker containers (mode: $Mode)..." -ForegroundColor Cyan

    docker compose -f $ComposeFile up -d

    if ($LASTEXITCODE -ne 0) {
        Write-Host ""
        Write-Host "Failed to start Docker containers!" -ForegroundColor Red
        exit $LASTEXITCODE
    }

    Write-Host ""
    Write-Host "Done! Services are starting..." -ForegroundColor Green
    Write-Host "- ERP API: " -NoNewline
    Write-Host "http://localhost:3000" -ForegroundColor Yellow
    Write-Host "- Monolith API: " -NoNewline
    Write-Host "http://localhost:8080" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "To view logs: " -NoNewline
    Write-Host ".\run.ps1 logs $Mode" -ForegroundColor Cyan
    Write-Host "To stop: " -NoNewline
    Write-Host ".\run.ps1 stop $Mode" -ForegroundColor Cyan
}

function Test-System {
    Write-Host "Running tests..." -ForegroundColor Cyan

    & .\gradlew.bat test

    if ($LASTEXITCODE -ne 0) {
        Write-Host ""
        Write-Host "Tests failed!" -ForegroundColor Red
        exit $LASTEXITCODE
    }

    Write-Host ""
    Write-Host "All tests passed!" -ForegroundColor Green
    Write-Host "Test report: " -NoNewline
    Write-Host "build\reports\tests\test\index.html" -ForegroundColor Yellow
}

function Stop-System {
    Write-Host "Stopping Docker containers (mode: $Mode)..." -ForegroundColor Cyan

    docker compose -f $ComposeFile down

    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Host "Services stopped successfully!" -ForegroundColor Green
    } else {
        Write-Host ""
        Write-Host "Error stopping services" -ForegroundColor Red
        exit $LASTEXITCODE
    }
}

function Show-Logs {
    Write-Host "Viewing Docker container logs (mode: $Mode)..." -ForegroundColor Cyan
    Write-Host "Press Ctrl+C to exit" -ForegroundColor Yellow
    Write-Host ""

    docker compose -f $ComposeFile logs --tail=100 -f
}

function Run-All {
    Start-System

    if ($LASTEXITCODE -ne 0) {
        Write-Host ""
        Write-Host "Start failed! Aborting..." -ForegroundColor Red
        exit $LASTEXITCODE
    }

    Write-Host ""
    & .\wait-for-services.ps1

    if ($LASTEXITCODE -ne 0) {
        Write-Host ""
        Write-Host "Services failed to become ready! Aborting..." -ForegroundColor Red
        Stop-System
        exit $LASTEXITCODE
    }

    Test-System

    if ($LASTEXITCODE -ne 0) {
        Write-Host ""
        Write-Host "Tests failed! Stopping services..." -ForegroundColor Red
        Stop-System
        exit $LASTEXITCODE
    }

    Write-Host ""
    Write-Host "================================================" -ForegroundColor Green
    Write-Host "All tasks completed successfully!" -ForegroundColor Green
    Write-Host "================================================" -ForegroundColor Green
    Write-Host ""
    Write-Host "Starting log view..." -ForegroundColor Cyan
    Start-Sleep -Seconds 2

    Show-Logs
}

# Main execution
switch ($Command) {
    "start" { Start-System }
    "test"  { Test-System }
    "stop"  { Stop-System }
    "logs"  { Show-Logs }
    "all"   { Run-All }
}

