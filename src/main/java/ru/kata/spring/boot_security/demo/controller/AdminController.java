package ru.kata.spring.boot_security.demo.controller;


import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final RoleRepository roleRepository;
    private final UserService userService;

    public AdminController(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @GetMapping("")
    public String indexView(Model model) {
        List<User> listUser = userService.findAll();
        model.addAttribute("users", listUser);
        return "indexSpringMvc";
    }

    @GetMapping("/addUser")
    public String addUserView(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        List<Role> roles = (List<Role>) roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        return "addUserField";
    }

    @PostMapping("/addOrUpdate")
    public String add(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Role> roles = (List<Role>) roleRepository.findAll();
            model.addAttribute("allRoles", roles);
            return "addUserField";
        } else {
            userService.save(user);
            return "redirect:/admin";
        }

    }

    @PostMapping("/removeUser")
    public String deleteUser(@ModelAttribute("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/updateUser")
    public String updateUser(@ModelAttribute("id") Long id, Model model) {
        User user = userService.findById(id).get();
        model.addAttribute("user", user);
        List<Role> roles = (List<Role>) roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        return "addUserField";
    }



}
