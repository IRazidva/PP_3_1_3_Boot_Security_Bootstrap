package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserDetailsImpl;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/admin")
    private String allUsers(@AuthenticationPrincipal UserDetailsImpl user, Model model) {
        model.addAttribute("currentUser", user.getUser());
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("listRoles", userService.getAllRoles());
        return "admin";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/edit")
    public String GetEditModal(@RequestParam(name="id",required = true) int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("listRoles", userService.getAllRoles());
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
        model.addAttribute("user",userService.getById(id));
        model.addAttribute("listRoles", userService.getAllRoles());
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
