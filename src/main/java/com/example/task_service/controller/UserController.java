package com.example.task_service.controller;

import com.example.task_service.dto.CreateUserRequest;
import com.example.task_service.dto.UserResponse;
import com.example.task_service.model.User;
import com.example.task_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UUID> createUser(@Valid @RequestBody CreateUserRequest request) {
        UUID idCreatedUser = service.createUser(request.name(), request.email());
        return ResponseEntity.ok(idCreatedUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("userId") UUID id) {
        if (id == null) throw new IllegalArgumentException();
        User user = service.getUserById(id);
        return ResponseEntity.ok(new UserResponse(user.getName(), user.getEmail()));
    }
}
