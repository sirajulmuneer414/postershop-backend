package com.postershop.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.postershop.backend.util.PaymentAttributeConverter;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PaymentCards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Convert(converter = PaymentAttributeConverter.class)
    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String cardHolderName;

    @Column(nullable = false)
    private String expiryDate;

    @Convert(converter = PaymentAttributeConverter.class)
    @Column(nullable = false)
    private String cvv;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore // Prevent infinite recursion
    private User user;

}
