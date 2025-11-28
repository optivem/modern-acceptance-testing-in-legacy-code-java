# Modern Acceptance Testing in Legacy Code (Java)

[![frontend-commit-stage](https://github.com/optivem/modern-acceptance-testing-in-legacy-code-java/actions/workflows/frontend-commit-stage.yml/badge.svg)](https://github.com/optivem/modern-acceptance-testing-in-legacy-code-java/actions/workflows/frontend-commit-stage.yml)
[![backend-commit-stage](https://github.com/optivem/modern-acceptance-testing-in-legacy-code-java/actions/workflows/backend-commit-stage.yml/badge.svg)](https://github.com/optivem/modern-acceptance-testing-in-legacy-code-java/actions/workflows/backend-commit-stage.yml)
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

Check that you have Node 22+

```shell
node -v
```

## Run Everything

```powershell
.\Run-SystemTests.ps1 all
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
- **ERP API**: [http://localhost:9011](http://localhost:9011)
- **Tax API**: [http://localhost:9012](http://localhost:9012)

## Separate Commands

### Build
Compiles the code and creates the Docker image (local mode only):
```powershell
.\Run-SystemTests.ps1 build
```

### Start Services
Starts the Docker containers:
```powershell
# Local mode (uses locally built code)
.\Run-SystemTests.ps1 start

# Pipeline mode (uses pre-built image from registry)
.\Run-SystemTests.ps1 start pipeline
```

You can open these URLs in your browser:
- **Frontend UI**: [http://localhost:3001](http://localhost:3001)
- **Backend API**: [http://localhost:8081/api](http://localhost:8081/api)
- **ERP API**: [http://localhost:9011](http://localhost:9011)
- **Tax API**: [http://localhost:9012](http://localhost:9012)
- **PostgreSQL Database**: localhost:5401 (database: `eshop`, user: `eshop_user`, password: `eshop_password`)

### Run Tests
```powershell
.\Run-SystemTests.ps1 test
```

### View Logs
```powershell
.\Run-SystemTests.ps1 logs
```

### Stop Services
```powershell
.\Run-SystemTests.ps1 stop
```

## License

[![MIT License](https://img.shields.io/badge/license-MIT-lightgrey.svg)](https://opensource.org/licenses/MIT)

This project is released under the [MIT License](https://opensource.org/licenses/MIT).

## Contributors

- [Valentina Jemuović](https://github.com/valentinajemuovic)
- [Jelena Cupać](https://github.com/jcupac)
