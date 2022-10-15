package com.ates.myfirstshop.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCartDto {

    private List<NewCartItemDto> cartItems;
    private double totalCost;
}
