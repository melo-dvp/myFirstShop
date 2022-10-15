package com.ates.myfirstshop.dto.cart;

import com.ates.myfirstshop.model.NewCart;
import com.ates.myfirstshop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCartItemDto {

    private Integer id;
    private Integer quantity;
    private Product product;

    public NewCartItemDto(NewCart cart){
        this.id = cart.getId();
        this.quantity = cart.getQuantity();
        this.setProduct(cart.getProduct());
    }
}
