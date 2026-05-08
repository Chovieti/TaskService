package com.example.task_service.service;

import com.example.task_service.model.Task;
import com.example.task_service.model.TaskStatus;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface TaskService {
    UUID createTask(String title, String description);
    Task getTaskById(UUID id);
    Page<Task> getTasks(int page, int size);
    Task assignTask(UUID taskId, UUID userId);
    Task changeStatus(UUID id, TaskStatus status);
}
