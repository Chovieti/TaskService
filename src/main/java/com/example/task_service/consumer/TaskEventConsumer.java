package com.example.task_service.consumer;

import com.example.task_service.event.TaskEvent;
import com.example.task_service.kafka.KafkaTopics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TaskEventConsumer {
    private static final Logger logger = LoggerFactory.getLogger(TaskEventConsumer.class);

    @KafkaListener(topics = KafkaTopics.TASK_EVENTS)
    public void listen(TaskEvent event) {
        logger.info("Kafka event received: {}", event);
    }
}
