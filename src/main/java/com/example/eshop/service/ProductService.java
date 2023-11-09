package com.example.eshop.service;

import com.example.eshop.domain.entity.Product;
import com.example.eshop.dto.request.ProductRequest;
import com.example.eshop.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest request);

    ProductResponse addAmount(ProductRequest request);
    Product findById(long productId);

    void saveAll(List<Product> productList);

    List<ProductResponse> getAll();
}
