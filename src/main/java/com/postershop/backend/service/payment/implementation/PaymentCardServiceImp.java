package com.postershop.backend.service.payment.implementation;

import com.postershop.backend.entity.PaymentCards;
import com.postershop.backend.entity.User;
import com.postershop.backend.repository.PaymentCardsRepository;
import com.postershop.backend.repository.UserRepository;
import com.postershop.backend.service.payment.PaymentCardService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class PaymentCardServiceImp implements PaymentCardService {

    private final PaymentCardsRepository paymentCardsRepository;
    private final UserRepository userRepository;

    public PaymentCardServiceImp(PaymentCardsRepository paymentCardsRepository, UserRepository userRepository) {
        this.paymentCardsRepository = paymentCardsRepository;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public PaymentCards addCard(PaymentCards card) {

        String username = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        card.setUser(user);
        return paymentCardsRepository.save(card);
    }

    @Override
    public List<PaymentCards> getMyCards() {
        String username = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        return paymentCardsRepository.findByUserId(user.getId());
    }

    @Override
    @Transactional
    public void deleteCard(Long cardId) {
        // In real app: Check if card belongs to user before delete
        paymentCardsRepository.deleteById(cardId);
    }
}
