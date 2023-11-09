package com.example.eshop.repository;

import com.example.eshop.domain.entity.Check;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckRepository extends JpaRepository<Check, Long> {
}
