package com.postershop.backend.service.order;

    // Service interface for managing orders in the poster shop application.

import com.postershop.backend.dto.order.OrderRequest;
import com.postershop.backend.entity.ShopOrder;
import com.postershop.backend.entity.enums.OrderStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService {
    @Transactional
    ShopOrder placeOrder(OrderRequest request);

    List<ShopOrder> getMyOrders();

    ShopOrder getOrderById(Long orderId);

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Transactional
    ShopOrder updateOrderStatus(Long orderId, OrderStatus newStatus);

    // Optional: Admin might want to see ALL orders in the system, not just their own
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Transactional(readOnly = true)
    List<ShopOrder> getAllOrders();
}
