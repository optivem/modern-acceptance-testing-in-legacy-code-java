# Multi-Agent ATDD Workflow

## Overview

This workflow takes a user story and produces a working, tested feature using a pipeline of
specialized agents. Human input is required at exactly two points.

## Human Touchpoints

1. **Gherkin approval** — after the Story Agent produces Gherkin scenarios, the human reviews
   and approves them. This is the only opportunity to correct business intent before implementation begins.
2. **Outcome review** — after all agents complete, the human reviews the working feature.

## Pipeline

```
User Story
    │
    ▼
[Story Agent]      →  Gherkin scenarios           ← HUMAN APPROVES
    │
    ▼
    ┌─────────────────────────────────────────────────────────┐
    │  Per-scenario loop (repeats until all scenarios GREEN)  │
    │                                                         │
    │  [Test Agent]    →  Acceptance tests   RED 1            │
    │      │                                                  │
    │      ▼                                                  │
    │  [DSL Agent]     →  DSL + interfaces   RED 2            │
    │      │                                                  │
    │      ▼                                                  │
    │  [Driver Agent]  →  Drivers            RED 3            │
    │      │                                                  │
    │      ▼                                                  │
    │  [Backend Agent] →  Working backend    GREEN 2          │
    │      │                                                  │
    │      ▼                                                  │
    │  [Frontend Agent]→  Working frontend   GREEN 2          │
    │      │                                                  │
    │      ▼                                                  │
    │  [Release Agent] →  Final commit       GREEN 2 (COMMIT) │
    │      │                                                  │
    │      └── remaining scenarios? ──► loop back             │
    └─────────────────────────────────────────────────────────┘
    │
    ▼
                                                   ← HUMAN REVIEWS OUTCOME
```

### When does the loop apply?

If the story has multiple scenarios and new DSL methods are needed (compile errors in RED 1),
the Test Agent implements only the **first scenario** and leaves the rest as `// TODO:` comments.
Each subsequent loop picks up the next scenario. If all scenarios share existing DSL (no compile
errors), they can all be implemented in a single pass.

## Agent Definitions

### Story Agent
- **Input:** User story in natural language
- **Output:** Gherkin scenarios (Given/When/Then)
- **Rules:** One scenario per acceptance criterion. Minimal, focused — no noise.
- **Handoff:** Present Gherkin to human and wait for approval before proceeding.

### Test Agent
- **Input:** Approved Gherkin scenarios
- **Output:** Committed acceptance tests (`@Disabled("RED 1 - Tests")`)
- **Governed by:** `acceptance-tests.md` — RED 1 (DRAFT) and RED 1 (COMMIT) phases
- **Handoff:** Tests committed, test class name passed to DSL Agent

### DSL Agent
- **Input:** Test class name and failing tests
- **Output:** DSL implementation + driver interfaces committed (`@Disabled("RED 2 - DSL")`)
- **Governed by:** `acceptance-tests.md` — RED 2 phases; `dsl-core.md` for coding rules
- **Handoff:** Driver interface signatures passed to Driver Agent

### Driver Agent
- **Input:** Driver interface signatures and disabled tests
- **Output:** Implemented drivers, tests committed (`@Disabled("RED 3 - Driver")`)
- **Governed by:** `acceptance-tests.md` — RED 3 phases; `driver-port.md` for coding rules
- **Note:** If failure is due to an external system stub, invoke Contract Tests sub-process (`contract-tests.md`) before continuing.
- **Handoff:** Passing driver layer, tests still disabled

### Backend Agent
- **Input:** Driver interfaces, existing backend codebase
- **Output:** Backend changes that make API acceptance tests pass
- **Governed by:** `acceptance-tests.md` — GREEN 2 (backend)
- **Constraint:** Must NOT change tests, DSL, or drivers — only backend code.
- **Handoff:** API acceptance tests passing

### Frontend Agent
- **Input:** Working backend, existing frontend codebase
- **Output:** Frontend changes that make UI acceptance tests pass
- **Governed by:** `acceptance-tests.md` — GREEN 2 (frontend)
- **Constraint:** Must NOT change tests, DSL, or drivers — only frontend code.
- **Handoff:** All acceptance tests passing

### Release Agent
- **Input:** All acceptance tests passing
- **Output:** `@Disabled` removed, final commit `<Scenario> | GREEN - System`
- **Governed by:** `acceptance-tests.md` — GREEN 2 (COMMIT)
- **Handoff:** Present outcome to human for review.

## Escalation Rule

Any agent that encounters a situation not covered by existing patterns must STOP and ask the
human rather than guess. Examples:
- A new abstraction with no existing precedent in the codebase
- A test failure with an unclear cause after one retry
- An ambiguous acceptance criterion that could map to multiple implementations

## Optional Sub-Process

If the Driver Agent's tests fail due to an external system stub not supporting the new operation,
invoke the Contract Tests pipeline defined in `contract-tests.md` before proceeding to GREEN phases.
