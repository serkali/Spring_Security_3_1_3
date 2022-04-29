package com.example.Spring_Security_3_1_2.dao;

import com.example.Spring_Security_3_1_2.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface RoleDao  extends JpaRepository<Role, Long> {
    List<Role> findAll();
    Role getRoleByName(String name);
}
