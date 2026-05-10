package com.example.task_service.mapper;

import com.example.task_service.dto.response.TaskResponse;
import com.example.task_service.model.Task;

public class TaskMapper {
    public static TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getAssigneeId()
        );
    }
}
