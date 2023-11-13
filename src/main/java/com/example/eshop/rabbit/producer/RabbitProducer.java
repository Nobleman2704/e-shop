package com.example.eshop.rabbit.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitProducer {
    @Value("${spring.rabbitmq.exchange}")
    private String exchangeName;
    @Value("${spring.rabbitmq.routingKey}")
    private String routingJsonKey;
    private final RabbitTemplate rabbitTemplate;

    public void sendOrderMessage(String message){
        rabbitTemplate.convertAndSend(exchangeName, routingJsonKey, message);
    }
}
