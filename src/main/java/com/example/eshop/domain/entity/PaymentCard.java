package com.example.eshop.domain.entity;

import com.example.eshop.enums.CardType;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "PAYMENT_CARD")
public class PaymentCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "CARD_TYPE")
    @Enumerated(EnumType.STRING)
    private CardType cardType;
    @Column(name = "OWNER_ID")
    private long ownerId;
    @Column(name = "BALANCE")
    private double balance;
    @CreatedDate
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    private LocalDateTime lastModifiedDate;
    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;
    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;
}
