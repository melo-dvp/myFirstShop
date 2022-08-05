package com.ates.myfirstshop.service;

import com.ates.myfirstshop.model.Category;
import com.ates.myfirstshop.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    public List<Category> ListOfCategories(){
        return categoryRepo.findAll();
    }

    public void createCategory(Category category){
        categoryRepo.save(category);
    }

    public void editCategory(int categoryId, Category updateCategory){
        Category category = categoryRepo.getById(categoryId);
        category.setCategoryName(updateCategory.getCategoryName());
        category.setDescription(updateCategory.getDescription());
        category.setImageUrl(updateCategory.getImageUrl());
        categoryRepo.save(category);
    }

    public boolean findById(int categoryId){
        return categoryRepo.findById(categoryId).isPresent();
    }

}
