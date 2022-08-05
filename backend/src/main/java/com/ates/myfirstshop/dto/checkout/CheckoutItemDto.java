package com.ates.myfirstshop.dto.checkout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutItemDto {

    private String productName;
    private Integer quantity;
    private double price;
    private long productId;
    private Integer userId;

}
