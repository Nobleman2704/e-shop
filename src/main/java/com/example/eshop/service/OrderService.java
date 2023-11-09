package com.example.eshop.service;

import com.example.eshop.domain.entity.Order;
import com.example.eshop.dto.request.OrderRequest;
import com.example.eshop.dto.response.CheckResponse;

public interface OrderService {
    Order findById(long orderId);

    void save(Order order);

    String cancelOrder(long userId, long orderId);

    CheckResponse orderProduct(long userId, OrderRequest productRequestList);
}
