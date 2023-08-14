package com.springboot.assignment.service.impl;

import com.springboot.assignment.entity.Category;
import com.springboot.assignment.entity.Products;
import com.springboot.assignment.exception.ResourceNotFoundException;
import com.springboot.assignment.repository.CategoryRepository;
import com.springboot.assignment.repository.ProductRepository;
import com.springboot.assignment.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

//@NoArgsConstructor
@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    //@Autowired
    private final CategoryRepository categoryRepository;

    //@Autowired
    private final ProductRepository productRepository;

    Set<Products> productSet = new HashSet<>();

    @Override
    public Category createCategory(Category newCategory) {
        for(Products product: newCategory.getProductsSet()){
           Products products = productRepository.findById(product.getId()).orElseThrow(()-> new ResourceNotFoundException("Product","ProductId",product.getId()));
           productSet.add(products);
        }
        newCategory.setProductsSet(productSet);
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category findCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",id));
        Set<Products> saveProduct= productRepository.findProductsByCategory(category.getId());
        category.setProductsSet(saveProduct);
        return category;
    }

    @Override
    public List<Category> findAllCategory() {
        List<Category> categoryList= categoryRepository.findAll();
        for(Category category: categoryList){
            Set<Products> productSet = productRepository.findProductsByCategory(category.getId());
            category.setProductsSet(productSet);
        }
        return categoryList;
    }

    @Override
    public Category updateCategory(Long id,Category newCategory) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",id));
        for(Products product: newCategory.getProductsSet()) {
                Products products = productRepository.findById(product.getId()).orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", product.getId()));
                products.setCategory(category.getId());
                productSet.add(products);
                productRepository.save(products);
            }

        category.setName(newCategory.getName());
        categoryRepository.save(category);
        category.setProductsSet(newCategory.getProductsSet());
        return category;
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",id));
        categoryRepository.deleteById(id);
    }
}
