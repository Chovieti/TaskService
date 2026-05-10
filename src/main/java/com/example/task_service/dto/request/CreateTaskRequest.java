package com.example.task_service.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateTaskRequest(@NotNull String title, @NotNull String description) {}
