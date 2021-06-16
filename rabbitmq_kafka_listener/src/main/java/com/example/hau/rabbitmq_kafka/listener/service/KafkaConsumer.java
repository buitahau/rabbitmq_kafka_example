package com.example.hau.rabbitmq_kafka.listener.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "users")
    public void consume(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info("-**Consume Normal------");
        logger.info(String.format("$$$ -> Consumed Message -> %s", message));
        logger.info(String.format("$$$ -> From Partition -> %s", partition));
        logger.info("-*******-");
    }

    @KafkaListener(topics = "users", containerFactory = "filterKafkaListenerContainerFactory")
    public void filterConsume( String message) {
        logger.info("-**Filer Consume------");
        logger.info(String.format("$$$ -> Consumed Message -> %s", message));
        logger.info("-*******-");
    }
}
