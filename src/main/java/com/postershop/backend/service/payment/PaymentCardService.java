package com.postershop.backend.service.payment;

import com.postershop.backend.entity.PaymentCards;

import java.util.List;

public interface PaymentCardService {
    PaymentCards addCard(PaymentCards card);

    List<PaymentCards> getMyCards();

    void deleteCard(Long cardId);
}
