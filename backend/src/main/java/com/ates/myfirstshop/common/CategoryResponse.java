package com.ates.myfirstshop.common;

import com.ates.myfirstshop.dto.CategoryDto;

import java.time.LocalDateTime;

public class CategoryResponse {

    private final boolean success;
    private final String message;

    private final CategoryDto entity;

    public CategoryResponse(boolean success, String message, CategoryDto entity){
        this.success = success;
        this.message = message;
        this.entity = entity;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public CategoryDto getEntity() { return entity; }

    public String getTimestamp(){
        return LocalDateTime.now().toString();
    }
}
