package com.postershop.backend.entity;

import com.postershop.backend.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

    // Order Entity

@Entity
@Data
public class ShopOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime orderDate = LocalDateTime.now();

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELED

    // One Order has Many Items
    @OneToMany(mappedBy = "shopOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();


    @OneToOne(mappedBy = "shopOrder", cascade = CascadeType.ALL)
    private OrderPaymentDetails paymentDetails;
}
