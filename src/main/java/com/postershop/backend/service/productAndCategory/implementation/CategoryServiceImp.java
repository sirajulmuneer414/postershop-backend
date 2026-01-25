package com.postershop.backend.service.productAndCategory.implementation;

import com.postershop.backend.entity.Category;
import com.postershop.backend.entity.Product;
import com.postershop.backend.entity.enums.AvailabilityStatus;
import com.postershop.backend.repository.CategoryRepository;
import com.postershop.backend.repository.ProductRepository;
import com.postershop.backend.service.productAndCategory.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryServiceImp(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    /**
     *  Retrieves all categories from the repository.
     *
     * @return a list of all categories
     */
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


    /**
     * Creates a new category if it does not already exist.
     *
     * @param category the category to be created
     * @return the created category
     * @throws IllegalArgumentException if a category with the same name already exists
     */
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Override
    public Category createCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category with the same name already exists");
        }

        category.setStatus(AvailabilityStatus.AVAILABLE);

        return categoryRepository.save(category);

    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id the ID of the category
     * @return the category with the specified ID
     * @throws IllegalArgumentException if no category is found with the given ID
     */
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + id));
    }


    /**
     * Updates an existing category with new details.
     *
     * @param id              the ID of the category to be updated
     * @param updatedCategory the category object containing updated details
     *
     * @return the updated category
     *
     * @throws IllegalArgumentException if no category is found with the given ID
     */
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Override
    public Category updateCategory(Long id, Category updatedCategory) {
        Category existingCategory = getCategoryById(id);

        existingCategory.setName(updatedCategory.getName());
        existingCategory.setDescription(updatedCategory.getDescription());
        existingCategory.setStatus(updatedCategory.getStatus());

        return categoryRepository.save(existingCategory);
    }


    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Transactional
    @Override
    public void deleteCategory(Long id) {

        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }

        Category category = getCategoryById(id);

        List<Product> productsInCategory = categoryRepository.findProductsByCategoryId(id);

        if(productsInCategory == null && productsInCategory.isEmpty()) {

            categoryRepository.deleteById(id);
            return;

        }

        for (Product product : productsInCategory) {
            product.setStatus(AvailabilityStatus.NOT_AVAILABLE);
            productRepository.save(product);
        }


        category.setStatus(AvailabilityStatus.NOT_AVAILABLE);
        categoryRepository.save(category);
    }
// ...


}
