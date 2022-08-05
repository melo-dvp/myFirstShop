package com.ates.myfirstshop.repository;

import com.ates.myfirstshop.model.AuthenticationToken;
import com.ates.myfirstshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends JpaRepository<AuthenticationToken, Integer> {

    AuthenticationToken findByUser(User user);
    AuthenticationToken findByToken(String token);
}
