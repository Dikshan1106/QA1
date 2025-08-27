# Bug Tracking

## Bug 1: API returns 500 on validation error
- Severity: Major
- Steps to Reproduce:
  1. POST /api/tasks with body `{ "title": " " }`
- Expected: 400 Bad Request with validation message
- Actual: 500 Internal Server Error (before fix)
- Root Cause: `ValidationException` not handled globally
- Fix: Added `GlobalExceptionHandler` to map to 400
- Prevention: Include global exception mapping and tests for invalid inputs

## Bug 2: UI form allows blank title submission
- Severity: Minor
- Steps to Reproduce:
  1. Open `/`, click Add without providing title
- Expected: Client-side prevention and server-side error shown
- Actual: Initial page reloaded without clear message (before adding validation attribute)
- Fix: Added `required` and max length attributes; server-side BindingResult check keeps errors
- Prevention: Keep parity between frontend constraints and backend validation

