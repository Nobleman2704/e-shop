package com.example.eshop.repository;

import com.example.eshop.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemsRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByParentOrderId(long parentOrderId);
}
