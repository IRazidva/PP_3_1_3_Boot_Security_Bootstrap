package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.service.UserDetailsImpl;

@Controller
public class UserController {

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping("/user")
    private String viewUser(@AuthenticationPrincipal UserDetailsImpl user, Model model) {
        model.addAttribute("user", user.getUser());
        return "user";
    }
}
