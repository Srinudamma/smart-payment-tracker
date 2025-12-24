package com.SmartPaymentTracker.smart_payment_tracker.repository;

import com.SmartPaymentTracker.smart_payment_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String Email);
}
