package com.springboot.assignment.repository;

import com.springboot.assignment.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Products,Long> {
    Set<Products> findProductsByCategory(Long category_id);
}
