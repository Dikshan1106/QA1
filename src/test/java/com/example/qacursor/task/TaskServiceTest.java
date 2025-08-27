package com.example.qacursor.task;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {
    private TaskService service;

    @BeforeEach
    void setup() {
        service = new TaskService();
        service.clearAll();
    }

    @Test
    void addTask_shouldAssignId_andStore() {
        Task created = service.addTask(new Task(null, "Write tests", "TDD first", false));
        assertNotNull(created.getId());
        assertEquals("Write tests", created.getTitle());
        assertFalse(created.isCompleted());
        assertEquals(1, service.listTasks().size());
    }

    @Test
    void addTask_shouldRejectBlankTitle() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> service.addTask(new Task(null, "  ", "desc", false)));
        assertTrue(ex.getMessage().contains("Title is required"));
    }

    @Test
    void completeTask_shouldMarkCompleted() {
        Task created = service.addTask(new Task(null, "Do it", null, false));
        Task updated = service.completeTask(created.getId());
        assertTrue(updated.isCompleted());
    }
}


