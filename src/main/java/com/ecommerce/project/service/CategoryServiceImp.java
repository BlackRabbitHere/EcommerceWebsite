package com.ecommerce.project.service;

import com.ecommerce.project.Repositories.CategoryRepository;
import com.ecommerce.project.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {
//    private List<Category> categories=new ArrayList<>();
    private Long nextId=1000L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {

        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
            categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long CategoryId) { // Logic to delete a user

        Category category = categoryRepository.findById(CategoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found"));

        categoryRepository.delete(category);
        return "Category with categoryid: "+CategoryId+" deleted successfully";
    }

    @Override
    public Category updateCategory(Category category , Long CategoryId) {

        Category savedCategory=categoryRepository.findById(CategoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found"));

        category.setCategoryId(CategoryId);
        savedCategory=categoryRepository.save(category);
        return savedCategory;
    }
}
