# Modern Acceptance Testing in Legacy Code (Java)

[![acceptance-stage](https://github.com/optivem/modern-acceptance-testing-in-legacy-code-java/actions/workflows/acceptance-stage.yml/badge.svg)](https://github.com/optivem/modern-acceptance-testing-in-legacy-code-java/actions/workflows/acceptance-stage.yml)
[![qa-stage](https://github.com/optivem/modern-acceptance-testing-in-legacy-code-java/actions/workflows/qa-stage.yml/badge.svg)](https://github.com/optivem/modern-acceptance-testing-in-legacy-code-java/actions/workflows/qa-stage.yml)
[![qa-signoff](https://github.com/optivem/modern-acceptance-testing-in-legacy-code-java/actions/workflows/qa-signoff.yml/badge.svg)](https://github.com/optivem/modern-acceptance-testing-in-legacy-code-java/actions/workflows/qa-signoff.yml)
[![prod-stage](https://github.com/optivem/modern-acceptance-testing-in-legacy-code-java/actions/workflows/prod-stage.yml/badge.svg)](https://github.com/optivem/modern-acceptance-testing-in-legacy-code-java/actions/workflows/prod-stage.yml)

## Prerequisites

- Java 21
- Docker Desktop
- PowerShell 7+
- Node 22+

Ensure you have JDK 21 installed

```shell
java -version
```

Check that JAVA_HOME is set correctly & points to your JDK 21 installation

```shell
echo $env:JAVA_HOME
```

Check that you have Powershell 7

```shell
$PSVersionTable.PSVersion
```

## Run Everything

```powershell
.\Run-SystemTests.ps1
```

This will:
1. Build the Frontend (TypeScript compilation and bundling with Vite)
2. Build the Backend (Java compilation and JAR creation)
3. Start Docker containers (Frontend, Backend, PostgreSQL, & Mock External APIs)
4. Wait for all services to be healthy
5. Run all System Tests (API E2E, UI E2E, and Smoke Tests)

You can open these URLs in your browser:
- **Frontend UI**: [http://localhost:3001](http://localhost:3001)
- **Backend API**: [http://localhost:8081/api](http://localhost:8081/api)
- **ERP API**: [http://localhost:9001/erp/health](http://localhost:9001/erp/health)
- **Tax API**: [http://localhost:9001/tax/health](http://localhost:9001/tax/health)

## Separate Commands

### Run with Local Build (Default)
Builds locally and runs all system tests:
```powershell
.\Run-SystemTests.ps1
# or explicitly:
.\Run-SystemTests.ps1 local
```

### Run with Pipeline Images
Uses pre-built Docker images from registry:
```powershell
.\Run-SystemTests.ps1 pipeline
```

### Quick Test Re-run
Skip build/start phases and just run tests (assumes services are already running):
```powershell
.\Run-SystemTests.ps1 -TestOnly
```

**Note:** The `-TestOnly` flag skips the build, stop, start, and wait phases. Use it when services are already running and you just want to re-run the tests quickly.

### Individual Component Builds
You can also build components separately:

**Backend:**
```powershell
cd backend
.\Build-Backend.ps1
```

**Frontend:**
```powershell
cd frontend
.\Build-Frontend.ps1
```

### Docker Commands
For manual Docker management:

**View Logs:**
```powershell
docker compose -f docker-compose.local.yml logs
# or for pipeline:
docker compose -f docker-compose.pipeline.yml logs
```

**Stop Services:**
```powershell
docker compose -f docker-compose.local.yml down
# or for pipeline:
docker compose -f docker-compose.pipeline.yml down
```

**Service URLs:**

You can open these URLs in your browser:
- **Frontend UI**: [http://localhost:3001](http://localhost:3001)
- **Backend API**: [http://localhost:8081/api](http://localhost:8081/api)
- **ERP API**: [http://localhost:9001/erp/health](http://localhost:9001/erp/health)
- **Tax API**: [http://localhost:9001/tax/health](http://localhost:9001/tax/health)
- **PostgreSQL Database**: localhost:5401 (database: `eshop`, user: `eshop_user`, password: `eshop_password`)



## License

[![MIT License](https://img.shields.io/badge/license-MIT-lightgrey.svg)](https://opensource.org/licenses/MIT)

This project is released under the [MIT License](https://opensource.org/licenses/MIT).

## Contributors

- [Valentina Jemuović](https://github.com/valentinajemuovic)
- [Jelena Cupać](https://github.com/jcupac)
