# Modern Acceptance Testing in Legacy Code (Java)

[![commit-stage-monolith](https://github.com/optivem/modern-acceptance-testing-in-legacy-code-java/actions/workflows/commit-stage-monolith.yml/badge.svg)](https://github.com/optivem/modern-acceptance-testing-in-legacy-code-java/actions/workflows/commit-stage-monolith.yml)
[![acceptance-stage](https://github.com/optivem/modern-acceptance-testing-in-legacy-code-java/actions/workflows/acceptance-stage.yml/badge.svg)](https://github.com/optivem/modern-acceptance-testing-in-legacy-code-java/actions/workflows/acceptance-stage.yml)
[![qa-stage](https://github.com/optivem/modern-acceptance-testing-in-legacy-code-java/actions/workflows/qa-stage.yml/badge.svg)](https://github.com/optivem/modern-acceptance-testing-in-legacy-code-java/actions/workflows/qa-stage.yml)
[![qa-signoff](https://github.com/optivem/modern-acceptance-testing-in-legacy-code-java/actions/workflows/qa-signoff.yml/badge.svg)](https://github.com/optivem/modern-acceptance-testing-in-legacy-code-java/actions/workflows/qa-signoff.yml)
[![prod-stage](https://github.com/optivem/modern-acceptance-testing-in-legacy-code-java/actions/workflows/prod-stage.yml/badge.svg)](https://github.com/optivem/modern-acceptance-testing-in-legacy-code-java/actions/workflows/prod-stage.yml)

## Prerequisites

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

Check that you have Powershell 7

```shell
$PSVersionTable.PSVersion
```

## Run Everything

```powershell
.\run.ps1 all
```

This will:
1. Build the Monolith
2. Start Docker containers (Monolith & Simulated External Systems)
3. Wait for services to be healthy
4. Run all System Tests

You can open these URLs in your browser:
- Monolith Application: [http://localhost:8080](http://localhost:8080)
- ERP API (JSON Server): [http://localhost:3000](http://localhost:3000)
- Tax API (JSON Server): [http://localhost:3001](http://localhost:3001)

## Separate Commands

### Start Services
```powershell
# Local mode (builds from source)
.\run.ps1 start

# Pipeline mode (uses pre-built image)
.\run.ps1 start pipeline
```

You can open these URLs in your browser:
- Monolith Application: [http://localhost:8080](http://localhost:8080)
- ERP API (JSON Server): [http://localhost:3000](http://localhost:3000)
- Tax API (JSON Server): [http://localhost:3001](http://localhost:3001)

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

## License

[![MIT License](https://img.shields.io/badge/license-MIT-lightgrey.svg)](https://opensource.org/licenses/MIT)

This project is released under the [MIT License](https://opensource.org/licenses/MIT).

## Contributors

- [Valentina Jemuović](https://github.com/valentinajemuovic)
- [Jelena Cupać](https://github.com/jcupac)
