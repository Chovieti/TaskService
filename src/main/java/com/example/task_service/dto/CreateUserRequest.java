package com.example.task_service.dto;

import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(
        @NotNull String name, @NotNull String email
) {}
