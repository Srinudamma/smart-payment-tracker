package com.SmartPaymentTracker.smart_payment_tracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // maps to login.html
    }

    @GetMapping("/home")
    public String homePage() {
        return "home"; // maps to home.html
    }
    @GetMapping("/transactions")
    public String transactionsPage() {
        return "transactions"; // maps to transactions.html
    }

    //@GetMapping("/reports")
    public String reportsPage() {
        return "reports"; // maps to reports.html
    }
    @GetMapping("/")
    public String home() {
        return "home";  // This should point to home.html in templates
    }

}

