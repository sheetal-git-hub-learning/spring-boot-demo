package com.springboot.assignment.service;

import com.springboot.assignment.entity.Category;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface CategoryService {

    Category createCategory(Category newCategory);
    Category findCategoryById(Long id);
    List<Category> findAllCategory();
    Category updateCategory(Long id, Category newCategory);
    void deleteCategory(Long id);
}
