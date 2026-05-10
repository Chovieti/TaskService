package com.example.task_service.event;

import com.example.task_service.model.TaskStatus;

import java.util.UUID;

public record TaskEvent(
        String eventType,
        UUID taskId,
        String title,
        String description,
        TaskStatus status,
        UUID assigneeId
) {}
