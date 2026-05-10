package com.example.task_service.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(
        @NotNull String name, @NotNull String email
) {}
