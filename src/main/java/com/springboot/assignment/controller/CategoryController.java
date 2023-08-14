package com.springboot.assignment.controller;

import com.springboot.assignment.entity.Category;
import com.springboot.assignment.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category/")
public class CategoryController {
    //TODO
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category newCategory) {
        Category category = categoryService.createCategory(newCategory);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable("id") Long id) {
        Category category = categoryService.findCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity< List<Category>> findAllCategory() {
        List<Category> categoryList= categoryService.findAllCategory();
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Long id,@Valid @RequestBody Category newCategory){
        Category category = categoryService.updateCategory(id,newCategory);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return "Category deleted successfully";
    }
}
