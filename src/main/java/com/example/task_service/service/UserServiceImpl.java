package com.example.task_service.service;

import com.example.task_service.model.User;
import com.example.task_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    // TODO Обработать ошибки создания
    @Override
    public UUID createUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        return repository.save(user).getId();
    }

    // TODO Заменить runtime на более информативное
    @Override
    public User getUserById(UUID id) {
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }
}
