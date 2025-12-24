package com.SmartPaymentTracker.smart_payment_tracker.controller;

import com.SmartPaymentTracker.smart_payment_tracker.entity.Transaction;
import com.SmartPaymentTracker.smart_payment_tracker.entity.User;
import com.SmartPaymentTracker.smart_payment_tracker.service.TransactionService;
import com.SmartPaymentTracker.smart_payment_tracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/web/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    // Show transaction page
    @GetMapping
    public String listTransactions(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Optional<User> user = userService.getUserByEmail(userDetails.getUsername());
        if (user.isPresent()) {
            List<Transaction> transactions = transactionService.getTransactionByUser(user.get());
            Transaction newTxn = new Transaction();
            newTxn.setType("EXPENSE");
            model.addAttribute("transactions", transactions);
            model.addAttribute("transaction", newTxn); // empty form for new entry
        }
        return "transactions";
    }

    // Save or update transaction
    @PostMapping
    public String saveTransaction(@Valid @ModelAttribute("transaction") Transaction transaction,
                                  BindingResult result,
                                  @AuthenticationPrincipal UserDetails userDetails,
                                  Model model) {

        // Fetch the logged-in user first
        User user = userService.getUserByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (result.hasErrors()) {
            model.addAttribute("transactions", transactionService.getTransactionByUser(user));
            return "transactions"; // re-render page with errors
        }

        transaction.setUser(user);
        transactionService.saveTransaction(transaction);

        return "redirect:/web/transactions";
    }



    // Edit transaction (prefill form)
    @GetMapping("/edit/{id}")
    public String editTransaction(@PathVariable Long id,
                                  @AuthenticationPrincipal UserDetails userDetails,
                                  Model model) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        Optional<User> user = userService.getUserByEmail(userDetails.getUsername());

        if (transaction.isPresent() && user.isPresent() &&
                transaction.get().getUser().getId().equals(user.get().getId())) {
            model.addAttribute("transaction", transaction.get());
            model.addAttribute("transactions", transactionService.getTransactionByUser(user.get()));
            return "transactions"; // same page but prefilled form
        }

        return "redirect:/web/transactions";
    }

    // Delete transaction
    @PostMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable Long id,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        Optional<User> user = userService.getUserByEmail(userDetails.getUsername());

        if (transaction.isPresent() && user.isPresent() &&
                transaction.get().getUser().getId().equals(user.get().getId())) {
            transactionService.deleteTransaction(id);
        }
        return "redirect:/web/transactions";
    }
}
