package com.example.task_service.controller;

import com.example.task_service.dto.CreateTaskRequest;
import com.example.task_service.dto.TaskResponse;
import com.example.task_service.model.Task;
import com.example.task_service.model.TaskStatus;
import com.example.task_service.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UUID> createTask(@Valid @RequestBody CreateTaskRequest request) {
        UUID idCreatedTask = service.createTask(request.title(), request.description());
        return ResponseEntity.ok(idCreatedTask);
    }
    // TODO Запрос для получения задачи по id
    @GetMapping("/{id}")
    public ResponseEntity<Void> getTaskById() {
        return ResponseEntity.ok().build();
    }
    // TODO Запрос для получения задач с пагинацией, разобраться подробнее и доделать(нужно будет ещё мапить)
    @GetMapping
    public Page<Task> getTasksPage() {
        return service.getTasks(0, 10);
    }
    // TODO Запрос на назначение исполнителя задаче
    @PatchMapping("/{id}/assign/{userId}")
    public ResponseEntity<Void> assignTask() {
        return ResponseEntity.ok().build();
    }
    // TODO Запрос смена статуса задаче
    // TODO Проверить работу!!!
    @PatchMapping("/{id}/{status}")
    public ResponseEntity<TaskResponse> changeTaskStatus(@PathVariable("id") UUID id, @PathVariable("status")TaskStatus status) {
        Task task = service.changeStatus(id, status);
        return ResponseEntity.ok(new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getAssigneeId())
        );
    }
}
