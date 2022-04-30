package com.example.Spring_Security_3_1_2.controller;

import com.example.Spring_Security_3_1_2.dao.RoleDao;
import com.example.Spring_Security_3_1_2.dao.UserDao;
import com.example.Spring_Security_3_1_2.model.Role;
import com.example.Spring_Security_3_1_2.model.User;
import com.example.Spring_Security_3_1_2.service.RoleService;
import com.example.Spring_Security_3_1_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserDao userDao;
    private final RoleDao roleDao;


    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, UserDao userDao, RoleDao roleDao) {
        this.userService = userService;
        this.roleService = roleService;
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @GetMapping
    public String printUsers(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user, Model model) {
        model.addAttribute("user", userService.getUserByUsername(user.getUsername()));
        model.addAttribute("userList", userService.index());
        model.addAttribute("roles", roleService.findAllRoles());
        return "admin";
    }

    @PostMapping
    public String add(@ModelAttribute("user") User user,
                      @RequestParam(value = "nameRoles") String[]roles) {
     //   setRolesFromRequest(user, roleAdmin, roleUser);
        user.setRoles(getSetOfRoles(roles));
        userService.addUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/{id}/remove")
    public String remove(@PathVariable("id") long id) {
        userService.removeUser(id);
        return "redirect:/admin/";
    }

    @GetMapping("/{id}/edit")
    public String edit(@ModelAttribute("user") User user,
                       ModelMap model,
                       @PathVariable("id") int id,
                       @RequestParam(value = "editRoles") String[]roles) {
       // setRolesFromRequest(user, roleAdmin, roleUser);
        user.setRoles(getSetOfRoles(roles));
        model.addAttribute("roles", roleService.findAllRoles());
        model.addAttribute("user", userService.getUserById(id));
        return "admin";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") long id,
                         @RequestParam(value = "editRoles") String[]roles) {
      //  setRolesFromRequest(user, roleAdmin, roleUser);
        user.setRoles(getSetOfRoles(roles));
        userService.updateUser(user);
        return "redirect:/admin/";
    }

    public HashSet<Role> getSetOfRoles(String[] roleNames) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : roleNames) {
            roleSet.add(roleDao.getRoleByName(role));
        }
        return (HashSet) roleSet;
    }
    }

