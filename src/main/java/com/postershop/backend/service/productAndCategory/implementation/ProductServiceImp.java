package com.postershop.backend.service.productAndCategory.implementation;

import com.postershop.backend.entity.Category;
import com.postershop.backend.entity.Product;
import com.postershop.backend.entity.enums.AvailabilityStatus;
import com.postershop.backend.repository.CategoryRepository;
import com.postershop.backend.repository.ProductRepository;
import com.postershop.backend.service.productAndCategory.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImp(ProductRepository productRepository, CategoryRepository categoryRepository) {
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
    public Product createProduct(Product product, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + categoryId));
        product.setCategory(category);
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

}
