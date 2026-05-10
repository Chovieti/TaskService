package com.example.task_service.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AssignTaskRequest(@NotNull UUID userId) {}
