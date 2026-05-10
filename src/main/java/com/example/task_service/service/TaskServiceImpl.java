package com.example.task_service.service;

import com.example.task_service.event.TaskEvent;
import com.example.task_service.exception.TaskNotFoundException;
import com.example.task_service.exception.UserNotFoundException;
import com.example.task_service.model.Task;
import com.example.task_service.model.TaskStatus;
import com.example.task_service.repository.TaskRepository;
import com.example.task_service.repository.UserRepository;
import com.example.task_service.service.kafka.TaskEventProducer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskEventProducer eventProducer;

    public TaskServiceImpl(TaskRepository taskRepository,
                           UserRepository userRepository,
                           TaskEventProducer eventProducer) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.eventProducer = eventProducer;
    }

    @Transactional
    @Override
    public UUID createTask(String title, String description) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(TaskStatus.CREATED);
        Task saved = taskRepository.save(task);
        eventProducer.send(
                new TaskEvent(
                        "TASK_CREATED",
                        saved.getId(),
                        saved.getTitle(),
                        saved.getDescription(),
                        saved.getStatus(),
                        null
                )
        );
        return saved.getId();
    }

    @Override
    public Task getTaskById(UUID id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    public Page<Task> getTasks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return taskRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Task assignTask(UUID taskId, UUID userId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        if (!userRepository.existsById(userId)) throw new UserNotFoundException(userId);
        task.setAssigneeId(userId);
        Task saved = taskRepository.save(task);
        eventProducer.send(
                new TaskEvent(
                        "TASK_ASSIGNED",
                        saved.getId(),
                        saved.getTitle(),
                        saved.getDescription(),
                        saved.getStatus(),
                        userId
                )
        );
        return saved;
    }

    @Transactional
    @Override
    public Task changeStatus(UUID id, TaskStatus status) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        task.setStatus(status);
        return task;
    }
}
