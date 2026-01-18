package com.postershop.backend.dto.order;

public record OrderItemRequest(
        Long productId,
        Integer quantity
) {}
