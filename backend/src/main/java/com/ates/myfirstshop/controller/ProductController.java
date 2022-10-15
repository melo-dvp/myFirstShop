package com.ates.myfirstshop.controller;

import com.ates.myfirstshop.common.ApiResponse;
import com.ates.myfirstshop.common.DeleteResponse;
import com.ates.myfirstshop.common.PostResponse;
import com.ates.myfirstshop.common.UpdateReponse;
import com.ates.myfirstshop.dto.FileUploadDto;
import com.ates.myfirstshop.dto.ProductDto;
import com.ates.myfirstshop.model.Category;
import com.ates.myfirstshop.model.FileUpload;
import com.ates.myfirstshop.model.Product;
import com.ates.myfirstshop.repository.CategoryRepo;
import com.ates.myfirstshop.repository.FileUploadRepo;
import com.ates.myfirstshop.service.FileUploadService;
import com.ates.myfirstshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;
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

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    FileUploadRepo fileUploadRepo;

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductDto>> getProducts(){
        List<ProductDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/add")
    @RolesAllowed("admin")
    public ResponseEntity<PostResponse> createProduct(
            @RequestParam("file") MultipartFile file,
            @RequestParam("categoryName")String name,
            @RequestParam("description")String description,
            @RequestParam("description")String price,
            @RequestParam("description")String categoryId
    ){
        Optional<Category> optionalCategory = categoryRepo.findById(Integer.parseInt(categoryId));
        ProductDto productDto = new ProductDto();
        productDto.setName(name);
        productDto.setDescription(description);
        productDto.setPrice(Double.parseDouble(price));
        try {
            productDto.setImageUrl(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(!optionalCategory.isPresent()){
            return new ResponseEntity<>(new PostResponse(false, "category does not exists", productDto), HttpStatus.NOT_FOUND);
        }

        FileUploadDto fileUploadDto = fileUploadService.saveImage(file);
        Optional<FileUpload> optionalFile = fileUploadRepo.findById(fileUploadDto.getId());

        int productId = productService.createProduct(productDto, optionalCategory.get(), optionalFile.get(), file);
        productDto.setId(productId);
        return new ResponseEntity<>(new PostResponse(true, "product has been added", productDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{productId}")
    @RolesAllowed("admin")
    public ResponseEntity<UpdateReponse> updateProduct(@PathVariable("productId") Integer productId, @RequestBody ProductDto productDto) throws Exception {
        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent()){
            return new ResponseEntity<>(new UpdateReponse(false, "category does not exists", productDto), HttpStatus.NOT_FOUND);
        }
        productService.updateProduct(productDto, productId, optionalCategory.get());
        return new ResponseEntity<>(new UpdateReponse(true, "product has been updated", productDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    @RolesAllowed("admin")
    public ResponseEntity<DeleteResponse> deleteProduct(@PathVariable("productId") int productId) throws Exception {
        if(!productService.findByIdBoolean(productId)){
            return new ResponseEntity<>(new DeleteResponse(false, "The productId does not exists", productId), HttpStatus.NOT_FOUND);
        }
        productService.deleteProduct(productId);
        return new ResponseEntity<>(new DeleteResponse(true, "product has been deleted", productId), HttpStatus.OK);
    }

}
