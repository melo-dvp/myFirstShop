package com.ates.myfirstshop.repository;

import com.ates.myfirstshop.model.Cart;
import com.ates.myfirstshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);

}
