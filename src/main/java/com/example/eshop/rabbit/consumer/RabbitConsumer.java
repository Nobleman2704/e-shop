package com.example.eshop.rabbit.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitConsumer {

    @SneakyThrows
    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void consumer(Message message){
        ObjectMapper objectMapper = new ObjectMapper();
        String str = objectMapper.readValue(message.getBody(), String.class);
        System.out.println(str);
    }
}
