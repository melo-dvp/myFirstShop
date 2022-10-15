package com.ates.myfirstshop.service;

import com.ates.myfirstshop.dto.cart.AddToCartDto;
import com.ates.myfirstshop.dto.cart.NewCartDto;
import com.ates.myfirstshop.dto.cart.NewCartItemDto;
import com.ates.myfirstshop.exceptions.CustomException;
import com.ates.myfirstshop.model.NewCart;
import com.ates.myfirstshop.model.Product;
import com.ates.myfirstshop.repository.NewCartRepo;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NewCartService {

    @Autowired
    private NewCartRepo cartRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private KeycloakService keycloakService;

    public Integer addToCart(AddToCartDto addToCartDto, String username) {

        // validate if the product id is valid
        Product product = productService.findById(addToCartDto.getProductId());

        List<UserRepresentation> optionalUser = keycloakService.getUser(username);
        if(optionalUser.isEmpty()){
            throw new CustomException("username is invalid: " + username);
        }

        // save the cart
        NewCart cart = new NewCart();
        cart.setProduct(product);
        cart.setUsername(username);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());

        cartRepo.save(cart);
        return cart.getId();
    }

    public NewCartDto listCartItems(String username) {

        List<UserRepresentation> optionalUser = keycloakService.getUser(username);
        if(optionalUser.isEmpty()){
            throw new CustomException("username is invalid: " + username);
        }

        List<NewCart> cartList = cartRepo.findAllByUsernameOrderByCreatedDateDesc(username);
        List<NewCartItemDto> cartItems = new ArrayList<>();

        double totalCost = 0;

        for(NewCart cart: cartList){
            NewCartItemDto cartItemDto = new NewCartItemDto(cart);
            totalCost += cartItemDto.getQuantity() * cart.getProduct().getPrice();
            cartItems.add(cartItemDto);
        }

        NewCartDto cartDto = new NewCartDto();
        cartDto.setTotalCost(totalCost);
        cartDto.setCartItems(cartItems);

        return cartDto;
    }

    public void deleteCartItem(Integer itemId, String username) {

        List<UserRepresentation> optionalUser = keycloakService.getUser(username);
        if(optionalUser.isEmpty()){
            throw new CustomException("username is invalid: " + username);
        }

        // the item id belongs to user
        Optional<NewCart> optionalCart = cartRepo.findById(itemId);

        if(optionalCart.isEmpty()){
            throw new CustomException("cart item id is invalid: " + itemId);
        }

        NewCart cart = optionalCart.get();

        System.out.println(cart.getUsername());

        if(!Objects.equals(cart.getUsername(), username)){
            throw new CustomException("cart item does not belong to user: " + itemId);
        }

        cartRepo.delete(cart);
    }

    public void deleteAllCartItems(String username){
        List<UserRepresentation> optionalUser = keycloakService.getUser(username);
        if(optionalUser.isEmpty()){
            throw new CustomException("username is invalid: " + username);
        }

        List<NewCart> cartList = cartRepo.findAllByUsernameOrderByCreatedDateDesc(username);
        for(NewCart cart: cartList){
            cartRepo.delete(cart);
        }
    }


}
