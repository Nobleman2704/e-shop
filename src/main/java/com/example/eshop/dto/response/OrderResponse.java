package com.example.eshop.dto.response;

import com.example.eshop.enums.OrderState;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderResponse {
    private long id;
    private long productId;
    private int productAmount;
    private OrderState orderState;
    private long userId;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;
}
