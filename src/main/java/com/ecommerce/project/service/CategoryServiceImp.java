package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {
    private List<Category> categories=new ArrayList<>();
    private Long nextId=1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryId(nextId++);
        categories.add(category);
    }

    @Override
    public String deleteCategory(Long id) { // Logic to delete a user
        Category category=categories.stream()
                .filter(c->c.getCategoryId().equals(id))
                .findFirst().orElse(null);
        if(category==null){
            return "Category not found";
        }
        categories.remove(category);
        return "Category with categoryid: "+id+" deleted successfully";
    }
}
