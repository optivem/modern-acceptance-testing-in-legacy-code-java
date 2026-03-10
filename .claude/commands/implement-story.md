Implement the following user story using the multi-agent ATDD workflow defined in `docs/prompts/atdd/workflow.md`.

Input: $ARGUMENTS

The input is either a GitHub issue number (e.g. `#42`) or free-text user story. Pass it as-is to the story-agent — it will handle both formats.

## Orchestration Steps

1. Launch **story-agent** with the input. It will read the GitHub issue if given a number, or use the text directly. Present the Gherkin scenarios it produces and wait for human approval before continuing.

Steps 2–8 below form a **per-scenario loop**. Repeat them for each scenario until all are GREEN.

2. Launch **test-agent** with the approved Gherkin (or the remaining scenarios if looping). It will complete RED 1 autonomously.
   - If multiple scenarios remain and new DSL is needed, it will implement only the first and leave the rest as `// TODO:` comments. Note which scenarios remain for subsequent loops.

3. Launch **dsl-agent**. It will complete RED 2 autonomously.

4. Launch **driver-agent**. It will complete RED 3 autonomously.
   - If it reports a stub failure (external system stub does not support the operation), follow the contract-tests process in `docs/prompts/atdd/contract-tests.md` before continuing.

5. Launch **backend-agent**. It will implement the backend until API tests pass.

6. Launch **frontend-agent**. It will implement the frontend until UI tests pass.

7. Launch **release-agent**. It will remove @Disabled and commit the final GREEN.

8. If there are remaining `// TODO:` scenarios in the test file, loop back to step 2 for the next scenario. Otherwise, present the final outcome to the user for review.

## Escalation

If any agent reports it cannot proceed (stuck, unexpected pattern, test failure it cannot explain), STOP and present the blocker to the user before continuing.
