package com.springboot.assignment.controller;

import com.springboot.assignment.entity.Category;
import com.springboot.assignment.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryControllerTest {
    @InjectMocks
    CategoryController categoryController;

    @Mock
    CategoryService categoryService;

    Category newCategory;

    Category savedCategory;

    @BeforeEach
    public void setup(){
        newCategory = new Category();
        savedCategory = new Category();
    }

    @Test
    public void createCategoryTest(){
        when(categoryService.createCategory(newCategory)).thenReturn(savedCategory);
        ResponseEntity<Category> response = categoryController.createCategory(newCategory);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(newCategory.getId(), savedCategory.getId());
    }

    @Test
    public void findCategoryByIdTest(){
        when(categoryService.findCategoryById(1l)).thenReturn(newCategory);
        ResponseEntity<Category> response = categoryController.findCategoryById(1l);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(newCategory == response.getBody());
    }

    @Test
    public void findAllCategoryTest(){
        when(categoryService.findAllCategory()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Category>> response = categoryController.findAllCategory();
        assertNotNull(response);
    }

    @Test
    public void updateCategoryTest(){
        when(categoryService.updateCategory(1L,newCategory)).thenReturn(savedCategory);
        ResponseEntity<Category> response = categoryController.updateCategory(1L,newCategory);
        assertTrue(savedCategory != null);
    }

    @Test
    public void deleteCategoryTest(){
        doNothing().when(categoryService).deleteCategory(1L);
        String response = categoryController.deleteCategory(1L);
        assertEquals("Category deleted successfully", response);
    }

}