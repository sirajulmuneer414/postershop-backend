package com.postershop.backend.repository;

import com.postershop.backend.entity.Category;
import com.postershop.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repository interface for Category entity

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByName(String name);

    List<Product> findProductsByCategoryId(Long id);
}
