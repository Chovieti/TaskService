package com.example.task_service.controller;

import com.example.task_service.dto.request.AssignTaskRequest;
import com.example.task_service.dto.request.ChangeTaskStatusRequest;
import com.example.task_service.dto.request.CreateTaskRequest;
import com.example.task_service.dto.response.TaskResponse;
import com.example.task_service.mapper.TaskMapper;
import com.example.task_service.model.Task;
import com.example.task_service.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@Validated
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UUID> createTask(@Valid @RequestBody CreateTaskRequest request) {
        UUID idCreatedTask = service.createTask(request.title(), request.description());
        return ResponseEntity.status(HttpStatus.CREATED).body(idCreatedTask);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable("id") UUID id) {
        Task task = service.getTaskById(id);
        return ResponseEntity.ok(TaskMapper.toResponse(task));
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponse>> getTasksPage(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(50) int size
    ) {
        Page<TaskResponse> tasks = service.getTasks(page, size).map(TaskMapper::toResponse);
        return ResponseEntity.ok(tasks);
    }

    @PatchMapping("/{taskId}/assign")
    public ResponseEntity<TaskResponse> assignTask(@PathVariable("taskId") UUID taskId,
                                                   @Valid @RequestBody AssignTaskRequest request)
    {
        Task task = service.assignTask(taskId, request.userId());
        return ResponseEntity.ok(TaskMapper.toResponse(task));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> changeTaskStatus(@PathVariable("id") UUID id,
                                                         @Valid @RequestBody ChangeTaskStatusRequest request) {
        Task task = service.changeStatus(id, request.status());
        return ResponseEntity.ok(TaskMapper.toResponse(task));
    }
}
