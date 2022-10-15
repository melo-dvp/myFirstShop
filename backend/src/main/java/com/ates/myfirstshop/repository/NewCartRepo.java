package com.ates.myfirstshop.repository;

import com.ates.myfirstshop.model.NewCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewCartRepo extends JpaRepository<NewCart, Integer> {
    List<NewCart> findAllByUsernameOrderByCreatedDateDesc(String username);
}
