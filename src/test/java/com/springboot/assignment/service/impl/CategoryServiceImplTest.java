package com.springboot.assignment.service.impl;

import com.springboot.assignment.entity.Category;
import com.springboot.assignment.entity.Products;
import com.springboot.assignment.exception.ResourceNotFoundException;
import com.springboot.assignment.repository.CategoryRepository;
import com.springboot.assignment.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class CategoryServiceImplTest {
    @InjectMocks
    CategoryServiceImpl categoryServiceImpl;

    @Mock
    CategoryRepository categoryRepository;
    @Mock
    ProductRepository productRepository;

    Category category;
    Products product1;
    Products product2;
    Category savedCategory;
    Set<Products> productsSet;

    @BeforeEach
    public void setup(){
        product2 = new Products();
        category = new Category();
        product1 = new Products();
        savedCategory = new Category();
        productsSet = new HashSet<>();

        product1.setId(1L);
        product1.setName("Product");
        product1.setCategory(1L);
        product2.setId(2L);
        productsSet.add(product2);
        productsSet.add(product1);

        category.setId(1L);
    }

    @AfterEach
    public void tearDown(){
        category = null;
        savedCategory = null;
        product1 = null;
        productsSet = null;
        product2=null;
    }

    @Test
    public void createCategoryTest(){
        when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(product1));
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        savedCategory = categoryServiceImpl.createCategory(category);
        assertNotNull(savedCategory);
    }

    @Test
    public void createCategoryWithResourceNotFoundExceptionTest(){
        category.setProductsSet(productsSet);
        when(productRepository.findById(0L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            categoryServiceImpl.createCategory(category);
        });
        assertTrue(savedCategory.getProductsSet().size() == 0);
    }

    @Test
    public void findCategoryByIdTest(){
        when(categoryRepository.findById(1L)).thenReturn(Optional.ofNullable(category));
        when(productRepository.findProductsByCategory(1L)).thenReturn(productsSet);
        savedCategory= categoryServiceImpl.findCategoryById(1L);
        assertNotNull(savedCategory.getId());
    }

    @Test
    public void findCategoryByIdWithResourceNotFoundExceptionTest(){
        when(categoryRepository.findById(0L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            categoryServiceImpl.findCategoryById(0L);
        });
        assertTrue(savedCategory.getProductsSet().size() == 0);
    }

    @Test
    public void findAllCategoryTest(){
        savedCategory.setId(2L);
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        categoryList.add(savedCategory);

        when(categoryRepository.findAll()).thenReturn(categoryList);
        when(productRepository.findProductsByCategory(anyLong())).thenReturn(Collections.emptySet());
        List<Category> newCategoryList = categoryServiceImpl.findAllCategory();
        assertEquals(categoryList.size(),newCategoryList.size());
    }

    @Test
    public void updateCategoryTest(){
        //savedCategory.setProductsSet(new HashSet<>(Arrays.asList(product1, product2)));

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.findById(1l)).thenReturn(Optional.of(product1));
        when(productRepository.save(any(Products.class))).thenReturn(product1);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        savedCategory = categoryServiceImpl.updateCategory(1L,category);
        assertTrue(savedCategory.getId()==1);
    }

    @Test
    public void updateCategoryWithResourceNotFoundExceptionTest(){
        category.setProductsSet(productsSet);
        when(categoryRepository.findById(0L)).thenReturn(Optional.empty());
        when(productRepository.findById(0l)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            categoryServiceImpl.updateCategory(0L,category);
        });
        assertTrue(savedCategory.getProductsSet().size() == 0);
    }

    @Test
    public void deleteCategoryTest(){
        when(categoryRepository.findById(1L)).thenReturn(Optional.ofNullable(category));
        doNothing().when(categoryRepository).deleteById(1L);
        categoryServiceImpl.deleteCategory(1L);
        verify(categoryRepository,times(1)).findById(1L);
    }

    @Test
    public void deleteCategoryResourceNotFoundExceptionTest() {
        when(categoryRepository.findById(0L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            categoryServiceImpl.deleteCategory(0L);
        });
        verify(categoryRepository).findById(0L);
    }

}