package com.example.qacursor.task;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(@Valid Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new ValidationException("Title is required");
        }
        String title = task.getTitle().trim();
        String description = task.getDescription() == null ? null : task.getDescription().trim();
        Task toPersist = new Task(null, title, description, false);
        return taskRepository.save(toPersist);
    }

    public List<Task> listTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTask(long id) {
        return taskRepository.findById(id);
    }

    public Task completeTask(long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Task not found"));
        task.setCompleted(true);
        return taskRepository.save(task);
    }

    public void clearAll() {
        taskRepository.deleteAll();
    }
}


