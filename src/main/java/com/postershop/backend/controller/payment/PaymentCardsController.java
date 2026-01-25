package com.postershop.backend.controller.payment;

import com.postershop.backend.entity.PaymentCards;
import com.postershop.backend.service.payment.PaymentCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments/cards")
public class PaymentCardsController {

    private final PaymentCardService paymentCardService;

    public PaymentCardsController(PaymentCardService paymentCardService) {
        this.paymentCardService = paymentCardService;
    }

    @PostMapping
    public ResponseEntity<PaymentCards> addCard(@RequestBody PaymentCards card) {
        return ResponseEntity.ok(paymentCardService.addCard(card));
    }

    @GetMapping
    public ResponseEntity<List<PaymentCards>> getMyCards() {
        return ResponseEntity.ok(paymentCardService.getMyCards());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        paymentCardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }
}
