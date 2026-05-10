package com.example.task_service.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank String name, @NotBlank String email
) {}
