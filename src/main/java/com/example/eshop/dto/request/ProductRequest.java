package com.example.eshop.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class ProductRequest {
    private long id;
    private String name;
    @Min(value = 1, message = "Minimum product amount is 1")
    private int amount;
    private double price;
}
