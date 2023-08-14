package com.springboot.assignment.service;

import com.springboot.assignment.entity.Products;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    Products createProduct(Products newProduct);
    Products findProductById(Long productId);
    List<Products> findAllProduct();
    Products updateProduct(Long productId, Products newProduct);
    void deleteProduct(Long productId);
}
