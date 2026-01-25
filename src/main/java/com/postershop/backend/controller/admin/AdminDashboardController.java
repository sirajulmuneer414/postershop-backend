package com.postershop.backend.controller.admin;


import com.postershop.backend.dto.admin.AdminStatsResponse;
import com.postershop.backend.entity.ShopOrder;
import com.postershop.backend.repository.OrderRepository;
import com.postershop.backend.repository.ProductRepository;
import com.postershop.backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminDashboardController {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public AdminDashboardController(UserRepository userRepository,
                                    ProductRepository productRepository,
                                    OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/stats")
    public ResponseEntity<AdminStatsResponse> getStats() {
        long totalUsers = userRepository.count() - 1; // Exclude admin user
        long totalProducts = productRepository.count();
        List<ShopOrder> orders = orderRepository.findAll();
        long totalOrders = orders.size();

        // Calculate revenue only from non-cancelled orders if you prefer
        double totalRevenue = orders.stream()
                .mapToDouble(ShopOrder::getTotalAmount)
                .sum();

        return ResponseEntity.ok(new AdminStatsResponse(totalUsers, totalProducts, totalOrders, totalRevenue));
    }
}