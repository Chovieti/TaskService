package com.example.task_service.event;

import java.util.UUID;

public record TaskEvent(
        String eventType,
        UUID taskId,
        String title,
        String description,
        String status,
        UUID assigneeId
) {}
