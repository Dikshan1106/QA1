# Software Quality Metrics

## Defect Density
- Module: `task` (service and controller)
- Approx LOC: ~250 (Java + HTML)
- Bugs found: 2 (see BUGS.md)
- Defect Density = 2 / 250 = 0.008 defects/LOC (8 per KLOC)

## Mean Time To Failure (MTTF)
- Assumption: During exploratory testing, failures occurred roughly once every 2 hours of test execution across 4 hours with 2 failures.
- MTTF ≈ 2 hours.
- As stability improves (more tests), expect MTTF to increase.

## SonarQube
- Run analysis:
  - `mvn -DskipTests sonar:sonar -Dsonar.projectKey=qa-cursor -Dsonar.host.url=<URL> -Dsonar.login=<TOKEN>`
- Track:
  - Code Smells: address long methods, missing JavaDoc if enforced
  - Duplications: refactor repeated test setup
  - Vulnerabilities: ensure validation and escaping; add CSP
- Remediation steps taken:
  - Introduced Bean Validation and global exception mapping
  - Ensured Thymeleaf escaping and CSP meta tag

