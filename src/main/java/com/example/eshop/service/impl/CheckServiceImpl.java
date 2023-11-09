package com.example.eshop.service.impl;

import com.example.eshop.domain.entity.*;
import com.example.eshop.enums.OrderState;
import com.example.eshop.dto.request.CheckRequest;
import com.example.eshop.dto.response.CheckResponse;
import com.example.eshop.dto.response.OrderResponse;
import com.example.eshop.exception.GlobalEShopException;
import com.example.eshop.mapper.CheckMapper;
import com.example.eshop.rabbit.producer.RabbitProducer;
import com.example.eshop.repository.CardRepository;
import com.example.eshop.repository.CheckRepository;
import com.example.eshop.repository.OrderItemsRepository;
import com.example.eshop.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckServiceImpl implements CheckService {
    private final CheckRepository checkRepository;
    private final CheckMapper checkMapper;
    private final UserService userService;
    private final OrderService orderService;
    private final CardService cardService;
    private final CardRepository cardRepository;
    private final RabbitProducer rabbitProducer;

    @Override
    public CheckResponse create(Check check) {
        return checkMapper.toDto(checkRepository.save(check));
    }

    @Override
    public String payment(CheckRequest checkRequest) {
        Check check = checkRepository.findById(checkRequest.getId()).orElseThrow(
                () -> new GlobalEShopException(String.format(
                        "Check not found by this id --> %s", checkRequest.getId())));

        Order parentOrder = orderService.findById(check.getOrderId());
        if (!parentOrder.getOrderState().equals(OrderState.PENDING))
            throw new GlobalEShopException(String.format(
                    "Order state is not in PENDING state! order state-->%s", parentOrder.getOrderState().name()));

        User user = userService.findById(check.getUserId());

        PaymentCard paymentCard = cardService.findById(checkRequest.getCardId());

        if (paymentCard.getOwnerId()!= user.getId())
            throw new GlobalEShopException("Payment card owner id must be equal to check user id!");

        double totalSum = check.getTotalSum();
        double balance = paymentCard.getBalance();
        if (balance < totalSum)
           throw new GlobalEShopException(String.format(
                    "Insufficient card balance, card balance --> %s", balance));

        parentOrder.setOrderState(OrderState.ORDERED);
        orderService.save(parentOrder);

        balance-=totalSum;
        paymentCard.setBalance(balance);

        cardRepository.save(paymentCard);
        rabbitProducer.sendOrderMessage("""
                ***********************************
                
                Order has successfully been paid
                
                ***********************************""");

        return "Order payment has been paid successfully";
    }
}
