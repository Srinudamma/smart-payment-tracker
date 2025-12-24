package com.SmartPaymentTracker.smart_payment_tracker.controller;

import com.SmartPaymentTracker.smart_payment_tracker.entity.User;
import com.SmartPaymentTracker.smart_payment_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers()
    {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    public ResponseEntity<User> getUserById(@PathVariable Long id)
    {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity :: ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //update user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,@RequestBody User userDetails )
    {
        Optional<User> userOptional = userService.getUserById(id);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            return ResponseEntity.ok(userService.saveUser(user));
        }
        else {return  ResponseEntity.notFound().build();}
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email)
    {
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity :: ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
