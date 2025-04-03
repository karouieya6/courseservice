package com.example.courseservice.controller;

import com.example.courseservice.model.Category;
import com.example.courseservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


    @RestController
    @RequestMapping("api/categories")
    public class CategoryController {

        @Autowired
        private CategoryService categoryService;

        @PostMapping
        public ResponseEntity<Category> addCategory(@RequestBody Category category) {
            return ResponseEntity.ok(categoryService.addCategory(category));
        }

        @GetMapping
        public List<Category> getAllCategories() {
            return categoryService.getAllCategories();
        }


}
