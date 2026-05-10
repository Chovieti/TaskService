package com.example.task_service.dto.request;

import com.example.task_service.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;

public record ChangeTaskStatusRequest(@NotBlank TaskStatus status) {}
