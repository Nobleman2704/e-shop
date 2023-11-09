package com.example.eshop.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CheckResponse {
    private long id;
    private long orderId;
    private long userId;
    private double totalSum;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;
}
