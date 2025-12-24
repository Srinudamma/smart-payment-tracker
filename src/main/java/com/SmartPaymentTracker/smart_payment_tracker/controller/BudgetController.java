package com.SmartPaymentTracker.smart_payment_tracker.controller;

import com.SmartPaymentTracker.smart_payment_tracker.entity.Budget;
import com.SmartPaymentTracker.smart_payment_tracker.entity.User;
import com.SmartPaymentTracker.smart_payment_tracker.service.BudgetService;
import com.SmartPaymentTracker.smart_payment_tracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/settings/budget")
public class BudgetController {

    private final BudgetService budgetService;
    private final UserService userService;

    public BudgetController(BudgetService budgetService, UserService userService) {
        this.budgetService = budgetService;
        this.userService = userService;
    }

    @GetMapping
    public String showBudgets(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        User user = userService.getUserByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("budget", new Budget());                 // ✅ REQUIRED
        model.addAttribute("budgets", budgetService.getBudgetsByUser(user));

        return "settings"; // settings.html
    }

    @PostMapping
    public String saveBudget(@Valid @ModelAttribute("budget") Budget budget,
                             BindingResult result,
                             @AuthenticationPrincipal UserDetails userDetails,
                             Model model) {

        User user = userService.getUserByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (result.hasErrors()) {
            model.addAttribute("budgets", budgetService.getBudgetsByUser(user));
            return "settings"; // SAME PAGE
        }

        budget.setUser(user);
        budgetService.saveOrUpdateBudget(budget);

        return "redirect:/settings/budget";
    }
}



