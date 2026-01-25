package com.postershop.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPaymentDetailsRepository extends JpaRepository<OrderPaymentDetailsRepository, Long> {
}
