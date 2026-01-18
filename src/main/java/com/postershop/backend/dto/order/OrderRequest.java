package com.postershop.backend.dto.order;

import java.util.List;

public record OrderRequest(
        List<OrderItemRequest> items,
        String cardNumber,
        String cardHolder
) {}
