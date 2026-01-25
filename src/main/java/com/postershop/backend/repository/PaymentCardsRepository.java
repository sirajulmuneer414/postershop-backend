package com.postershop.backend.repository;

import com.postershop.backend.entity.PaymentCards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentCardsRepository extends JpaRepository<PaymentCards, Long> {
    List<PaymentCards> findByUserId(Long userId);
}
