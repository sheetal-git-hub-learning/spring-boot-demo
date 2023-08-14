package com.springboot.assignment.repository;

import com.springboot.assignment.entity.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;
    Category category;
    Category savedCategory;
    @Mock
    private JpaRepository<Category, Long> jpaRepository;

    @BeforeEach
    public void setup(){
        category = new Category();
        category.setId(1L);
        category.setName("Category");
    }

    @AfterEach
    public void tearDown(){
        category = null;
        savedCategory = null;
    }

    @Test
    public void saveCategoryTest(){
        when(jpaRepository.save(category)).thenReturn(savedCategory);
        savedCategory = categoryRepository.save(category);
        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo(category.getName());
    }

    @Test
    public void findByIdTest(){
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(category));
        Optional<Category> categoryOptional = categoryRepository.findById(1L);
        assertNotNull(categoryOptional);
    }

    @Test
    public void deleteByIdTest(){
        doNothing().when(jpaRepository).deleteById(1L);
        categoryRepository.deleteById(1L);
    }

    @Test
    public void findAllTest(){
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        when(jpaRepository.findAll()).thenReturn(categoryList);
        categoryList = categoryRepository.findAll();
        assertThat(categoryList).isNotNull();
    }

}