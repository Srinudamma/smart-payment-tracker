package com.SmartPaymentTracker.smart_payment_tracker.controller;

import com.SmartPaymentTracker.smart_payment_tracker.entity.User;
import com.SmartPaymentTracker.smart_payment_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // register.html
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        userService.registerUser(user); // delegate to service
        model.addAttribute("success", "Registration successful! Please log in.");
        return "login"; // redirect to login page
    }
}
