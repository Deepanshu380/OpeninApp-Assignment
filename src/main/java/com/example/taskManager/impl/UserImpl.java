package com.example.taskManager.impl;


import com.example.taskManager.models.Task;
import com.example.taskManager.models.User;
import com.example.taskManager.repository.UserRepository;
import com.example.taskManager.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void createOrUpdateUser(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (user == null) {
            User newUser = new User();
            newUser.setPhoneNumber(phoneNumber);
            newUser.setPriority(2);
            userRepository.save(newUser);
        }
    }
}
