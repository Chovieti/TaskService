package com.example.task_service.service.kafka;

import com.example.task_service.event.TaskEvent;
import com.example.task_service.event.TaskEventType;
import com.example.task_service.kafka.KafkaTopics;
import com.example.task_service.model.Task;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TaskEventProducer {
    private final KafkaTemplate<String, TaskEvent> kafkaTemplate;

    public TaskEventProducer(KafkaTemplate<String, TaskEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTaskCreated(Task task) {
        send(new TaskEvent(
                TaskEventType.TASK_CREATED.name(),
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getAssigneeId()
        ));
    }

    public void sendTaskAssigned(Task task) {
        send(new TaskEvent(
                TaskEventType.TASK_ASSIGNED.name(),
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getAssigneeId()
        ));
    }

    public void sendTaskStatusChanged(Task task) {
        send(new TaskEvent(
                TaskEventType.TASK_STATUS_CHANGED.name(),
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getAssigneeId()
        ));
    }

    private void send(TaskEvent event) {
        kafkaTemplate.send(KafkaTopics.TASK_EVENTS, event);
    }
}
