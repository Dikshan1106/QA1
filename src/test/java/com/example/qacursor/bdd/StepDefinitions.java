package com.example.qacursor.bdd;

import com.example.qacursor.task.Task;
import com.example.qacursor.task.TaskService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@io.cucumber.spring.CucumberContextConfiguration
@SpringBootTest
public class StepDefinitions {

    @Autowired
    private TaskService taskService;

    @Given("no tasks exist")
    public void no_tasks_exist() {
        taskService.clearAll();
    }

    @When("I add a task with title {string} and description {string}")
    public void i_add_a_task(String title, String description) {
        taskService.addTask(new Task(null, title, description, false));
    }

    @Then("the task list should contain a task titled {string}")
    public void the_task_list_should_contain(String title) {
        boolean found = taskService.listTasks().stream().anyMatch(t -> title.equals(t.getTitle()));
        assertTrue(found, "Expected to find task with title " + title);
    }
}


