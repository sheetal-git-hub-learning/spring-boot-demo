package com.springboot.assignment.controller;

import com.springboot.assignment.entity.Products;
import com.springboot.assignment.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/products/")
@RestController
public class ProductController {
    //TODO-add lombok
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Products> createProduct(@Valid @RequestBody Products newProduct) {
        Products products= productService.createProduct(newProduct);
        return new ResponseEntity<>(products, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Products> findProductById(@PathVariable("id") Long productId) {
        Products products= productService.findProductById(productId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Products>> findAllProduct(){
        List<Products> products= productService.findAllProduct();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Products> updateProduct(@PathVariable("id") Long productId,@Valid @RequestBody Products newProduct) {
        Products products = productService.updateProduct(productId,newProduct);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public String deleteProduct(@PathVariable("id")Long productId) {
        productService.deleteProduct(productId);
        return "Product deleted successfully";
    }

}
