package com.example.task_service.service;

import com.example.task_service.model.User;

import java.util.UUID;

public interface UserService {
    UUID createUser(String name, String email);
    User getUserById(UUID id);
}
