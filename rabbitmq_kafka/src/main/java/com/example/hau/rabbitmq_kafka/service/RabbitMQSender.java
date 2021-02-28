package com.example.hau.rabbitmq_kafka.service;

import com.example.hau.rabbitmq_kafka.model.Employee;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingKey;

    public void send(Employee employee) {
        rabbitTemplate.convertAndSend(exchange, routingKey, employee);
        System.out.println("Send msg = " + employee);
    }
}
