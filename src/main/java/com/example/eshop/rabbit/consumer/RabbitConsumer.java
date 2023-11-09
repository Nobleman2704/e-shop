package com.example.eshop.rabbit.consumer;

import com.example.eshop.domain.entity.OrderItem;
import com.example.eshop.repository.OrderItemsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RabbitConsumer {
    private final OrderItemsRepository orderItemsRepository;

    @SneakyThrows
    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void consumer(Message message){
        ObjectMapper objectMapper = new ObjectMapper();
        String str = objectMapper.readValue(message.getBody(), String.class);
        System.out.println(str);
    }

    public static <T> T convert(Message message, Class<T> cls) {
        ObjectMapper mapper = new ObjectMapper();
        String messageBody = new String(message.getBody());
        HashMap<String, Object> result = new HashMap<>();
        try {
            result = mapper.readValue(messageBody, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return mapper.convertValue(result, cls);
    }
}
