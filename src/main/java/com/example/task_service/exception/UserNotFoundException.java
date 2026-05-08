package com.example.task_service.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    private final UUID id;

    public UserNotFoundException(UUID id) {
        super("User with id " + id + " not found");
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}