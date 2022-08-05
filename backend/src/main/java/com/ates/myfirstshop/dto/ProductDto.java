package com.ates.myfirstshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    // for create it can be optional
    // for update we need the id
    private Integer id;

    private @NotNull String name;
    private @NotNull String imageUrl;
    private @NotNull double price;
    private @NotNull String description;
    private @NotNull Integer categoryId;


}
