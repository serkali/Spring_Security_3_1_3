package com.example.Spring_Security_3_1_2.controller;

import com.example.Spring_Security_3_1_2.model.Role;
import com.example.Spring_Security_3_1_2.model.User;
import com.example.Spring_Security_3_1_2.service.RoleService;
import com.example.Spring_Security_3_1_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {


    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getUser(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByUsername(principal.getName()));
        model.addAttribute("roles", userService.getUserByUsername(principal.getName()).getRoles());
        return "show_user";
    }

    @GetMapping("/user_list")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.index());
        return "all_users";
    }

    @GetMapping("/new")
    public String formNewUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("listRoles", roleService.findAllRoles());
        return "new";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(required=false) String roleAdmin,
                             @RequestParam(required=false) String roleUser) {
        setRolesFromRequest(user, roleAdmin, roleUser);
        userService.addUser(user);
        return "redirect:/admin/user_list";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("person") User user,
                         @RequestParam(required=false) String roleAdmin,
                         @RequestParam(required=false) String roleUser) {
        setRolesFromRequest(user, roleAdmin, roleUser);
        userService.updateUser(user);
        return "redirect:/admin/user_list";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        userService.removeUser(id);
        return "redirect:/admin/user_list";
    }

    private void setRolesFromRequest(@ModelAttribute("person") User user,
                                     @RequestParam(required = false) String roleAdmin,
                                     @RequestParam(required = false) String roleUser) {
        Set<Role> roles = new HashSet<>();
        if (roleAdmin != null) {
            roles.add(roleService.getRoleByName(roleAdmin));
        }
        if (roleUser != null) {
            roles.add(roleService.getRoleByName(roleUser));
        }
        user.setRoles(roles);
    }
}
