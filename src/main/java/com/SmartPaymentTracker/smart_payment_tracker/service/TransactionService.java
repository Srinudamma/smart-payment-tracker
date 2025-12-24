package com.SmartPaymentTracker.smart_payment_tracker.service;

import com.SmartPaymentTracker.smart_payment_tracker.entity.Transaction;
import com.SmartPaymentTracker.smart_payment_tracker.entity.User;
import com.SmartPaymentTracker.smart_payment_tracker.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TransactionService
{
    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction saveTransaction(Transaction transaction)
    {
        return transactionRepository.save(transaction);
    }

    //get All Transactions

    public List<Transaction> getAllTransactions()
    {
        return transactionRepository.findAll();
    }

    //Get Transaction By Id

    public Optional<Transaction> getTransactionById(Long id)
    {
        return transactionRepository.findById(id);
    }
    // delete transaction by Id
    public void deleteById(Long id)
    {
        transactionRepository.deleteById(id);
    }
    //get all transactions of each user
    public List<Transaction> getTransactionByUser(User user)
    {
        return transactionRepository.findByUser(user);
    }
    //get transactions of user on a  specific date
    public List<Transaction> getTransactionByUserAndDate(User user, LocalDate date)
    {
        return transactionRepository.findByUserAndDate(user,date);
    }

    //get transaction by type
     public List<Transaction> getTransactionByUserAndType(User user,String type)
     {
         return transactionRepository.findByUserAndType(user,type);
     }

     //Get Transactions between two dates
    public List<Transaction> getTransactionByUserAndDateBetween(User user,LocalDate start, LocalDate end)
    {
        return transactionRepository.findByUserAndDateBetween(user,start,end);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
    public Map<String, Object> generateInsights(List<Transaction> transactions) {

        Map<String, Object> insights = new HashMap<>();

        double totalExpense = 0;
        Map<String, Double> categoryTotals = new HashMap<>();

        for (Transaction t : transactions) {
            if ("EXPENSE".equals(t.getType())) {
                totalExpense += t.getAmount();
                categoryTotals.merge(
                        t.getCategory(),
                        t.getAmount(),
                        Double::sum
                );
            }
        }

        // Highest spending category
        String topCategory = null;
        double maxAmount = 0;

        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            if (entry.getValue() > maxAmount) {
                maxAmount = entry.getValue();
                topCategory = entry.getKey();
            }
        }

        insights.put("topCategory", topCategory);
        insights.put("topCategoryAmount", maxAmount);

        // Percentage
        double percentage = (maxAmount / totalExpense) * 100;
        insights.put("topCategoryPercentage", Math.round(percentage));

        return insights;
    }
    public List<String> generateSpendingAlerts(List<Transaction> transactions) {

        List<String> alerts = new ArrayList<>();

        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();

        Map<String, Double> categoryTotals = new HashMap<>();

        // Only CURRENT MONTH expenses
        for (Transaction t : transactions) {
            if ("EXPENSE".equalsIgnoreCase(t.getType())
                    && t.getDate().getMonthValue() == currentMonth
                    && t.getDate().getYear() == currentYear) {

                categoryTotals.merge(
                        t.getCategory().toLowerCase(),
                        t.getAmount(),
                        Double::sum
                );
            }
        }

        // Monthly limits
        Map<String, Double> thresholds = Map.of(
                "food", 2000.0,
                "bills", 800.0,
                "travel", 1500.0
        );

        // Generate alerts
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            String category = entry.getKey();
            double spent = entry.getValue();

            if (thresholds.containsKey(category)
                    && spent > thresholds.get(category)) {

                alerts.add(
                        "⚠️ You overspent in " + category +
                                " this month (₹" + spent + ")"
                );
            }
        }

        return alerts;
    }

    public String compareMonthlyExpenses(List<Transaction> transactions) {

        // Collect month-wise totals: YYYY-MM -> amount
        Map<String, Double> monthlyTotals = new HashMap<>();

        for (Transaction t : transactions) {
            if (!"EXPENSE".equalsIgnoreCase(t.getType())) continue;

            String key = t.getDate().getYear() + "-" +
                    String.format("%02d", t.getDate().getMonthValue());

            monthlyTotals.merge(key, t.getAmount(), Double::sum);
        }

        // If only one (or zero) month exists
        if (monthlyTotals.size() < 2) {
            return "📊 This is your first month of tracking expenses";
        }

        // Sort months chronologically
        List<String> months = new ArrayList<>(monthlyTotals.keySet());
        Collections.sort(months);

        String currentMonth = months.get(months.size() - 1);
        String previousMonth = months.get(months.size() - 2);

        double currentTotal = monthlyTotals.get(currentMonth);
        double previousTotal = monthlyTotals.get(previousMonth);

        double diff = currentTotal - previousTotal;

        if (diff > 0) {
            return "📉 You spent ₹" + diff + " more than last month";
        } else if (diff < 0) {
            return "📈 Good job! You saved ₹" + Math.abs(diff) + " compared to last month";
        } else {
            return "⚖️ Your spending is the same as last month";
        }
    }

    public List<String> getMonthlySpendingAlerts(User user) {

        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();

        List<String> alerts = new ArrayList<>();

        Map<String, Double> limits = Map.of(
                "Food", 3000.0,
                "Bills", 800.0,
                "Travel", 2000.0
        );

        for (String category : limits.keySet()) {

            List<Transaction> txns =
                    transactionRepository.findMonthlyCategoryExpenses(
                            user, category, month, year
                    );

            double total = txns.stream()
                    .mapToDouble(Transaction::getAmount)
                    .sum();

            if (total > limits.get(category)) {
                alerts.add(
                        "⚠️ You overspent in " + category +
                                " this month. Spent ₹" + total +
                                " | Limit ₹" + limits.get(category)
                );
            }
        }
        return alerts;
    }





}
