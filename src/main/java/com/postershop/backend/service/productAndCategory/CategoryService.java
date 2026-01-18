package com.postershop.backend.service.productAndCategory;

import com.postershop.backend.entity.Category;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    Category createCategory(Category category);

    Category getCategoryById(Long id);

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    Category updateCategory(Long id, Category updatedCategory);
}
