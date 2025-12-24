package com.SmartPaymentTracker.smart_payment_tracker.repository;

import com.SmartPaymentTracker.smart_payment_tracker.entity.Transaction;
import com.SmartPaymentTracker.smart_payment_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.*;
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
    List<Transaction> findByUserAndDate(User user, LocalDate Date);
    List<Transaction> findByUserAndType(User user,String type);
    List<Transaction> findByUserAndDateBetween(User user,LocalDate start,LocalDate end);
    @Query("""
SELECT t FROM Transaction t
WHERE t.user = :user
AND t.category = :category
AND MONTH(t.date) = :month
AND YEAR(t.date) = :year
""")
    List<Transaction> findMonthlyCategoryExpenses(
            @Param("user") User user,
            @Param("category") String category,
            @Param("month") int month,
            @Param("year") int year
    );



}
