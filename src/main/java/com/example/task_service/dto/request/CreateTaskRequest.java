package com.example.task_service.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateTaskRequest(@NotBlank String title, @NotBlank String description) {}
