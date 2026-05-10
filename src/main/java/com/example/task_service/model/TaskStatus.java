package com.example.task_service.model;

import java.util.Arrays;

public enum TaskStatus {
    CREATED, IN_PROGRESS, DONE;

    public static TaskStatus fromString(String status) {
        return Arrays.stream(values())
                .filter(v -> v.name().equals(status))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown: " + status));
    }
}
