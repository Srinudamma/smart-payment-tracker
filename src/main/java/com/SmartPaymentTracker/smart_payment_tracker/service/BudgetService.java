package com.SmartPaymentTracker.smart_payment_tracker.service;

import com.SmartPaymentTracker.smart_payment_tracker.entity.Budget;
import com.SmartPaymentTracker.smart_payment_tracker.entity.User;
import com.SmartPaymentTracker.smart_payment_tracker.repository.BudgetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public List<Budget> getBudgetsByUser(User user) {
        return budgetRepository.findByUser(user);
    }

    public void saveOrUpdateBudget(Budget budget) {
        budgetRepository.save(budget);
    }

    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }
}
