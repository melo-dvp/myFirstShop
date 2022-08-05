package com.ates.myfirstshop.controller;

import com.ates.myfirstshop.common.ApiResponse;
import com.ates.myfirstshop.dto.ProductDto;
import com.ates.myfirstshop.model.Category;
import com.ates.myfirstshop.model.Product;
import com.ates.myfirstshop.repository.CategoryRepo;
import com.ates.myfirstshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryRepo categoryRepo;

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductDto>> getProducts(){
        List<ProductDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto){
        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false, "category does not exists"), HttpStatus.NOT_FOUND);
        }
        productService.createProduct(productDto, optionalCategory.get());
        return new ResponseEntity<>(new ApiResponse(true, "product has been added"), HttpStatus.CREATED);
    }

    @PostMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Integer productId, @RequestBody ProductDto productDto) throws Exception {
        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false, "category does not exists"), HttpStatus.NOT_FOUND);
        }
        productService.updateProduct(productDto, productId, optionalCategory.get());
        return new ResponseEntity<>(new ApiResponse(true, "product has been updated"), HttpStatus.OK);
    }

}
