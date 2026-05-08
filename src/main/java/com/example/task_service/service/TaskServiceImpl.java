package com.example.task_service.service;

import com.example.task_service.exception.TaskNotFoundException;
import com.example.task_service.model.Task;
import com.example.task_service.model.TaskStatus;
import com.example.task_service.repository.TaskRepository;
import com.example.task_service.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UUID createTask(String title, String description) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(TaskStatus.CREATED);
        return taskRepository.save(task).getId();
    }

    @Override
    public Task getTaskById(UUID id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    // TODO Разобраться с pageable
    @Override
    public Page<Task> getTasks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Task assignTask(UUID taskId, UUID userId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        userRepository.findById(userId).orElseThrow(() -> new TaskNotFoundException(userId));
        task.setAssigneeId(userId);
        return taskRepository.save(task);
    }

    @Transactional
    @Override
    public Task changeStatus(UUID id, TaskStatus status) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        task.setStatus(status);
        return taskRepository.save(task);
    }
}
