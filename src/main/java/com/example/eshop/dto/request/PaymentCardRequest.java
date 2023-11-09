package com.example.eshop.dto.request;

import com.example.eshop.annotation.ValidCardTypePattern;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class PaymentCardRequest {
    @ValidCardTypePattern(message = "Invalid card type! Allowed values are only" +
            " HUMO, VISA, MASTER_CARD, UZ_CARD!")
    private String cardType;
    private long ownerId;
    @Min(value = 1, message = "Min card balance must be 1")
    private double balance;
}
