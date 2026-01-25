package com.postershop.backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.postershop.backend.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class OrderPaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private LocalDateTime paymentDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // e.g., "SUCCESS", "FAILED"

    // Link to the Card used
    @ManyToOne
    @JoinColumn(name = "saved_card_id")
    private PaymentCards savedCard;

    // Link to the Order (One-to-One)
    @OneToOne
    @JoinColumn(name = "shop_order_id")
    @JsonIgnore
    private ShopOrder shopOrder;
}
