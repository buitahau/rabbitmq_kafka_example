package com.example.hau.rabbitmq_kafka.controller;

import com.example.hau.rabbitmq_kafka.model.Employee;
import com.example.hau.rabbitmq_kafka.service.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/example-rabbitmq/")
public class RabbitMQWebController {

    @Autowired
    RabbitMQSender rabbitMQSender;

    @GetMapping(value = "/producer")
    public String producer(@RequestParam("empName") String empName,
                           @RequestParam("empId") String empId) {
        Employee employee = new Employee();
        employee.setEmpId(empId);
        employee.setEmpName(empName);

        rabbitMQSender.send(employee);
        return "Message sent to the RabbitMQ Successfully";
    }
}
