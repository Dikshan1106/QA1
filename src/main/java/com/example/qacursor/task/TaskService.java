package com.example.qacursor.task;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {
    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    public Task addTask(@Valid Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new ValidationException("Title is required");
        }
        long id = idSequence.getAndIncrement();
        Task toSave = new Task(id, task.getTitle().trim(),
                task.getDescription() == null ? null : task.getDescription().trim(),
                false);
        tasks.put(id, toSave);
        return toSave;
    }

    public List<Task> listTasks() {
        return new ArrayList<>(tasks.values());
    }

    public Optional<Task> getTask(long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public Task completeTask(long id) {
        Task task = tasks.get(id);
        if (task == null) throw new NoSuchElementException("Task not found");
        task.setCompleted(true);
        return task;
    }

    public void clearAll() {
        tasks.clear();
        idSequence.set(1);
    }
}


