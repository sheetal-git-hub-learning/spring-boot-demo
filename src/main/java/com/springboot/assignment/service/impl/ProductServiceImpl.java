package com.springboot.assignment.service.impl;

import com.springboot.assignment.entity.Category;
import com.springboot.assignment.entity.Products;
import com.springboot.assignment.exception.ResourceNotFoundException;
import com.springboot.assignment.repository.CategoryRepository;
import com.springboot.assignment.repository.ProductRepository;
import com.springboot.assignment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Products createProduct(Products newProduct) {
        Category category = categoryRepository.findById(newProduct.getCategory()).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",newProduct.getCategory()));
        return  productRepository.save(newProduct);
    }

    @Override
    public Products findProductById(Long productId) {
        Products product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","ProductId",productId));
        return product;
    }

    @Override
    public List<Products> findAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Products updateProduct(Long productId, Products newProduct) {
        Category category = categoryRepository.findById(newProduct.getCategory()).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",newProduct.getCategory()));
        Products product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","ProductId",productId));

        product.setCategory(newProduct.getCategory());
        product.setName(newProduct.getName());
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        Products product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","ProductId",productId));
        productRepository.deleteById(productId);
    }
}


