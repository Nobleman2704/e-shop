package com.example.eshop.service;

import com.example.eshop.domain.entity.PaymentCard;
import com.example.eshop.dto.request.PaymentCardRequest;
import com.example.eshop.dto.response.PaymentCardResponse;

public interface CardService {
    PaymentCardResponse create(PaymentCardRequest paymentCardRequest);

    PaymentCardResponse fillBalance(long cardId, double amount);
    PaymentCard findById(long cardId);
}
