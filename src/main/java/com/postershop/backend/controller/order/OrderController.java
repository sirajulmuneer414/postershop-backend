package com.postershop.backend.controller.order;

import com.postershop.backend.dto.order.OrderRequest;
import com.postershop.backend.dto.order.OrderStatusRequest;
import com.postershop.backend.entity.ShopOrder;
import com.postershop.backend.service.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {


    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Endpoint to place a new order
    @PostMapping
    public ResponseEntity<ShopOrder> checkout(@RequestBody OrderRequest request) {
        log.info("Received order request: {}", request);
        ShopOrder order =  orderService.placeOrder(request);
        log.info("Order placed successfully: {}", order);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<ShopOrder>> getMyHistory() {

        log.info("Fetching order history for the current user");
        List<ShopOrder> orderList = orderService.getMyOrders();
        log.info("Retrieved {} orders for the current user", orderList.size());

        return ResponseEntity.ok(orderList);
    }


    // ADMIN ONLY: Update status
    // Usage: PATCH /api/orders/1/status?status=SHIPPED
    @PatchMapping("/{id}/status")
    public ResponseEntity<ShopOrder> updateStatus(@PathVariable Long id, @RequestBody OrderStatusRequest statusRequest) {
        log.info("Updating order ID {} to status {}", id, statusRequest.status());
        ShopOrder order =  orderService.updateOrderStatus(id, statusRequest.status());
        log.info("Order ID {} updated successfully to status {}", id, order.getStatus());
        return ResponseEntity.ok(order);
    }

    // ADMIN ONLY: See all orders
    @GetMapping("/all")
    public ResponseEntity<List<ShopOrder>> getAllOrders() {
        log.info("Fetching all orders for admin");
        List<ShopOrder> orderList = orderService.getAllOrders();
        log.info("Retrieved {} orders", orderList.size());
        return ResponseEntity.ok(orderList);
    }

}
