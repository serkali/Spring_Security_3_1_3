package com.example.Spring_Security_3_1_2.service;

import com.example.Spring_Security_3_1_2.dao.RoleDao;
import com.example.Spring_Security_3_1_2.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public List<Role> findAllRoles() {
        return roleDao.findAll();
    }

    public Role getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }
}
