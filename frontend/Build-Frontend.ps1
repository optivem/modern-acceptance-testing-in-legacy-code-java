function Build-Frontend {
    Set-Location frontend
    if (-not (Test-Path "node_modules")) {
        Execute-Command -Command "npm install"
    }
    Set-Location ..

    Execute-Command -Command "npm run build" -SubFolder "frontend"
}