package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(value = "/user")
    private String viewUser(Model model) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user", userService.getByName(userName));
        return "user";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/admin")
    private String allUsers(Model model) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Role> listRoles = (List<Role>) userService.allRoles();
        model.addAttribute("currentUser", userService.getByName(userName));
        model.addAttribute("userList", userService.allUsers());
        model.addAttribute("listRoles", listRoles);
        return "admin";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/edit")
    public String GetEditModal(@RequestParam(name="id",required = true) int id, Model model) {
        User user = userService.getById(id);
        List<Role> listRoles = (List<Role>) userService.allRoles();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "editModal";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/edit")
    public String PostEditModal(@ModelAttribute("user") User user) {
        userService.edit(user);
        return "redirect:/admin";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/delete")
    private String GetDeleteModal(@RequestParam(name="id",required = true) int id, Model model) {
        User user = userService.getById(id);
        List<Role> listRoles = (List<Role>) userService.allRoles();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "deleteModal";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/delete")
    private String PostDeleteModal(@ModelAttribute("user") User user) {
        userService.deleteById(user.getId());
        return "redirect:/admin";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/add")
    public String PostAdd(@ModelAttribute User user) {
        userService.add(user);
        return "redirect:/admin";
    }
}
