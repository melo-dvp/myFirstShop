package com.ates.myfirstshop.service;

import com.ates.myfirstshop.dto.cart.AddToCartDto;
import com.ates.myfirstshop.dto.cart.CartDto;
import com.ates.myfirstshop.dto.cart.CartItemDto;
import com.ates.myfirstshop.exceptions.CustomException;
import com.ates.myfirstshop.model.Cart;
import com.ates.myfirstshop.model.Product;
import com.ates.myfirstshop.model.User;
import com.ates.myfirstshop.repository.CartRepo;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductService productService;


    public void addToCart(AddToCartDto addToCartDto, User user) {

        // validate if the product id is valid
        Product product = productService.findById(addToCartDto.getProductId());

        // save the cart
        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());

        cartRepo.save(cart);
    }

    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepo.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItems = new ArrayList<>();

        double totalCost = 0;

        for(Cart cart: cartList){
            CartItemDto cartItemDto = new CartItemDto(cart);
            totalCost += cartItemDto.getQuantity() * cart.getProduct().getPrice();
            cartItems.add(cartItemDto);
        }

        CartDto cartDto = new CartDto();
        cartDto.setTotalCost(totalCost);
        cartDto.setCartItems(cartItems);

        return cartDto;
    }

    public void deleteCartItem(Integer itemId, User user) {

        // the item id belongs to user
        Optional<Cart> optionalCart = cartRepo.findById(itemId);

        if(optionalCart.isEmpty()){
            throw new CustomException("cart item id is invalid: " + itemId);
        }

        Cart cart = optionalCart.get();

        if(cart.getUser() != user){
            throw new CustomException("cart item does not belong to user: " + itemId);
        }

        cartRepo.delete(cart);
    }
    public void deleteAllCartItems(User user){
        List<Cart> cartList = cartRepo.findAllByUserOrderByCreatedDateDesc(user);
        for(Cart cart: cartList){
            cartRepo.delete(cart);
        }
    }
}
