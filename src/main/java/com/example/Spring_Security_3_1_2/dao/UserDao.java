package com.example.Spring_Security_3_1_2.dao;

import com.example.Spring_Security_3_1_2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User getUserByUsername(String username);
}
