package com.example.task_service.model;

public enum TaskStatus {
    CREATED, IN_PROGRESS, DONE;

    public static TaskStatus fromString(String status) {
        try {
            return TaskStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown event type: " + status);
        }
    }
}
