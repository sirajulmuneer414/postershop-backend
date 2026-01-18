package com.postershop.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

    // Order Item Entity

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity;
    private Double priceAtPurchase; // Snapshot of price

    @ManyToOne
    @JoinColumn(name = "shop_order_id")
    @JsonIgnore// Prevent infinite recursion in JSON
    private ShopOrder shopOrder;
}
