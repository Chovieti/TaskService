package com.example.task_service.dto.request;

import com.example.task_service.model.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeTaskStatusRequest(@NotNull TaskStatus status) {}
