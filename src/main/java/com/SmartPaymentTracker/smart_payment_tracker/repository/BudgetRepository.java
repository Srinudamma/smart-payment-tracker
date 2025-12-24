package com.SmartPaymentTracker.smart_payment_tracker.repository;

import com.SmartPaymentTracker.smart_payment_tracker.entity.Budget;
import com.SmartPaymentTracker.smart_payment_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByUser(User user);

    Optional<Budget> findByUserAndCategory(User user, String category);
}
