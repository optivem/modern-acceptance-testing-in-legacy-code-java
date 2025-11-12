# Acceptance Testing in Legacy Code - Java

E-Shop system with acceptance testing for legacy code scenarios.

## Project Structure

```
├── monolith/              # Main e-shop application
├── system-test/           # System/acceptance tests
├── docker-compose.yml     # Default compose (uses local)
├── docker-compose.local.yml    # Local development (builds from source)
├── docker-compose.pipeline.yml # CI/CD (uses pre-built images)
├── json-server-db.erp-api.json # Mock ERP API data
└── run.ps1               # Orchestration script with health checks
```

## Quick Start

### Prerequisites

- Java 21
- Docker Desktop
- PowerShell 7+

### Run Everything

```powershell
.\run.ps1 all
```

This will:
1. Build the monolith
2. Start Docker containers (ERP API + Monolith)
3. Wait for services to be healthy
4. Run all system tests
5. Show logs

## Commands

### Start Services
```powershell
# Local mode (builds from source)
.\run.ps1 start

# Pipeline mode (uses pre-built image)
.\run.ps1 start pipeline
```

### Run Tests
```powershell
.\run.ps1 test
```

### View Logs
```powershell
.\run.ps1 logs
```

### Stop Services
```powershell
.\run.ps1 stop
```

## Services

| Service | Port | Description |
|---------|------|-------------|
| ERP API | 3000 | Mock external ERP system providing product prices |
| Monolith | 8080 | E-shop application |

## Testing

The system includes:
- **Smoke Tests**: Basic connectivity and health checks
- **E2E Tests (API)**: Order management via REST API
- **E2E Tests (UI)**: Order management via web interface

Test reports are generated at: `system-test/build/reports/tests/test/index.html`

## Modes

**Local Mode (default)**
- Builds monolith from source code
- Best for development

**Pipeline Mode**
- Uses pre-built Docker image from registry
- Best for CI/CD pipelines

## Architecture

The system uses Docker Compose to orchestrate:
- **monolith**: Spring Boot application
- **erp-api**: JSON Server providing mock product data

Both services communicate via Docker's default network, with the monolith calling `http://erp-api:3000` to fetch product prices.

## Development Workflow

1. Make code changes in `monolith/`
2. Run `.\run.ps1 all` to rebuild and test
3. Check test results
4. View logs if needed: `.\run.ps1 logs`
5. Stop when done: `.\run.ps1 stop`

## Troubleshooting

### Services won't start
```powershell
docker compose down -v
.\run.ps1 start
```

### Tests fail
Check the logs for errors:
```powershell
.\run.ps1 logs
```

### Port conflicts
Ensure ports 3000 and 8080 are not in use:
```powershell
netstat -ano | findstr :8080
netstat -ano | findstr :3000
```

