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
        [string]$ErrorMessage,
        [string]$SubFolder = $null
    )

    if ($SubFolder) {
        Write-Host "Changing directory to: $SubFolder" -ForegroundColor Cyan
        Set-Location $SubFolder
    } else {
        Write-Host "Staying in current directory: $(Get-Location)" -ForegroundColor Cyan
    }

    Write-Host "Executing: $Command" -ForegroundColor Cyan
    $Result = Invoke-Expression $Command

    if ($SubFolder) {
        Write-Host "Changing directory to parent" -ForegroundColor Cyan
        Set-Location ..
    }

    Write-Host "$LASTEXITCODE returned from command." -ForegroundColor Cyan
    if ($LASTEXITCODE -ne 0) {
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
        Execute-Command -Command "docker compose -f $ComposeFile logs $ContainerName --tail=$LogLines" -ErrorMessage "Failed to get Docker compose logs"
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
    Execute-Command -Command "& .\gradlew.bat clean build" -SubFolder "backend" -ErrorMessage "Backend build failed!"
}

function Build-Frontend {
    Set-Location frontend
    if (-not (Test-Path "node_modules")) {
        Execute-Command -Command "npm install" -ErrorMessage "Frontend dependency installation failed!"
    }
    Set-Location ..

    Execute-Command -Command "npm run build" -SubFolder "frontend" -ErrorMessage "Frontend build failed!"
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
    Execute-Command "docker compose -f docker-compose.local.yml down 2>`$null" "Error for docker compose down for local"
    Execute-Command "docker compose -f docker-compose.pipeline.yml down 2>`$null" "Error for docker compose down for pipeline"

    $DockerCommand = "docker ps -aq --filter 'name=$ContainerName' 2>`$null"

    $ProjectContainers = Execute-Command -Command $DockerCommand -ErrorMessage "Error retrieving Docker containers!"
    if ($ProjectContainers) {
        Execute-Command "docker stop $ProjectContainers 2>`$null" "Failed to stop project containers"
        Execute-Command "docker rm -f $ProjectContainers 2>`$null" "Failed to remove project containers"
    }

    # Wait to ensure containers are fully stopped and ports are released
    Start-Sleep -Seconds 2
}

function Start-System {
    Execute-Command -Command "docker compose -f $ComposeFile up -d --build" -ErrorMessage "Failed to start Docker containers!"

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
    Execute-Command -Command "& .\gradlew.bat clean test" -SubFolder "system-test" -ErrorMessage "System tests execution failed!"

    Write-Host ""
    Write-Host "All tests passed!" -ForegroundColor Green
    Write-Host "Test report: " -NoNewline
    Write-Host $TestReportPath -ForegroundColor Yellow
}



function Show-Logs {
    Execute-Command -Command "docker compose -f $ComposeFile logs --tail=100 -f" -ErrorMessage "Failed to retrieve Docker logs!"
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


