function Build-Backend {
    Write-Host "Building backend..." -ForegroundColor Cyan
    Execute-Command -Command "& .\gradlew.bat clean build" -SubFolder "backend"
}