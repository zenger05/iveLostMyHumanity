package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {
    @GetMapping("/user")
    public String userInfo(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("username", username);
        return "user";
    }
}
