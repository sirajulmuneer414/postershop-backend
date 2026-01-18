package com.postershop.backend.service.order;

import com.postershop.backend.dto.order.OrderRequest;
import com.postershop.backend.entity.ShopOrder;
import com.postershop.backend.entity.enums.OrderStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

    // Service interface for managing orders in the poster shop application.

public interface OrderService {

    @Transactional
    ShopOrder placeOrder(OrderRequest request);

    List<ShopOrder> getMyOrders();

    ShopOrder getOrderById(Long orderId);

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Transactional
    ShopOrder updateOrderStatus(Long orderId, OrderStatus newStatus);

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Transactional(readOnly = true)
    List<ShopOrder> getAllOrders();
}
