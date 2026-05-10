package com.example.task_service.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final UUID id;

    public UserNotFoundException(UUID id) {
        super("User with id " + id + " not found");
        this.id = id;
    }
}