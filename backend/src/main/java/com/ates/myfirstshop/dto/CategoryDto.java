package com.ates.myfirstshop.dto;

import com.ates.myfirstshop.model.FileUpload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Integer id;
    private @NotNull String categoryName;
    private @NotNull String description;
    private @NotNull byte[] imageUrl;
    private @NotNull FileUploadDto fileUploadDto;
}
