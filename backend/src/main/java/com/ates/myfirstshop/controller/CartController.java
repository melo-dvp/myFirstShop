package com.ates.myfirstshop.controller;

import com.ates.myfirstshop.common.ApiResponse;
import com.ates.myfirstshop.dto.cart.AddToCartDto;
import com.ates.myfirstshop.dto.cart.CartDto;
import com.ates.myfirstshop.model.Product;
import com.ates.myfirstshop.model.User;
import com.ates.myfirstshop.service.AuthenticationService;
import com.ates.myfirstshop.service.CartService;
import com.ates.myfirstshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto, @RequestParam("token") String token){

        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);

        cartService.addToCart(addToCartDto, user);

        return new ResponseEntity<>(new ApiResponse(true, "added to cart"), HttpStatus.CREATED);
    }


    @GetMapping("/getAll")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token){
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);

        CartDto cartDto = cartService.listCartItems(user);

        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Integer itemId, @RequestParam("token") String token){
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);

        cartService.deleteCartItem(itemId, user);

        return new ResponseEntity<>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<ApiResponse> deleteAll(@RequestParam("token") String token){
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);

        cartService.deleteAllCartItems(user);
        return new ResponseEntity<>(new ApiResponse(true, "All Items has been removed"), HttpStatus.OK);
    }

}
