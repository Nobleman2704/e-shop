package com.example.eshop.service.impl;

import com.example.eshop.domain.entity.PaymentCard;
import com.example.eshop.dto.request.PaymentCardRequest;
import com.example.eshop.dto.response.PaymentCardResponse;
import com.example.eshop.enums.CardType;
import com.example.eshop.exception.GlobalEShopException;
import com.example.eshop.mapper.CardMapper;
import com.example.eshop.repository.CardRepository;
import com.example.eshop.service.CardService;
import com.example.eshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final UserService userService;
    private final CardMapper cardMapper;
    private final CardRepository cardRepository;

    @Override
    public PaymentCardResponse create(PaymentCardRequest paymentCardRequest) {
        PaymentCard entity = cardMapper.toEntity(paymentCardRequest);
        entity.setCardType(CardType.valueOf(paymentCardRequest.getCardType().toUpperCase()));
        userService.findById(paymentCardRequest.getOwnerId());
        return cardMapper.toDto(cardRepository.save(entity));
    }

    @Override
    public PaymentCardResponse fillBalance(long cardId, double amount) {
        if (amount<=0)
            throw new GlobalEShopException("Amount should be positive");

        PaymentCard paymentCard = findById(cardId);
        paymentCard.setBalance(paymentCard.getBalance()+amount);

        return cardMapper.toDto(cardRepository.save(paymentCard));
    }

    @Override
    public PaymentCard findById(long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() ->
                new GlobalEShopException(String.format(
                        "Payment card was not found by this id -->%s", cardId)));
    }
}
