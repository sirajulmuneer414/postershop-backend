package com.postershop.backend.controller.productAndCategory;

import com.postershop.backend.entity.Product;
import com.postershop.backend.service.productAndCategory.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Get all products - PUBLIC
    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("Received request to get all products");
        List<Product> products = productService.getAllProducts();
        log.info("Returning {} products", products.size());
        return ResponseEntity.ok(products);
    }

    // Create a new product : ADMIN ONLY
    @PostMapping("/add")
    public ResponseEntity<Product> createProduct(@RequestBody Product product,
                                                 @RequestParam Long categoryId,
                                                 @RequestParam(name = "imageFile" , required = false) MultipartFile imageFile
    ) {
        log.info("Received request to create product: {}", product.getName());
        Product createdProduct = productService.createProduct(product,imageFile, categoryId);
        log.info("Created product with ID: {}", createdProduct.getId());
        return ResponseEntity.ok(createdProduct);
    }

    // Get product by ID - PUBLIC
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        log.info("Received request to get product by ID: {}", id);
        Product product = productService.getProductById(id);
        log.info("Returning product: {}", product.getName());
        return ResponseEntity.ok(product);
    }

    // Update product : ADMIN ONLY
    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        log.info("Received request to update product with ID: {}", id);
        Product product = productService.updateProduct(id, updatedProduct);
        log.info("Updated product with ID: {}", product.getId());
        return ResponseEntity.ok(product);
    }

    // Delete product : ADMIN ONLY
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.info("Received request to delete product ID: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
