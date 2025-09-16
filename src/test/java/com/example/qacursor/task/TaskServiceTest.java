package com.example.qacursor.task;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.mockito.Mockito;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class TaskServiceTest {
    private TaskService service;
    private TaskRepository repository;
    private Map<Long, Task> store;
    private AtomicLong seq;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(TaskRepository.class);
        store = new ConcurrentHashMap<>();
        seq = new AtomicLong(1);

        when(repository.save(any(Task.class))).thenAnswer((Answer<Task>) invocation -> {
            Task t = invocation.getArgument(0);
            if (t.getId() == null) {
                t.setId(seq.getAndIncrement());
            }
            store.put(t.getId(), t);
            return t;
        });
        when(repository.findAll()).thenAnswer(inv -> new ArrayList<>(store.values()));
        when(repository.findById(anyLong())).thenAnswer(inv -> Optional.ofNullable(store.get(inv.getArgument(0))));
        doAnswer(inv -> { store.clear(); seq.set(1); return null; }).when(repository).deleteAll();


        service = new TaskService(repository);
       //service = new TaskService(); // uncomment for get bug 1 for jira

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


