Feature: Task management
  As a user
  I want to add a new task
  So that I can track my work

  Scenario: Add a basic task
    Given no tasks exist
    When I add a task with title "Study TDD" and description "write tests first"
    Then the task list should contain a task titled "Study TDD"


