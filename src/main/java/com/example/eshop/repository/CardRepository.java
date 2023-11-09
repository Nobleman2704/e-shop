package com.example.eshop.repository;

import com.example.eshop.domain.entity.PaymentCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<PaymentCard, Long> {
}
