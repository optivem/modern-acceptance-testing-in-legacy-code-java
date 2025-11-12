Write-Host "Checking if services are healthy..." -ForegroundColor Cyan

$maxAttempts = 30
$attempt = 0
$erpApiReady = $false
$monolithReady = $false

Write-Host "Waiting for ERP API..." -ForegroundColor Yellow
while (-not $erpApiReady -and $attempt -lt $maxAttempts) {
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:3000/products/10" -UseBasicParsing -TimeoutSec 2
        if ($response.StatusCode -eq 200) {
            # Verify the response contains valid product data
            $productData = $response.Content | ConvertFrom-Json
            if ($productData.id -eq 10 -and $productData.price) {
                $erpApiReady = $true
                Write-Host "[OK] ERP API is ready! Product 10 price: `$$($productData.price)" -ForegroundColor Green
            } else {
                Write-Host "  ERP API returned invalid data: $($response.Content)" -ForegroundColor Yellow
                $attempt++
                Start-Sleep -Seconds 1
            }
        }
    } catch {
        $attempt++
        Write-Host "  Attempt $attempt/$maxAttempts - ERP API not ready yet..." -ForegroundColor Gray
        Start-Sleep -Seconds 1
    }
}

if (-not $erpApiReady) {
    Write-Host "[FAIL] ERP API failed to become ready" -ForegroundColor Red
    Write-Host "  Checking ERP API logs..." -ForegroundColor Yellow
    docker compose -f docker-compose.local.yml logs erp-api --tail=20
    exit 1
}

$attempt = 0
Write-Host "Waiting for Monolith API..." -ForegroundColor Yellow
while (-not $monolithReady -and $attempt -lt $maxAttempts) {
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:8080/" -UseBasicParsing -TimeoutSec 2 -ErrorAction SilentlyContinue
        if ($response.StatusCode -eq 200 -or $response.StatusCode -eq 404) {
            $monolithReady = $true
            Write-Host "[OK] Monolith API is ready!" -ForegroundColor Green
        }
    } catch {
        $attempt++
        Write-Host "  Attempt $attempt/$maxAttempts - Monolith API not ready yet..." -ForegroundColor Gray
        Start-Sleep -Seconds 1
    }
}

if (-not $monolithReady) {
    Write-Host "[FAIL] Monolith API failed to become ready" -ForegroundColor Red
    Write-Host "  Checking Monolith logs..." -ForegroundColor Yellow
    docker compose -f docker-compose.local.yml logs monolith --tail=50
    exit 1
}

# Test internal connectivity from monolith to erp-api
Write-Host "Testing internal network connectivity..." -ForegroundColor Yellow
$networkTest = docker compose -f docker-compose.local.yml exec -T monolith wget -q -O- http://erp-api:3000/products/10 2>&1
if ($LASTEXITCODE -eq 0) {
    Write-Host "[OK] Monolith can reach ERP API internally!" -ForegroundColor Green
} else {
    Write-Host "[FAIL] Monolith cannot reach ERP API at http://erp-api:3000" -ForegroundColor Red
    Write-Host "  Network test output: $networkTest" -ForegroundColor Gray
    exit 1
}

# Test that order creation actually works
Write-Host "Testing order creation API..." -ForegroundColor Yellow
try {
    $orderRequest = @{
        productId = "10"
        quantity = "5"
    } | ConvertTo-Json

    $orderResponse = Invoke-WebRequest -Uri "http://localhost:8080/api/orders" `
        -Method POST `
        -Headers @{"Content-Type" = "application/json"} `
        -Body $orderRequest `
        -UseBasicParsing `
        -TimeoutSec 5 `
        -ErrorAction Stop

    Write-Host "[OK] Order creation works! Response: $($orderResponse.StatusCode)" -ForegroundColor Green
} catch {
    Write-Host "[FAIL] Order creation failed!" -ForegroundColor Red
    Write-Host "  Status Code: $($_.Exception.Response.StatusCode.value__)" -ForegroundColor Gray
    Write-Host "  Response Body:" -ForegroundColor Gray
    $errorBody = $_.ErrorDetails.Message
    if ($errorBody) {
        Write-Host "  $errorBody" -ForegroundColor Gray
    }
    Write-Host ""
    Write-Host "  Checking recent monolith logs for the actual error:" -ForegroundColor Yellow
    Write-Host "  Looking for exception messages..." -ForegroundColor Yellow
    docker compose -f docker-compose.local.yml logs monolith --tail=100 | Select-String -Pattern "Exception:|ERROR|Caused by:|Failed to" -Context 2,10
    Write-Host ""
    Write-Host "  Full recent logs:" -ForegroundColor Yellow
    docker compose -f docker-compose.local.yml logs monolith --tail=50
    exit 1
}

Write-Host ""
Write-Host "All services are healthy and ready for testing!" -ForegroundColor Green

