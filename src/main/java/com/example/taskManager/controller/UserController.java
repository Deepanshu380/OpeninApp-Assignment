package com.example.taskManager.controller;

import com.example.taskManager.JwtUtil;
import com.example.taskManager.repository.UserRepository;
import com.example.taskManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestHeader("Authorization") String token) {
        String phoneNumber = getPhoneNumberFromToken(token);
        // Use the phoneNumber to create or update the user
        userService.createOrUpdateUser(phoneNumber);
        return ResponseEntity.ok("User created/updated successfully.");
    }

    // Add other CRUD endpoints as needed

    private String getPhoneNumberFromToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            return jwtUtil.extractPhoneNumber(jwt);
        }
        return null;

    }
}
