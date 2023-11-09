package com.example.eshop.mapper;

import com.example.eshop.domain.entity.PaymentCard;
import com.example.eshop.dto.request.PaymentCardRequest;
import com.example.eshop.dto.response.PaymentCardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper extends BaseMapper<PaymentCard, PaymentCardRequest, PaymentCardResponse>{
    @Override
    @Mapping(target = "cardType", source = "cardType", ignore = true)
    PaymentCard toEntity(PaymentCardRequest request);
}
