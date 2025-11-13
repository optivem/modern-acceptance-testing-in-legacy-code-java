# Modern Acceptance Testing in Legacy Code (Java)

[![commit-stage-monolith](https://github.com/optivem/e2e-testing-in-legacy-code-java/actions/workflows/commit-stage-monolith.yml/badge.svg)](https://github.com/optivem/e2e-testing-in-legacy-code-java/actions/workflows/commit-stage-monolith.yml)
[![acceptance-stage](https://github.com/optivem/e2e-testing-in-legacy-code-java/actions/workflows/acceptance-stage.yml/badge.svg)](https://github.com/optivem/e2e-testing-in-legacy-code-java/actions/workflows/acceptance-stage.yml)
[![qa-stage](https://github.com/optivem/e2e-testing-in-legacy-code-java/actions/workflows/qa-stage.yml/badge.svg)](https://github.com/optivem/e2e-testing-in-legacy-code-java/actions/workflows/qa-stage.yml)
[![qa-signoff](https://github.com/optivem/e2e-testing-in-legacy-code-java/actions/workflows/qa-signoff.yml/badge.svg)](https://github.com/optivem/e2e-testing-in-legacy-code-java/actions/workflows/qa-signoff.yml)
[![prod-stage](https://github.com/optivem/e2e-testing-in-legacy-code-java/actions/workflows/prod-stage.yml/badge.svg)](https://github.com/optivem/e2e-testing-in-legacy-code-java/actions/workflows/prod-stage.yml)

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

Ensure you have JDK 21 installed

```shell
java -version
```

Check that JAVA_HOME is set correctly & points to your JDK 21 installation

```shell
echo $env:JAVA_HOME
```

Ensure you have Gradle 8.14 installed

```shell
./gradlew --version
```

Check that you have Powershell 7

```shell
$PSVersionTable.PSVersion
```

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

### Smoke Tests
- **ApiSmokeTest**: Verifies basic API connectivity
- **UiSmokeTest**: Verifies home page loads

### E2E Tests (API)
- **ApiE2eTest**: Tests order management via REST API
  - Place order
  - Get order details
  - Cancel order
  - Validation (negative quantity, non-integer SKU, non-integer quantity)

### E2E Tests (UI)
- **UiE2eTest**: Tests order management via web interface (Playwright)
  - Calculate total order price
  - Retrieve order history
  - Cancel order
  - Validation (negative quantity, non-integer SKU, non-integer quantity)

### Test Data

Tests use the following SKUs (string identifiers defined in `json-server-db.erp-api.json`):
- **SKU "HP-15"**: HP Pavilion Laptop - $109.95
- **SKU "SAM-2020"**: Samsung Galaxy Book - $499.99
- **SKU "HUA-P30"**: Huawei P30 - $679.99

### Test Configuration

Test configuration is in `system-test/src/test/resources/application.yml`:
- `test.eshop.baseUrl`: Target URL for tests (default: `http://localhost:8080`)
- `test.wait.seconds`: Timeout for UI waits (default: 10 seconds)

### Working on Monolith

**Build directly:**
```powershell
cd monolith
.\gradlew clean build
```

**Run locally (without Docker):**
```powershell
cd monolith
.\gradlew bootRun
```
Application runs on `http://localhost:8080`

**Configuration profiles** (`monolith/src/main/resources/application.yml`):
- `e2e` (default): Uses `http://erp-api:3000` - for Docker environments
- `qa`: Uses `http://erp-api:3000` - for QA environments
- `prod`: Uses `http://erp-api:3000` - for production

### Working on Tests

**Run tests directly:**
```powershell
cd system-test
.\gradlew test
```

**View test report:**
```
system-test/build/reports/tests/test/index.html
```

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

## License

[![MIT License](https://img.shields.io/badge/license-MIT-lightgrey.svg)](https://opensource.org/licenses/MIT)

This project is released under the [MIT License](https://opensource.org/licenses/MIT).

## Contributors

- [Valentina Jemuović](https://github.com/valentinajemuovic)
- [Jelena Cupać](https://github.com/jcupac)
