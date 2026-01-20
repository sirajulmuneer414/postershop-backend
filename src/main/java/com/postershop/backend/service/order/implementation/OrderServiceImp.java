package com.postershop.backend.service.order.implementation;

import com.postershop.backend.dto.order.OrderRequest;
import com.postershop.backend.entity.*;
import com.postershop.backend.entity.enums.OrderStatus;
import com.postershop.backend.repository.OrderRepository;
import com.postershop.backend.repository.ProductRepository;
import com.postershop.backend.repository.UserRepository;
import com.postershop.backend.service.order.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

    // Implementation of the OrderService interface for managing orders in the poster shop application.

@Service
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderServiceImp(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    /**
     * Places a new order based on the provided OrderRequest.
     *
     * @param request The order request containing item details and payment information.
     *
     * @return The placed ShopOrder.
     */
    @Transactional
    @Override
    public ShopOrder placeOrder(OrderRequest request) {
        String username = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Order Object Creation
        ShopOrder order = new ShopOrder();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);

        //Processing of Items & Calculation of Total (USING STREAMS)
        List<OrderItem> orderItems = request.items().stream()
                .map(itemRequest -> {
                    Product product = productRepository.findById(itemRequest.productId())
                            .orElseThrow(() -> new IllegalArgumentException("Product not found: " + itemRequest.productId()));

                    if (product.getStock() < itemRequest.quantity()) {
                        throw new RuntimeException("Not enough stock for: " + product.getName());
                    }
                    product.setStock(product.getStock() - itemRequest.quantity()); // Decrease stock

                    OrderItem item = new OrderItem();
                    item.setProduct(product);
                    item.setQuantity(itemRequest.quantity());
                    item.setPriceAtPurchase(product.getPrice());
                    item.setShopOrder(order); // Link back to parent
                    return item;
                })
                .toList();

        order.setItems(orderItems);

        // Calculation Total Amount using Stream Reduction
        double total = orderItems.stream()
                .mapToDouble(item -> item.getPriceAtPurchase() * item.getQuantity())
                .sum();

        order.setTotalAmount(total);

        //  Payment (Encryption happens automatically via JPA Converter)
        Payment payment = new Payment();
        payment.setCardNumber(request.cardNumber());
        payment.setCardHolderName(request.cardHolder());
        payment.setExpiryDate(request.expiryDate());
        order.setPayment(payment);

        return orderRepository.save(order);
    }

    /**
     * Retrieves all orders placed by the currently authenticated user.
     *
     * @return A list of ShopOrder objects belonging to the user.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShopOrder> getMyOrders() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return orderRepository.findByUserUsername(username);
    }

    /**
     * Retrieves a specific order by its ID.
     *
     * @param orderId The ID of the order to retrieve.
     *
     * @return The ShopOrder with the specified ID.
     *
     * @throws IllegalArgumentException if the order is not found.
     */
    @Override
    public ShopOrder getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));
    }


    /**
     * Updates the status of an existing order. Only accessible by admin users.
     *
     * @param orderId   The ID of the order to update.
     * @param newStatus The new status to set for the order.
     *
     * @return The updated ShopOrder.
     */
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Transactional
    @Override
    public ShopOrder updateOrderStatus(Long orderId, OrderStatus newStatus) {
        ShopOrder order = getOrderById(orderId);

        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    /**
     * Retrieves all orders in the system. Only accessible by admin users.
     *
     * @return A list of all ShopOrder objects.
     */
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Transactional(readOnly = true)
    @Override
    public List<ShopOrder> getAllOrders() {
        return orderRepository.findAll();
    }

}
