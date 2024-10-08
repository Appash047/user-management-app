package com.gokulinfocare.userapp.controller;

import com.gokulinfocare.userapp.exception.InvalidUserDetailsException;
import com.gokulinfocare.userapp.model.User;
import com.gokulinfocare.userapp.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/add")
    public String saveUser(@Valid User user, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            log.warn("Validation errors occurred while adding user: {}", result.getFieldErrors());
            return "add-user";
        }
        try {
            User savedUser = userService.saveUser(user);
            redirectAttributes.addFlashAttribute("message", "User Saved");
            log.info("User added successfully: {}", savedUser);
        } catch (InvalidUserDetailsException e) {
            model.addAttribute("error", e.getMessage());
            log.warn("Failed to add user: {}", e.getMessage());
            return "add-user";
        }
        return "redirect:/";
    }

    @GetMapping("/all")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "all-users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "edit-user";
        } else {
            model.addAttribute("error", "User not found");
            return "redirect:/users/all";
        }
    }


    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isPresent()) {
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("message", "User updated successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "User not found");
        }
        return "redirect:/users/all";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            log.info("User Deleted successfully: {id}", id);
            redirectAttributes.addFlashAttribute("message", "User " + id + " deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting user");
        }
        return "redirect:/users/all";
    }
}