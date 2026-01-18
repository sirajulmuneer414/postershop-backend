package com.postershop.backend.dto.order;

import com.postershop.backend.entity.enums.OrderStatus;

public record OrderStatusRequest(
        OrderStatus status
) {}
