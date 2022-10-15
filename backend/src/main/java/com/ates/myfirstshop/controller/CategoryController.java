package com.ates.myfirstshop.controller;

import com.ates.myfirstshop.common.CategoryResponse;
import com.ates.myfirstshop.common.DeleteResponse;
import com.ates.myfirstshop.common.PostResponse;
import com.ates.myfirstshop.common.UpdateReponse;
import com.ates.myfirstshop.dto.CategoryDto;
import com.ates.myfirstshop.dto.FileUploadDto;
import com.ates.myfirstshop.model.Category;
import com.ates.myfirstshop.model.FileUpload;
import com.ates.myfirstshop.repository.CategoryRepo;
import com.ates.myfirstshop.repository.FileUploadRepo;
import com.ates.myfirstshop.service.CategoryService;
import com.ates.myfirstshop.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    FileUploadRepo fileUploadRepo;

    @Autowired
    FileUploadService fileUploadService;

    @GetMapping("/getAll")
    public List<CategoryDto> getAllCagegories(){
        return categoryService.ListOfCategories();
    }

    @PostMapping("/create")
    @RolesAllowed("admin")
    public ResponseEntity<CategoryResponse> createCategory(
            @RequestParam("file")MultipartFile file,
            @RequestParam("categoryName")String categoryName,
            @RequestParam("description")String description
    ){
        FileUploadDto fileUploadDto = fileUploadService.saveImage(file);
        Optional<FileUpload> optionalFile = fileUploadRepo.findById(fileUploadDto.getId());
        int categoryId = categoryService.createCategory(categoryName, description, file, optionalFile.get());
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName(categoryName);
        categoryDto.setDescription(description);
        categoryDto.setFileUploadDto(fileUploadDto);
        categoryDto.setId(categoryId);
        try {
            categoryDto.setImageUrl(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(new CategoryResponse(true, "created the category", categoryDto), HttpStatus.CREATED);
    }


    @PutMapping("update/{categoryId}")
    @RolesAllowed("admin")
    public ResponseEntity<UpdateReponse> updateCategory(
            @PathVariable("categoryId") int categoryId,
            @RequestParam("file")MultipartFile file,
            @RequestParam("categoryName")String categoryName,
            @RequestParam("description")String description,
            @RequestParam("fileId")String fileId
            ){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName(categoryName);
        categoryDto.setDescription(description);
        categoryDto.setId(categoryId);
        try {
            categoryDto.setImageUrl(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(!categoryService.findById(categoryId)){
            return new ResponseEntity<>(new UpdateReponse(false, "The categoryId does not exists", categoryDto), HttpStatus.NOT_FOUND);
        }
        categoryService.editCategory(categoryId, categoryName, description, file);
        FileUploadDto fileUploadDto = fileUploadService.editFileUpload(Integer.parseInt(fileId), file);
        categoryDto.setFileUploadDto(fileUploadDto);
        return new ResponseEntity<>(new UpdateReponse(true, "updated the category", categoryDto), HttpStatus.OK);
    }

    @DeleteMapping("delete/{categoryId}")
    @RolesAllowed("admin")
    public ResponseEntity<DeleteResponse> deleteCategory(@PathVariable("categoryId") int categoryId){
        if(!categoryService.findById(categoryId)){
            return new ResponseEntity<>(new DeleteResponse(false, "The categoryId does not exists", categoryId), HttpStatus.NOT_FOUND);
        }
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new DeleteResponse(true, "deleted the category", categoryId), HttpStatus.OK);
    }

}
