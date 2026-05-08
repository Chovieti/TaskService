package com.example.task_service.dto;

import jakarta.validation.constraints.NotNull;

public record CreateTaskRequest(@NotNull String title, @NotNull String description) {}
