package com.ates.myfirstshop.repository;

import com.ates.myfirstshop.model.User;
import com.ates.myfirstshop.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepo extends JpaRepository<WishList, Integer> {

    List<WishList> findAllByUserOrderByCreatedDateDesc(User user);
}
