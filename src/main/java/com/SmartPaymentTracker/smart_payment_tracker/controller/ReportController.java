package com.SmartPaymentTracker.smart_payment_tracker.controller;

import com.SmartPaymentTracker.smart_payment_tracker.entity.Transaction;
import com.SmartPaymentTracker.smart_payment_tracker.entity.User;
import com.SmartPaymentTracker.smart_payment_tracker.service.EmailService;
import com.SmartPaymentTracker.smart_payment_tracker.service.PdfReportService;
import com.SmartPaymentTracker.smart_payment_tracker.service.TransactionService;
import com.SmartPaymentTracker.smart_payment_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @Autowired
    private PdfReportService pdfReportService;

    @Autowired
    private EmailService emailService;

    // Show Reports page with filters
    @GetMapping
    public String reports(@AuthenticationPrincipal UserDetails userDetails,
                          @RequestParam(required = false) String startDate,
                          @RequestParam(required = false) String endDate,
                          @RequestParam(required = false) String category,
                          Model model) {

        User user = userService.getUserByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Transaction> transactions;

        // Filter by date range
        if (startDate != null && endDate != null) {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            transactions = transactionService.getTransactionByUserAndDateBetween(user, start, end);
        } else {
            transactions = transactionService.getTransactionByUser(user);
        }

        // Filter by category
        if (category != null && !category.isEmpty()) {
            transactions = transactions.stream()
                    .filter(t -> t.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
        }

        double totalIncome = transactions.stream()
                .filter(t -> "INCOME".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalExpense = transactions.stream()
                .filter(t -> "EXPENSE".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double balance = totalIncome - totalExpense;

        // Expenses by category
        Map<String, Double> expenseByCategory = transactions.stream()
                .filter(t -> "EXPENSE".equalsIgnoreCase(t.getType()))
                .collect(Collectors.groupingBy(Transaction::getCategory, Collectors.summingDouble(Transaction::getAmount)));

        // Monthly spending trend
        Map<String, Double> monthlyExpense = transactions.stream()
                .filter(t -> "EXPENSE".equalsIgnoreCase(t.getType()))
                .collect(Collectors.groupingBy(t -> t.getDate().getMonth().toString(), TreeMap::new, Collectors.summingDouble(Transaction::getAmount)));

        // Pass all to the frontend
        model.addAttribute("transactions", transactions);
        model.addAttribute("totalIncome", totalIncome);
        model.addAttribute("totalExpense", totalExpense);
        model.addAttribute("balance", balance);
        model.addAttribute("expenseByCategory", expenseByCategory);
        model.addAttribute("monthlyExpense", monthlyExpense);

        List<Transaction> transactions1 =
                transactionService.getTransactionByUser(user);

        Map<String, Object> insights =
                transactionService.generateInsights(transactions1);

        model.addAttribute("insights", insights);

        LocalDate now = LocalDate.now();

        List<Transaction> transactions2 =
                transactionService.getTransactionByUserAndDateBetween(
                        user,
                        now.withDayOfMonth(1),
                        now.withDayOfMonth(now.lengthOfMonth())
                );


        List<String> alerts =
                transactionService.generateSpendingAlerts(transactions2);

        model.addAttribute("alerts", alerts);
        List<Transaction> allTransactions =
                transactionService.getTransactionByUser(user);

        String comparison =
                transactionService.compareMonthlyExpenses(allTransactions);


        model.addAttribute("monthlyComparison", comparison);



        return "reports";
    }
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadPdf(
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.getUserByEmail(userDetails.getUsername())
                .orElseThrow();

        List<Transaction> transactions =
                transactionService.getTransactionByUser(user);


        byte[] pdfBytes =
                pdfReportService.generateExpenseReport(user, transactions);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=expense-report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
    @GetMapping("/email")
    public String emailPdf(
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {

        try {
            User user = userService.getUserByEmail(userDetails.getUsername()).orElseThrow();
            List<Transaction> txns = transactionService.getTransactionByUser(user);

            byte[] pdf = pdfReportService.generateExpenseReport(user, txns);
            emailService.sendPdfEmail(user.getEmail(), pdf);

            redirectAttributes.addFlashAttribute(
                    "success", "📧 Expense report emailed successfully"
            );

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "error", "❌ Email failed. Check email settings."
            );
        }

        return "redirect:/reports";
    }


}

