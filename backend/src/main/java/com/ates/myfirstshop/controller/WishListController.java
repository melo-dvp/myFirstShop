package com.ates.myfirstshop.controller;

import com.ates.myfirstshop.common.ApiResponse;
import com.ates.myfirstshop.dto.ProductDto;
import com.ates.myfirstshop.model.Product;
import com.ates.myfirstshop.model.User;
import com.ates.myfirstshop.model.WishList;
import com.ates.myfirstshop.service.AuthenticationService;
import com.ates.myfirstshop.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    WishListService wishListService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishList(
            @RequestBody Product product,
            @RequestParam("token") String token){

        // authenticate the token
        authenticationService.authenticate(token);

        // find the user
        User user = authenticationService.getUser(token);

        // save the wishlist
        WishList wishList = new WishList(user, product);
        wishListService.createWishList(wishList);
        ApiResponse apiResponse = new ApiResponse(true, "Added to wishlist");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    // get all wishlist item for a user
    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token){
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);

        List<ProductDto> productDtos = wishListService.getWishListForUser(user);

        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }


}
