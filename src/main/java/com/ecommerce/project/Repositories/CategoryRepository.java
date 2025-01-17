package com.ecommerce.project.Repositories;

import com.ecommerce.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByCategoryName(String categoryName);// jpa automatically parse the method name but make sure to follow the convention of findBy
}
