package com.ates.myfirstshop.controller;

import com.ates.myfirstshop.common.ApiResponse;
import com.ates.myfirstshop.model.Category;
import com.ates.myfirstshop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/getAll")
    public List<Category> getAllCagegories(){
        return categoryService.ListOfCategories();
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category){
        categoryService.createCategory(category);
        return new ResponseEntity<>(new ApiResponse(true, "created the category"), HttpStatus.CREATED);
    }

    @PostMapping("update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") int categoryId, @RequestBody Category category){
        if(!categoryService.findById(categoryId)){
            return new ResponseEntity<>(new ApiResponse(false, "The categoryId does not exists"), HttpStatus.NOT_FOUND);
        }
        categoryService.editCategory(categoryId, category);
        return new ResponseEntity<>(new ApiResponse(true, "updated the category"), HttpStatus.OK);
    }

}
