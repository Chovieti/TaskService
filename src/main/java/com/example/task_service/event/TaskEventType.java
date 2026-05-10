package com.example.task_service.event;

public enum TaskEventType {
    TASK_CREATED, TASK_ASSIGNED, TASK_STATUS_CHANGED;

    public static TaskEventType fromString(String type) {
        try {
            return TaskEventType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown event type: " + type);
        }
    }
}
