package com.ates.myfirstshop.controller;

import com.ates.myfirstshop.common.ApiResponse;
import com.ates.myfirstshop.common.DeleteResponse;
import com.ates.myfirstshop.common.PostResponse;
import com.ates.myfirstshop.dto.cart.AddToCartDto;
import com.ates.myfirstshop.dto.cart.NewCartDto;
import com.ates.myfirstshop.service.AuthenticationService;
import com.ates.myfirstshop.service.NewCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/newCart")
public class NewCartController {

    @Autowired
    private NewCartService cartService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/add")
    public ResponseEntity<PostResponse> addToCart(
            @RequestBody AddToCartDto addToCartDto,
            @RequestParam("token") String token
    ){
        if(authenticationService.successfullAuthentication(token)){
            String username = authenticationService.extractUserName(token);
            int cartId = cartService.addToCart(addToCartDto, username);
            addToCartDto.setId(cartId);
            return new ResponseEntity<>(new PostResponse(true, "added to cart", addToCartDto), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(new PostResponse(false, "invalid Token", null), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<NewCartDto> getCartItems(@RequestParam("token") String token){
        if(authenticationService.successfullAuthentication(token)){
            String username = authenticationService.extractUserName(token);
            NewCartDto cartDto = cartService.listCartItems(username);
            return new ResponseEntity<>(cartDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<DeleteResponse> deleteCartItem(@PathVariable("cartItemId") Integer itemId, @RequestParam("token") String token){
        if(authenticationService.successfullAuthentication(token)){
            String username = authenticationService.extractUserName(token);
            cartService.deleteCartItem(itemId, username);
            return new ResponseEntity<>(new DeleteResponse(true, "Item has been removed", itemId), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<ApiResponse> deleteAll(@RequestParam("token") String token){
        if(authenticationService.successfullAuthentication(token)){
            String username = authenticationService.extractUserName(token);
            cartService.deleteAllCartItems(username);
            return new ResponseEntity<>(new ApiResponse(true, "All Items has been removed"), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ApiResponse(false, "invalid Token"), HttpStatus.FORBIDDEN);
        }
    }



}
