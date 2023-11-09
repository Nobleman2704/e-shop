package com.example.eshop.dto.response;

import com.example.eshop.enums.CardType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentCardResponse {
    private long id;
    private CardType cardType;
    private long ownerId;
    private double balance;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;
}
