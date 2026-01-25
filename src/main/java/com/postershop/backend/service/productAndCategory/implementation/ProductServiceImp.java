package com.postershop.backend.service.productAndCategory.implementation;

import com.postershop.backend.entity.Category;
import com.postershop.backend.entity.Product;
import com.postershop.backend.entity.enums.AvailabilityStatus;
import com.postershop.backend.repository.CategoryRepository;
import com.postershop.backend.repository.ProductRepository;
import com.postershop.backend.service.productAndCategory.ProductService;
import com.postershop.backend.util.CloudinaryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CloudinaryService cloudinaryService;

    public ProductServiceImp(ProductRepository productRepository, CategoryRepository categoryRepository, CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    /**
     * Retrieves all products from the repository.
     *
     * @return a list of all products
     */
    @Transactional(readOnly = true)
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    /**
     * Creates a new product and associates it with a category.
     *
     * @param product    the product to be created
     * @param categoryId the ID of the category to associate with the product
     *
     * @return the created product
     *
     * @throws IllegalArgumentException if no category is found with the given ID
     */
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Transactional
    @Override
    public Product createProduct(Product product, MultipartFile image, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + categoryId));
        product.setCategory(category);

        if (image != null && !image.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(image);
            product.setImageUrl(imageUrl);
        }
        product.setStatus(AvailabilityStatus.AVAILABLE);
        return productRepository.save(product);
    }


    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product
     *
     * @return the product with the specified ID
     *
     * @throws IllegalArgumentException if no product is found with the given ID
     */
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }


    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Transactional
    @Override
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = getProductById(id);

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStock(updatedProduct.getStock());
        existingProduct.setStatus(updatedProduct.getStatus());

        if(updatedProduct.getCategory() != null) {
            Category category = categoryRepository.findById(updatedProduct.getCategory().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + updatedProduct.getCategory().getId()));
            existingProduct.setCategory(category);
        }

        return productRepository.save(existingProduct);
    }


    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to be deleted
     *
     * @throws RuntimeException if no product is found with the given ID
     */
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Transactional
    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }

        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        cloudinaryService.deleteImage(product.getImageUrl());
        productRepository.delete(product);
    }
}
