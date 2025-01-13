package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;

import java.util.List;

// Interface for loose coupling and modularity

public interface CategoryService {
    List<Category> getAllCategories();
    void createCategory(Category category);
    String deleteCategory(Long id);
}
