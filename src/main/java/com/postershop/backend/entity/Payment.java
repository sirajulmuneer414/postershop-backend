package com.postershop.backend.entity;

import com.postershop.backend.util.PaymentAttributeConverter;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Payment {

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

    @Transient
    private String cvv;

}
