package com.example.hau.rabbitmq_kafka.controller;

import com.example.hau.rabbitmq_kafka.service.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaWebController {
    private final Producer producer;

    @Autowired
    public KafkaWebController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/publish")
    public void sendMessage(@RequestParam("message") String message, @RequestParam("topic") String topic) {
        this.producer.sendMessage(topic, message);
    }

    @PostMapping(value = "/publish/employee")
    public void sendEmployee(@RequestParam("empName") String empName) {
        this.producer.sendMessageEmployee(empName);
    }
}
