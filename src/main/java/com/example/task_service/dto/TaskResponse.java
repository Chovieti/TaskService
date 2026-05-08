package com.example.task_service.dto;

import com.example.task_service.model.TaskStatus;

import java.util.UUID;

public record TaskResponse(
        UUID id,
        String title,
        String description,
        TaskStatus status,
        UUID assigneeId
) {}
