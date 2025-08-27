package com.example.qacursor.task;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("tasks", taskService.listTasks());
        model.addAttribute("task", new Task());
        return "index";
    }

    @PostMapping("/tasks")
    public String addTaskForm(@Valid @ModelAttribute("task") Task task, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tasks", taskService.listTasks());
            return "index";
        }
        taskService.addTask(task);
        return "redirect:/";
    }

    // REST endpoints
    @GetMapping("/api/tasks")
    @ResponseBody
    public List<Task> listTasksApi() {
        return taskService.listTasks();
    }

    @PostMapping("/api/tasks")
    public ResponseEntity<Task> addTaskApi(@RequestBody @Valid Task task) {
        Task created = taskService.addTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/api/tasks/{id}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable long id) {
        Task updated = taskService.completeTask(id);
        return ResponseEntity.ok(updated);
    }
}


