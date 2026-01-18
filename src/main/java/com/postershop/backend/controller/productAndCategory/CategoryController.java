package com.postershop.backend.controller.productAndCategory;

import com.postershop.backend.entity.Category;
import com.postershop.backend.service.productAndCategory.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Get all categories - PUBLIC
    @GetMapping()
    public ResponseEntity<List<Category>> getAllCategories() {
        log.info("Received request to get all categories");
        List<Category> categories = categoryService.getAllCategories();
        log.info("Returning {} categories", categories.size());
        return ResponseEntity.ok(categories);
    }

    // Create a new category : ADMIN ONLY
    @PostMapping("/add")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        log.info("Received request to create category: {}", category.getName());
        Category createdCategory = categoryService.createCategory(category);
        log.info("Created category with ID: {}", createdCategory.getId());
        return ResponseEntity.ok(createdCategory);
    }

    // Get category by ID - PUBLIC
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        log.info("Received request to get category by ID: {}", id);
        Category category = categoryService.getCategoryById(id);
        log.info("Returning category: {}", category.getName());
        return ResponseEntity.ok(category);
    }

    // Update category : ADMIN ONLY
    @PutMapping("/update/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
        log.info("Received request to update category with ID: {}", id);
        Category category = categoryService.updateCategory(id, updatedCategory);
        log.info("Updated category with ID: {}", category.getId());
        return ResponseEntity.ok(category);
    }
}
