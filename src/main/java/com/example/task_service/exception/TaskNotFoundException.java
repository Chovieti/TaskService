package com.example.task_service.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TaskNotFoundException extends RuntimeException {
    private final UUID id;

    public TaskNotFoundException(UUID id) {
        super("Task with id " + id + " not found");
        this.id = id;
    }
}
