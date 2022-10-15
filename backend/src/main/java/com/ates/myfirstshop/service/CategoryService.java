package com.ates.myfirstshop.service;

import com.ates.myfirstshop.dto.CategoryDto;
import com.ates.myfirstshop.dto.FileUploadDto;
import com.ates.myfirstshop.dto.ProductDto;
import com.ates.myfirstshop.model.Category;
import com.ates.myfirstshop.model.FileUpload;
import com.ates.myfirstshop.model.Product;
import com.ates.myfirstshop.repository.CategoryRepo;
import com.ates.myfirstshop.repository.FileUploadRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    FileUploadRepo fileUploadRepo;

    public CategoryDto getCategoryDto(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setCategoryName(category.getCategoryName());
        categoryDto.setDescription(category.getDescription());
        categoryDto.setImageUrl(Base64.getDecoder().decode(category.getImageUrl()));

        Optional<FileUpload> optionalFile = fileUploadRepo.findById(category.getFileUpload().getId());
        FileUploadDto fileUploadDto = new FileUploadDto();
        fileUploadDto.setId(optionalFile.get().getId());
        fileUploadDto.setName(optionalFile.get().getName());
        fileUploadDto.setSize(optionalFile.get().getSize());
        fileUploadDto.setType(optionalFile.get().getType());
        fileUploadDto.setLastModifiedDate(optionalFile.get().getLastModifiedDate());
        fileUploadDto.setLastModified(optionalFile.get().getLastModified());

        categoryDto.setFileUploadDto(fileUploadDto);

        return categoryDto;
    }

    public List<CategoryDto> ListOfCategories(){
        List<Category> allCategories = categoryRepo.findAll();

        List<CategoryDto> categoryDtos = new ArrayList<>();
        for(Category category: allCategories){
            categoryDtos.add(getCategoryDto(category));
        }
        return categoryDtos;
    }

    public int createCategory(String categoryName, String description, MultipartFile file, FileUpload fileUpload){
        Category category = new Category();

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains("..")){
            System.out.println("not a valid file");
        }

        category.setCategoryName(categoryName);
        category.setDescription(description);
        category.setFileUpload(fileUpload);
        try {
            category.setImageUrl(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        categoryRepo.save(category);
        return category.getId();
    }

    public void editCategory(int categoryId, String name, String desc, MultipartFile file){
        Category category = categoryRepo.getReferenceById(categoryId);
        category.setCategoryName(name);
        category.setDescription(desc);
        try {
            category.setImageUrl(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        categoryRepo.save(category);
    }

    public boolean findById(int categoryId){
        return categoryRepo.findById(categoryId).isPresent();
    }

    public void deleteCategory(int categoryId){
        categoryRepo.deleteById(categoryId);
    }

}
