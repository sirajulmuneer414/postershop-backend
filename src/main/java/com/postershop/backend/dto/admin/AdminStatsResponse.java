package com.postershop.backend.dto.admin;

public record AdminStatsResponse(
        long totalUsers,
        long totalProducts,
        long totalOrders,
        double totalRevenue
) {}