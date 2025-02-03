package com.ecommerce.project.service;

import com.ecommerce.project.Repositories.CategoryRepository;
import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImp implements CategoryService {
//    private List<Category> categories=new ArrayList<>();
    private Long nextId=1000L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories=categoryRepository.findAll();
        if(categories.isEmpty())
            throw new APIException("No categories found");
        List<CategoryDTO>categoryDTOS =categories.stream()
                .map(c->modelMapper.map(c,CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse=new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
            Category category=modelMapper.map(categoryDTO,Category.class);
            Category categoryfromDB = categoryRepository.findByCategoryName(category.getCategoryName());
            if (categoryfromDB != null)
                throw new APIException("Category with name "+category.getCategoryName()+" already exists !!!");

            Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDTO.class);

    }

    @Override
    public CategoryDTO deleteCategory(Long CategoryId) { // Logic to delete a user

        Category category = categoryRepository.findById(CategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",CategoryId));

        categoryRepository.delete(category);
        return modelMapper.map(category,CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO , Long CategoryId) {

        Category savedCategory=categoryRepository.findById(CategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",CategoryId));

        Category category=modelMapper.map(categoryDTO,Category.class);
        category.setCategoryId(CategoryId);
        savedCategory=categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDTO.class);
    }
}
