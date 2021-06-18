package com.example.hau.rabbitmq_kafka.service.kafka;

import com.example.hau.rabbitmq_kafka.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Service
public class Producer {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, Employee> kafkaTemplateEmployee;

    public void sendMessage(String topic, String message) {
        logger.info(String.format("$$$ -> Producing message %s to topic %s", message, topic));
        ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(topic, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                logger.error("Unable to send message=[ {} ] due to : {}", message, throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("Sent message=[ {} ] with offset=[ {} ]", message, result.getRecordMetadata().hasOffset());
            }
        });
    }

    public void sendMessageEmployee(String empName) {
        Employee employee = new Employee();
        employee.setEmpId(UUID.randomUUID().toString());
        employee.setEmpName(empName);
        ListenableFuture<SendResult<String, Employee>> future = this.kafkaTemplateEmployee.send("add-employee", employee);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Employee>>() {
            @Override
            public void onFailure(Throwable throwable) {
                logger.error("Unable to send message=[ {} ] due to : {}", employee.getEmpName(), throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Employee> result) {
                logger.info("Sent message=[ {} ] with offset=[ {} ]", employee.getEmpName(), result.getRecordMetadata().hasOffset());
            }
        });
    }
}
