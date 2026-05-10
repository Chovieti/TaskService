package com.example.task_service.service.kafka;

import com.example.task_service.event.TaskEvent;
import com.example.task_service.kafka.KafkaTopics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TaskEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public TaskEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(TaskEvent event) {
        kafkaTemplate.send(KafkaTopics.TASK_EVENTS, event);
    }
}
