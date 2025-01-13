package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public String deleteCategory(Long CategoryId) { // Logic to delete a user
        Category category=categories.stream()
                .filter(c->c.getCategoryId().equals(CategoryId))
                .findFirst()
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category Not Found"));

        categories.remove(category);
        return "Category with categoryid: "+CategoryId+" deleted successfully";
    }

    @Override
    public Category updateCategory(Category category , Long CategoryId) {
        Optional<Category> optionalCategory=categories.stream()
                .filter(c->c.getCategoryId().equals(CategoryId))
                .findFirst();

        if(optionalCategory.isPresent()) {
            Category existingCategory=optionalCategory.get();
            existingCategory.setCategoryName(category.getCategoryName());
            return existingCategory;
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category Not Found");
        }


    }
}
