package com.ecommerce.project.Repositories;

import com.ecommerce.project.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
