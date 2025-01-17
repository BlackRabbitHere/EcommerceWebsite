package com.ecommerce.project.service;

import com.ecommerce.project.Repositories.CategoryRepository;
import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CategoryServiceImp implements CategoryService {
//    private List<Category> categories=new ArrayList<>();
    private Long nextId=1000L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories=categoryRepository.findAll();
        if(categories.isEmpty())
            throw new APIException("No categories found");
        return categories;
    }

    @Override
    public void createCategory(Category category) {
            Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
            if (savedCategory != null)
                throw new APIException("Category with name "+category.getCategoryName()+" already exists !!!");
            categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long CategoryId) { // Logic to delete a user

        Category category = categoryRepository.findById(CategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",CategoryId));

        categoryRepository.delete(category);
        return "Category with categoryid: "+CategoryId+" deleted successfully";
    }

    @Override
    public Category updateCategory(Category category , Long CategoryId) {

        Category savedCategory=categoryRepository.findById(CategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",CategoryId));

        category.setCategoryId(CategoryId);
        savedCategory=categoryRepository.save(category);
        return savedCategory;
    }
}
