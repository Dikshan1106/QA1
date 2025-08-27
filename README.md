## QA Cursor Project

Spring Boot demo app covering TDD, BDD, Selenium/UI, API tests, CI, JMeter, and basic security/usability.

### Run locally
- Prereqs: JDK 17, Maven, Chrome installed for Selenium.
- Start app: `mvn spring-boot:run`

### Tests
- Unit tests (JUnit): `mvn test`
- Integration (API + Selenium + Cucumber): `mvn verify`
  - Headless Chrome: run with `-Dheadless=true` and set `SELENIUM_HEADLESS=1` if needed.

### TDD Steps
1. Red: see `TaskServiceTest` initially failing for blank title and id assignment.
2. Green: implemented `TaskService.addTask` minimal logic.
3. Refactor: trimmed inputs, extracted validation, added `completeTask`.

### BDD
- Feature: `src/test/java/com/example/qacursor/bdd/AddTask.feature`
- Steps: `StepDefinitions.java`
- Runner: `CucumberTest.java`

### Selenium UI Scenarios
1. Add Task via form
2. Validation on blank title

### API Tests
- REST Assured integration tests in `TaskApiIT.java` validate create and list endpoints.

### CI
- GitHub Actions workflow in `.github/workflows/ci.yml` builds and runs tests.

### JMeter
- Load test plan: `jmeter/TaskApi.jmx` targeting `/api/tasks`.

### Security (OWASP basics)
- Input validation: Bean Validation on `Task` and server-side checks in `TaskService`.
- Basic XSS prevention: Thymeleaf escapes by default; CSP meta header added in `index.html`.

### SonarQube
- To analyze: `mvn -DskipTests sonar:sonar -Dsonar.projectKey=qa-cursor -Dsonar.host.url=<URL> -Dsonar.login=<TOKEN>`


