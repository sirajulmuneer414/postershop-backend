package com.postershop.backend.service.productAndCategory;

import com.postershop.backend.entity.Product;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductService {
    @Transactional(readOnly = true)
    List<Product> getAllProducts();

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    Product createProduct(Product product, Long categoryId);

    Product getProductById(Long id);

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Transactional
    Product updateProduct(Long id, Product updatedProduct);
}
