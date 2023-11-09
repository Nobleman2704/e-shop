package com.example.eshop.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    List<ProductRequest> productList;
}
